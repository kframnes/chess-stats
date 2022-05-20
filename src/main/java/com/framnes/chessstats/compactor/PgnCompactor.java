package com.framnes.chessstats.compactor;

import com.github.bhlangonijr.chesslib.game.Game;
import com.github.bhlangonijr.chesslib.move.Move;
import com.github.bhlangonijr.chesslib.move.MoveConversionException;
import com.github.bhlangonijr.chesslib.move.MoveList;
import com.github.bhlangonijr.chesslib.pgn.PgnHolder;
import com.google.inject.Singleton;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

@Singleton
public class PgnCompactor {

    // Now we have the entire move tree in Maps of Maps, we need to generate our String PGN
    // from this data.  We're going to leverage a Stack in order to pull moves out of the tree
    // and serialize them into the proper format.
    //
    class StackedMove {

        private boolean isNewVariation;
        private boolean isWhiteMove;
        private int moveNumber;
        private Map<String, Map> moveTree;

        public StackedMove(boolean isNewVariation, boolean isWhiteMove, int moveNumber, Map<String, Map> moveTree) {
            this.isNewVariation = isNewVariation;
            this.isWhiteMove = isWhiteMove;
            this.moveNumber = moveNumber;
            this.moveTree = moveTree;
        }

        public boolean isNewVariation() {
            return isNewVariation;
        }

        public boolean isWhiteMove() {
            return isWhiteMove;
        }

        public int getMoveNumber() {
            return moveNumber;
        }

        public Map<String, Map> getMoveTree() {
            return moveTree;
        }

    }

    public void run(String importPath) throws MoveConversionException {

        // Initialize our import target, which can be a PGN file, or a directory of
        // PGN files.
        File importTarget = new File(importPath);
        if (!importTarget.exists()) {
            System.err.println("Import path not found: " + importPath);
            System.exit(1);
        }

        List<PgnHolder> pgns = new ArrayList<>();
        if (importTarget.isDirectory()) {
            File[] files = importTarget.listFiles((file) -> file.getName().endsWith(".pgn"));
            for (File file : files) {
                pgns.add(new PgnHolder(file.getAbsolutePath()));
            }
        } else {
            pgns.add(new PgnHolder(importTarget.getAbsolutePath()));
        }

        try {
            for (PgnHolder pgnHolder : pgns) {
                pgnHolder.loadPgn();
            }
        } catch (Exception e) {
            throw new RuntimeException("There was an issue loading PGN file", e);
        }

        // We're going to create a map of all moves and variations from our list of 'games'.  This map
        // will be in the form of: <move> --> <map of moves>
        //
        //  {
        //      'e4': {
        //          'e5': {
        //              ...
        //              },
        //          'c5': {
        //              ...
        //          }
        //      ...
        //      ...
        //  }
        //
        Map<String, Map> rootTree = new HashMap<>();

        for (PgnHolder pgnHolder : pgns) {
            for (Game game : pgnHolder.getGame()) {

                String moveText = game.getMoveText()
                        .toString()
                        .replace('\n', ' ');
                MoveList moves = new MoveList();
                moves.loadFromSan(moveText);

                // Initialize tree pointer to the root (starting at first move)
                //
                Map<String, Map> moveTree = rootTree;
                for (Move move : moves) {
                    moveTree = moveTree.computeIfAbsent(move.getSan(), (k) -> new HashMap<String, Map>());
                }

            }
        }

        Stack<StackedMove> moveStack = new Stack<>();
        moveStack.push(new StackedMove(false, true, 1, rootTree));

        StringBuffer pgnBuffer = new StringBuffer();

        while(!moveStack.isEmpty()) {

            StackedMove current = moveStack.pop();
            boolean isFirstOption = true;

            for (Map.Entry<String, Map> entry : current.getMoveTree().entrySet()) {

                String san = entry.getKey();
                Map<String, Map> continuation = entry.getValue();

                if (isFirstOption) {
                    pgnBuffer.append(getPgnMoveNumber(current));
                    if (entry.getValue().isEmpty()) {
                        pgnBuffer.append(san + " ) ");
                    } else {
                        moveStack.push(new StackedMove(
                                false,
                                !current.isWhiteMove(),
                                current.isWhiteMove() ? current.getMoveNumber() : current.getMoveNumber() + 1,
                                continuation
                        ));
                        pgnBuffer.append(san + " ");
                    }
                    isFirstOption = false;
                } else {
                    moveStack.push(new StackedMove(
                            true,
                            current.isWhiteMove(),
                            current.getMoveNumber(),
                            Collections.singletonMap(san, continuation)
                    ));
                }

            }

        }

        // It's easiest to end all lines with " ) " and then trim the last one from the mainline, so we do that here.
        String pgn = pgnBuffer.substring(0, pgnBuffer.length() - 2) + " *";
        System.out.println("===== PGN ========================================");
        System.out.println(pgn);
        System.out.println("===== PGN ========================================");

        System.exit(0);

    }

    /**
     * Given a move, provides the current PGN fragment representing this move.
     *
     * @param move
     * @return
     */
    private String getPgnMoveNumber(StackedMove move) {
        if (move.isWhiteMove()) {
            if (move.isNewVariation()) {
                return "( " + move.getMoveNumber() + ". ";
            } else {
                return move.getMoveNumber() + ". ";
            }
        } else {
            if (move.isNewVariation()) {
                return "( " + move.getMoveNumber() + "... ";
            } else {
                return " ";
            }
        }
    }

    public static void main(String [] args) throws MoveConversionException {

        String importPath = System.getProperty("importPath");

        // Kick off the import process.
        PgnCompactor compactor = new PgnCompactor();
        compactor.run(importPath);

    }

}
