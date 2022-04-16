package com.framnes.chessstats.cleaner;

import com.framnes.chessstats.console.ITrackableProgress;
import com.framnes.chessstats.console.TrackedWorkConsole;
import com.framnes.chessstats.dao.ChessStatsDao;
import com.framnes.chessstats.model.ChessMove;
import com.framnes.chessstats.model.ChessPlayer;
import com.framnes.chessstats.model.ChessPlayerStats;

import java.util.List;

public class CleanWorker implements Runnable, ITrackableProgress {

    private static final int BOOK_DEPTH = 5;

    private final ChessStatsDao chessStatsDao;
    private final TrackedWorkConsole trackedWorkConsole;

    private final ChessPlayer player;

    private boolean failed = false;

    public CleanWorker(ChessStatsDao chessStatsDao, TrackedWorkConsole trackedWorkConsole, ChessPlayer player) {
        this.chessStatsDao = chessStatsDao;
        this.trackedWorkConsole = trackedWorkConsole;
        this.player = player;
    }

    @Override
    public void run() {

        try {
            List<ChessMove> moves = chessStatsDao.getMovesForTargetPlayer(player.getId(), BOOK_DEPTH * 2);
            ChessPlayerStats stats = new ChessPlayerStats(player, moves);
            chessStatsDao.deletePlayerStats(player.getId());
            chessStatsDao.insertPlayerStats(stats);
            chessStatsDao.updatePlayerState(player.getId(), false);
        } catch (Exception e) {
            failed = true;
        } finally {
            trackedWorkConsole.untrack(this);
        }

    }

    @Override
    public String trackableName() {
        return String.format("%s", player.getUsername());
    }

    @Override
    public int completedWorkUnits() {
        return 1;
    }

    @Override
    public int totalWorkUnits() {
        return 1;
    }

    @Override
    public boolean isFailed() {
        return failed;
    }

}
