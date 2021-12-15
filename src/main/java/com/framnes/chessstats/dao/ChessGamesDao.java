package com.framnes.chessstats.dao;

import com.framnes.chessstats.model.ChessGame;
import com.framnes.chessstats.model.ChessMove;
import org.jdbi.v3.sqlobject.SqlObject;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlBatch;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

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
    @GetGeneratedKeys
    int insertGame(@BindBean("game") ChessGame game);

    @SqlBatch(
        "INSERT INTO `moves` " +
        "(game_id, move_color, ply, move, eval_before, mate_in_before, eval_after, mate_in_after, " +
                "move_rank, forced, final_position, checkmate) " +
        "VALUES " +
        "(:gameId, :moveColor, :ply, :move, :positionEvalBefore, :mateInBefore, " +
                ":positionEvalAfter, :mateInAfter, :moveEngineRank, :forced, " +
                ":finalPosition, :checkMate)"
    )
    void insertMoves(@BindBean List<ChessMove> move);

}
