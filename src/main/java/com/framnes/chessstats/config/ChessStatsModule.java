package com.framnes.chessstats.config;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

public class ChessStatsModule extends AbstractModule {

    @Provides
    public Jdbi providesJdbi() {

        Jdbi jdbi = Jdbi.create("jdbc:mysql:User=root;Password=pass;Database=chess;Server=127.0.0.1;Port=3306;");
        jdbi.installPlugin(new SqlObjectPlugin());

        return jdbi;

    }



}
