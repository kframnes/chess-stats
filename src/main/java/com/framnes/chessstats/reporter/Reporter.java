package com.framnes.chessstats.reporter;

import com.framnes.chessstats.config.ChessStatsModule;
import com.framnes.chessstats.console.ReportConsole;
import com.framnes.chessstats.dao.ChessStatsDao;
import com.framnes.chessstats.model.ChessMove;
import com.framnes.chessstats.model.ChessPlayerStats;
import com.framnes.chessstats.model.GameSite;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import org.jdbi.v3.core.Jdbi;

import java.util.List;

public class Reporter {

    private final ChessStatsDao chessStatsDao;

    @Inject
    public Reporter(Jdbi jdbi) {
        this.chessStatsDao = jdbi.onDemand(ChessStatsDao.class);
    }

    public void run(String targetPlayer) {

        // Fetch stats
        ChessPlayerStats targetStats = chessStatsDao.getTargetPlayerStats(GameSite.CHESS_COM, targetPlayer);
        List<ChessPlayerStats> comparableStats = chessStatsDao.getComparablePlayersStats(GameSite.CHESS_COM);

        // Build report
        ReportConsole reporter = new ReportConsole(comparableStats, targetStats);
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
