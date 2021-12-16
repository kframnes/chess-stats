package com.framnes.chessstats.dao;

import com.framnes.chessstats.model.ChessGame;
import com.framnes.chessstats.model.ChessMove;
import org.jdbi.v3.sqlobject.SqlObject;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlBatch;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
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


    @SqlQuery(
        "SELECT m.id, m.game_id AS gameId, m.move_color AS moveColor, m.ply, m.move, " +
            "m.eval_before AS positionEvalBefore, m.mate_in_before AS mateInBefore, " +
            "m.eval_after AS positionEvalAfter, m.mate_in_after AS mateInAfter, " +
            "m.move_rank AS moveEngineRank, m.forced, m.final_position AS finalPosition, " +
            "m.checkmate AS checkMate " +
        "FROM `games` g " +
            "INNER JOIN `moves` m on g.id = m.game_id " +
        "WHERE g.white_player = :player " +
            "AND m.move_color = 'WHITE' " +
            "AND m.ply > :bookPlyDepth " +
        "UNION " +
        "SELECT m.id, m.game_id AS gameId, m.move_color AS moveColor, m.ply, m.move, " +
            "m.eval_before AS positionEvalBefore, m.mate_in_before AS mateInBefore, " +
            "m.eval_after AS positionEvalAfter, m.mate_in_after AS mateInAfter, " +
            "m.move_rank AS moveEngineRank, m.forced, m.final_position AS finalPosition, " +
            "m.checkmate AS checkMate " +
        "FROM `games` g " +
            "INNER JOIN `moves` m on g.id = m.game_id " +
        "WHERE g.black_player = :player " +
            "AND m.move_color = 'BLACK' " +
            "AND m.ply > :bookPlyDepth"
    )
    @RegisterBeanMapper(ChessMove.class)
    List<ChessMove> getMovesForTargetPlayer(@Bind("player") String playerName, @Bind("bookPlyDepth") int bookPlyDepth);

    @SqlQuery(
        "SELECT m.id, m.game_id AS gameId, m.move_color AS moveColor, m.ply, m.move, " +
            "m.eval_before AS positionEvalBefore, m.mate_in_before AS mateInBefore, " +
            "m.eval_after AS positionEvalAfter, m.mate_in_after AS mateInAfter, " +
            "m.move_rank AS moveEngineRank, m.forced, m.final_position AS finalPosition, " +
            "m.checkmate AS checkMate " +
        "FROM `games` g " +
            "INNER JOIN `moves` m on g.id = m.game_id " +
            "LEFT JOIN cheaters c on g.site = c.site AND c.player_name = g.white_player " +
        "WHERE g.white_player <> :player AND c.player_name IS NULL " +
            "AND g.white_elo >= :minElo AND g.white_elo <= :maxElo " +
            "AND m.move_color = 'WHITE' " +
            "AND m.ply > :bookPlyDepth " +
        "UNION " +
        "SELECT m.id, m.game_id AS gameId, m.move_color AS moveColor, m.ply, m.move, " +
            "m.eval_before AS positionEvalBefore, m.mate_in_before AS mateInBefore, " +
            "m.eval_after AS positionEvalAfter, m.mate_in_after AS mateInAfter, " +
            "m.move_rank AS moveEngineRank, m.forced, m.final_position AS finalPosition, " +
            "m.checkmate AS checkMate " +
        "FROM `games` g " +
            "INNER JOIN `moves` m on g.id = m.game_id " +
            "LEFT JOIN cheaters c on g.site = c.site AND c.player_name = g.black_player " +
        "WHERE g.black_player <> :player AND c.player_name IS NULL " +
            "AND g.black_elo >= :minElo AND g.black_elo <= :maxElo " +
            "AND m.move_color = 'BLACK' " +
            "AND m.ply > :bookPlyDepth"
    )
    @RegisterBeanMapper(ChessMove.class)
    List<ChessMove> getMovesForComparable(@Bind("player") String playerName, @Bind("bookPlyDepth") int bookPlyDepth,
                                          @Bind("minElo") int minElo, @Bind("maxElo") int maxElo);

    @SqlQuery(
        "SELECT MIN(elo) FROM (" +
            "SELECT white_elo AS elo FROM `games` WHERE white_player = :player " +
            "UNION " +
            "SELECT black_elo AS elo FROM `games` WHERE black_player = :player " +
        ") AS elos"
    )
    int getMinElo(@Bind("player") String playerName);

    @SqlQuery(
        "SELECT MAX(elo) FROM (" +
            "SELECT white_elo AS elo FROM `games` WHERE white_player = :player " +
            "UNION " +
            "SELECT black_elo AS elo FROM `games` WHERE black_player = :player " +
        ") AS elos"
    )
    int getMaxElo(@Bind("player") String playerName);

}
