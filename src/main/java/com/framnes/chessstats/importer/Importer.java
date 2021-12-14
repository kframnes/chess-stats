package com.framnes.chessstats.importer;

import com.framnes.chessstats.config.ChessStatsModule;
import com.framnes.chessstats.console.ImportConsole;
import com.framnes.chessstats.dao.ChessGamesDao;
import com.github.bhlangonijr.chesslib.pgn.PgnHolder;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import org.jdbi.v3.core.Jdbi;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Singleton
public class Importer {

    // Lock to keep the import process alive as it processes Games.
    public final static Object LOCK = new Object();

    private ExecutorService executor;
    private final ChessGamesDao chessGamesDao;

    @Inject
    public Importer(Jdbi jdbi) {
        this.chessGamesDao = jdbi.onDemand(ChessGamesDao.class);
    }

    public void run(String importPath, int numThreads) {

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

        int totalGames = 0;
        try {
            for (PgnHolder pgnHolder : pgns) {
                pgnHolder.loadPgn();
                totalGames += pgnHolder.getGame().size();
            }
        } catch (Exception e) {
            throw new RuntimeException("There was an issue loading PGN file", e);
        }

        // Create executor with number of threads requested
        this.executor = Executors.newFixedThreadPool(numThreads);

        // Initialize jobs
        ImportConsole console = new ImportConsole(totalGames, numThreads);
        pgns.stream().flatMap(pgnHolder -> pgnHolder.getGame().stream())
                .map(game -> new ImportWorker(chessGamesDao, console, game))
                .forEach(executor::submit);

    }

    public static void main(String [] args) {

        String importPath = System.getProperty("importPath");

        // Constructor and import process
        Injector injector = Guice.createInjector(
                new ChessStatsModule()
        );

        // Kick off the import process.
        Importer importer = injector.getInstance(Importer.class);
        importer.run(importPath, 5);

        // Lock until all importing is complete.
        synchronized (LOCK) {
            try {
                LOCK.wait();
            } catch (InterruptedException e) {
                e.printStackTrace(System.err);
            }
        }


    }

}
