package com.framnes.chessstats.importer;

import com.framnes.chessstats.console.ITrackableProgress;
import com.framnes.chessstats.console.ImportConsole;
import com.framnes.chessstats.dao.ChessGamesDao;
import com.framnes.chessstats.engine.Engine;
import com.framnes.chessstats.engine.EngineFactory;
import com.framnes.chessstats.engine.EvaluatedPosition;
import com.framnes.chessstats.model.ChessGame;
import com.framnes.chessstats.model.ChessMove;
import com.github.bhlangonijr.chesslib.game.Game;
import com.github.bhlangonijr.chesslib.move.MoveList;
import org.jdbi.v3.core.statement.UnableToExecuteStatementException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

public class ImportWorker implements Runnable, ITrackableProgress {

    private final EngineFactory engineFactory;
    private final ChessGamesDao chessGamesDao;
    private final ImportConsole importConsole;

    private final Game game;

    private int analyzedMoves = 0;
    private boolean failed = false;

    public ImportWorker(EngineFactory engineFactory, ChessGamesDao chessGamesDao, ImportConsole importConsole, Game game) {
        this.engineFactory = engineFactory;
        this.chessGamesDao = chessGamesDao;
        this.importConsole = importConsole;
        this.game = game;
    }

    @Override
    public void run() {

        Engine engine = null;
        try {

            // Insert game details
            ChessGame chessGame = new ChessGame(game);
            int gameId = chessGamesDao.insertGame(chessGame);

            // Initialize engine
            engine = engineFactory.getEngine();
            if (!engine.startNewGame()) {
                throw new RuntimeException("There was a problem communicating with the engine");
            }

            // Prepare move list
            String moveText = game.getMoveText()
                    .toString()
                    .replace('\n', ' ');
            MoveList moves = new MoveList();
            moves.loadFromSan(moveText);
            game.setCurrentMoveList(moves);

            // Start console tracking for this item
            importConsole.track(this);

            // Analyze all positions in the game
            List<EvaluatedPosition> positions = new ArrayList<>();
            for (int i=0; i<=moves.size(); i++) {
                String fen = i==0 ? moves.getStartFen() : moves.getFen(i);
                positions.add(engine.bestMoves(fen));
                analyzedMoves++;
            }

            // Insert moves
            List<ChessMove> chessMoves = new ArrayList<>();
            for (int i=0; i<moves.size(); i++) {
                chessMoves.add(new ChessMove(gameId, i + 1, moves.get(i), positions.get(i), positions.get(i + 1),
                        i == moves.size() - 1));
            }

            chessGamesDao.insertMoves(chessMoves);

        } catch (UnableToExecuteStatementException insertFailed) {

            // It's reasonable that we may attempt to re-import a game in which case we don't want
            // to raise any sort of alarm.  However, we do want to throw an exception related to
            // some other underlying cause.
            if (!(insertFailed.getCause() instanceof SQLIntegrityConstraintViolationException)) {
                failed = true;
            }

        } catch (Exception e) {
            failed = true;
        } finally {
            if (engine != null) {
                engine.shutdown();
            }
            importConsole.untrack(this);
        }

    }

    @Override
    public String trackableName() {
        return String.format("%s - %s", game.getWhitePlayer().getName(), game.getBlackPlayer().getName());
    }

    @Override
    public int completedWorkUnits() {
        return analyzedMoves;
    }

    @Override
    public int totalWorkUnits() {
        return game.getCurrentMoveList().size();
    }

    @Override
    public boolean isFailed() {
        return failed;
    }

}
