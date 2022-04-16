package com.framnes.chessstats.importer;

import com.framnes.chessstats.console.ITrackableProgress;
import com.framnes.chessstats.console.TrackedWorkConsole;
import com.framnes.chessstats.dao.ChessStatsDao;
import com.framnes.chessstats.engine.Engine;
import com.framnes.chessstats.engine.EngineFactory;
import com.framnes.chessstats.engine.EvaluatedPosition;
import com.framnes.chessstats.model.ChessGame;
import com.framnes.chessstats.model.ChessMove;
import com.framnes.chessstats.model.ChessPlayer;
import com.framnes.chessstats.model.Color;
import com.framnes.chessstats.model.GameSite;
import com.github.bhlangonijr.chesslib.game.Game;
import com.github.bhlangonijr.chesslib.move.MoveList;
import org.jdbi.v3.core.statement.UnableToExecuteStatementException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

public class ImportWorker implements Runnable, ITrackableProgress {

    private final EngineFactory engineFactory;
    private final ChessStatsDao chessStatsDao;
    private final TrackedWorkConsole trackedWorkConsole;

    private final Game game;

    private int analyzedMoves = 0;
    private boolean failed = false;

    public ImportWorker(EngineFactory engineFactory, ChessStatsDao chessStatsDao, TrackedWorkConsole trackedWorkConsole, Game game) {
        this.engineFactory = engineFactory;
        this.chessStatsDao = chessStatsDao;
        this.trackedWorkConsole = trackedWorkConsole;
        this.game = game;
    }

    @Override
    public void run() {

        Engine engine = null;
        try {

            // Insert game details
            ChessPlayer white = getPlayer(game, Color.WHITE, GameSite.CHESS_COM);
            ChessPlayer black = getPlayer(game, Color.BLACK, GameSite.CHESS_COM);

            ChessGame chessGame = new ChessGame(game, white.getId(), black.getId());
            int gameId = chessStatsDao.insertGame(chessGame);

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
            trackedWorkConsole.track(this);

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

            chessStatsDao.insertMoves(chessMoves);

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
            trackedWorkConsole.untrack(this);
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

    /**
     * Helper function that attempts to fetch the player from data, creating them if they
     * do not currently exist.  The player is also marked as dirty since we will be importing
     * new moves for them.
     */
    private ChessPlayer getPlayer(Game game, Color color, GameSite site) {

        // Determine who we're looking at
        String username; int elo;
        switch (color) {
            case WHITE:
                username = game.getWhitePlayer().getName();
                elo = game.getWhitePlayer().getElo();
                break;
            case BLACK:
                username = game.getBlackPlayer().getName();
                elo = game.getBlackPlayer().getElo();
                break;
            default:
                throw new RuntimeException("Player must report as WHITE or BLACK!");
        }

        // Does we already have a record for this player?
        ChessPlayer player = chessStatsDao.getPlayer(site, username);
        if (player != null) {
            chessStatsDao.updatePlayerState(player.getId(), true);
            return player;
        }

        // Didn't find a hit, so we need to insert the player as new (by default new players will
        // be marked dirty)
        int playerId = chessStatsDao.insertPlayer(new ChessPlayer(site, username, elo));
        return chessStatsDao.getPlayer(playerId);

    }

}
