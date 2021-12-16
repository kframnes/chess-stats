package com.framnes.chessstats.reporter;

import com.framnes.chessstats.config.ChessStatsModule;
import com.framnes.chessstats.console.ReportConsole;
import com.framnes.chessstats.dao.ChessGamesDao;
import com.framnes.chessstats.model.ChessMove;
import com.framnes.chessstats.model.GameSite;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import org.jdbi.v3.core.Jdbi;

import java.util.List;

public class Reporter {

    private static final int BOOK_DEPTH = 5;
    private static final int ELO_RANGE = 50;

    private final ChessGamesDao chessGamesDao;

    @Inject
    public Reporter(Jdbi jdbi) {
        this.chessGamesDao = jdbi.onDemand(ChessGamesDao.class);
    }

    public void run(String targetPlayer) {

        // Fetch moves for player
        List<ChessMove> playerMoves = chessGamesDao.getMovesForTargetPlayer(targetPlayer, BOOK_DEPTH*2);

        // Fetch moves for comparable players
        int minElo = chessGamesDao.getMinElo(targetPlayer) - ELO_RANGE;
        int maxElo = chessGamesDao.getMaxElo(targetPlayer) + ELO_RANGE;
        List<ChessMove> comparableMoves = chessGamesDao.getMovesForComparable(targetPlayer, BOOK_DEPTH, minElo, maxElo);

        // Build report
        ReportConsole reporter = new ReportConsole(comparableMoves, playerMoves);
        reporter.run();

    }

    public static void main(String [] args) {

        String targetPlayer = System.getProperty("targetPlayer");

        // Constructor and import process
        Injector injector = Guice.createInjector(
                new ChessStatsModule()
        );

        Reporter reporter = injector.getInstance(Reporter.class);
        reporter.run(targetPlayer);

    }

}
