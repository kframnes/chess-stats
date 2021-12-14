package com.framnes.chessstats.dao;

import com.framnes.chessstats.model.ChessGame;
import org.jdbi.v3.sqlobject.SqlObject;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface ChessGamesDao extends SqlObject {

    @SqlUpdate(
        "INSERT INTO `games` " +
        "(site, game_key, type, result, " +
                "white_player, black_player, " +
                "white_elo, black_elo) " +
        "VALUES " +
        "(:game.gameSite, :game.gameKey, :game.gameType, :game.gameResult, " +
                ":game.whitePlayer, :game.blackPlayer," +
                ":game.whiteElo, :game.blackElo)"
    )
    void insertGame(@BindBean("game") ChessGame game);

}
