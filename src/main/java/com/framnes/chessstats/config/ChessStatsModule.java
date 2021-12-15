package com.framnes.chessstats.config;

import com.framnes.chessstats.engine.EngineFactory;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

public class ChessStatsModule extends AbstractModule {

    private final static String ENGINE_PATH = "/usr/local/bin/stockfish";

    @Provides
    public Jdbi providesJdbi() {
        Jdbi jdbi = Jdbi.create("jdbc:mysql://127.0.0.1:3306/chess?user=root&password=pass");
        jdbi.installPlugin(new SqlObjectPlugin());
        return jdbi;
    }

    @Provides
    public EngineFactory providesEngineFactory() {
        return new EngineFactory(ENGINE_PATH);
    }

}
