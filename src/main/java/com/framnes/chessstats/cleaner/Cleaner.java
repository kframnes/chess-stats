package com.framnes.chessstats.cleaner;

import com.framnes.chessstats.config.ChessStatsModule;
import com.framnes.chessstats.console.TrackedWorkConsole;
import com.framnes.chessstats.dao.ChessStatsDao;
import com.framnes.chessstats.model.ChessPlayer;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import org.jdbi.v3.core.Jdbi;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Singleton
public class Cleaner {

    // Lock to keep the clean process alive as it recalculates stats for players.
    public final static Object LOCK = new Object();

    private final ChessStatsDao chessStatsDao;

    @Inject
    public Cleaner(Jdbi jdbi) {
        this.chessStatsDao = jdbi.onDemand(ChessStatsDao.class);
    }

    public void run(int numThreads) {

        // Get a list of all dirty players.
        List<ChessPlayer> players = chessStatsDao.getDirtyPlayers();
        if (players.isEmpty()) {
            System.out.println("\nAll player stats are clean and ready for reporting.\n");
            System.exit(0);
        }

        // Create executor with number of threads requested
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        // Initialize jobs
        TrackedWorkConsole console = new TrackedWorkConsole(executor, LOCK);
        players.stream()
                .map(player -> new CleanWorker(chessStatsDao, console, player))
                .forEach(executor::submit);
        console.start();

        // Lock until all cleaning is complete.
        synchronized (LOCK) {
            try {
                LOCK.wait();
            } catch (InterruptedException e) {
                e.printStackTrace(System.err);
            }
        }

        console.stop();
        executor.shutdown();

        System.exit(0);

    }

    public static void main(String [] args) {

        // Constructor and import process
        Injector injector = Guice.createInjector(
                new ChessStatsModule()
        );

        // Kick off the import process.
        Cleaner importer = injector.getInstance(Cleaner.class);
        importer.run(5);

    }

}
