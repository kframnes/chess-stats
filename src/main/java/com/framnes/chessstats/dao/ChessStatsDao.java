package com.framnes.chessstats.dao;

import com.framnes.chessstats.model.ChessGame;
import com.framnes.chessstats.model.ChessMove;
import com.framnes.chessstats.model.ChessPlayer;
import com.framnes.chessstats.model.ChessPlayerStats;
import com.framnes.chessstats.model.GameSite;
import org.jdbi.v3.sqlobject.SqlObject;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlBatch;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.Collections;
import java.util.List;

public interface ChessStatsDao extends SqlObject {

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PLAYERS
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @SqlQuery(
        "SELECT id, site, username, elo, dirty, accused, flagged " +
        "FROM `players` p " +
        "WHERE p.site = :site " +
                "AND p.username = :username"
    )
    @RegisterBeanMapper(ChessPlayer.class)
    ChessPlayer getPlayer(@Bind("site") GameSite site, @Bind("username") String username);

    @SqlQuery(
        "SELECT id, site, username, elo, dirty, accused, flagged " +
        "FROM `players` p " +
        "WHERE p.id = :id"
    )
    @RegisterBeanMapper(ChessPlayer.class)
    ChessPlayer getPlayer(@Bind("id") int id);

    @SqlUpdate(
        "INSERT INTO `players` " +
        "(site, username, elo) " +
        "VALUES (:player.site, :player.username, :player.elo)"
    )
    @GetGeneratedKeys
    int insertPlayer(@BindBean("player") ChessPlayer player);

    @SqlUpdate(
        "UPDATE `players` " +
        "SET dirty = :dirty " +
        "WHERE id = :id"
    )
    void updatePlayerState(@Bind("id") int id, @Bind("dirty") boolean dirty);

    @SqlQuery(
        "SELECT id, site, username, elo, dirty, accused, flagged " +
        "FROM `players` " +
        "WHERE dirty = 1"
    )
    @RegisterBeanMapper(ChessPlayer.class)
    List<ChessPlayer> getDirtyPlayers();

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // GAMES
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @SqlUpdate(
        "INSERT INTO `games` " +
        "(site, game_key, type, result, " +
                "white_player, black_player) " +
        "VALUES (:game.gameSite, :game.gameKey, :game.gameType, :game.gameResult, " +
                ":game.whitePlayerId, :game.blackPlayerId)"
    )
    @GetGeneratedKeys
    int insertGame(@BindBean("game") ChessGame game);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // MOVES
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @SqlBatch(
        "INSERT INTO `moves` " +
        "(game_id, move_color, ply, move, eval_before, mate_in_before, eval_after, mate_in_after, " +
                "move_rank, forced, final_position, checkmate) " +
        "VALUES (:gameId, :moveColor, :ply, :move, :positionEvalBefore, :mateInBefore, " +
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
                "AND m.forced = 0 " +

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
                "AND m.ply > :bookPlyDepth " +
                "AND m.forced = 0 "
    )
    @RegisterBeanMapper(ChessMove.class)
    List<ChessMove> getMovesForTargetPlayer(@Bind("player") int playerId, @Bind("bookPlyDepth") int bookPlyDepth);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // STATS
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @SqlUpdate(
        "DELETE FROM `players_stats` " +
        "WHERE `player_id` = :player"
    )
    void deletePlayerStats(@Bind("player") int playerId);

    @SqlUpdate(
        "INSERT INTO `players_stats` " +
        "(player_id, " +

                "n_losing_mate, " +
                "n_losing_1000, " +
                "n_losing_800, " +
                "n_losing_600, " +
                "n_losing_400, " +
                "n_losing_200, " +
                "n_losing_50, " +
                "n_even, " +
                "n_winning_50, " +
                "n_winning_200, " +
                "n_winning_400, " +
                "n_winning_600, " +
                "n_winning_800, " +
                "n_winning_1000, " +
                "n_winning_mate, " +
                "n_winning, " +
                "n_losing, " +
                "n_total, " +
                
                "t1_losing_mate, " +
                "t1_losing_1000, " +
                "t1_losing_800, " +
                "t1_losing_600, " +
                "t1_losing_400, " +
                "t1_losing_200, " +
                "t1_losing_50, " +
                "t1_even, " +
                "t1_winning_50, " +
                "t1_winning_200, " +
                "t1_winning_400, " +
                "t1_winning_600, " +
                "t1_winning_800, " +
                "t1_winning_1000, " +
                "t1_winning_mate, " +
                "t1_winning, " +
                "t1_losing, " +
                "t1_total, " +

                "t2_losing_mate, " +
                "t2_losing_1000, " +
                "t2_losing_800, " +
                "t2_losing_600, " +
                "t2_losing_400, " +
                "t2_losing_200, " +
                "t2_losing_50, " +
                "t2_even, " +
                "t2_winning_50, " +
                "t2_winning_200, " +
                "t2_winning_400, " +
                "t2_winning_600, " +
                "t2_winning_800, " +
                "t2_winning_1000, " +
                "t2_winning_mate, " +
                "t2_winning, " +
                "t2_losing, " +
                "t2_total, " +

                "t3_losing_mate, " +
                "t3_losing_1000, " +
                "t3_losing_800, " +
                "t3_losing_600, " +
                "t3_losing_400, " +
                "t3_losing_200, " +
                "t3_losing_50, " +
                "t3_even, " +
                "t3_winning_50, " +
                "t3_winning_200, " +
                "t3_winning_400, " +
                "t3_winning_600, " +
                "t3_winning_800, " +
                "t3_winning_1000, " +
                "t3_winning_mate, " +
                "t3_winning, " +
                "t3_losing, " +
                "t3_total," +

                "givesMajors_losing_mate, " +
                "givesMajors_losing_1000, " +
                "givesMajors_losing_800, " +
                "givesMajors_losing_600, " +
                "givesMajors_losing_400, " +
                "givesMajors_losing_200, " +
                "givesMajors_losing_50, " +
                "givesMajors_even, " +
                "givesMajors_winning_50, " +
                "givesMajors_winning_200, " +
                "givesMajors_winning_400, " +
                "givesMajors_winning_600, " +
                "givesMajors_winning_800, " +
                "givesMajors_winning_1000, " +
                "givesMajors_winning_mate, " +
                "givesMajors_winning, " +
                "givesMajors_losing, " +
                "givesMajors_total," +

                "givesMinors_losing_mate, " +
                "givesMinors_losing_1000, " +
                "givesMinors_losing_800, " +
                "givesMinors_losing_600, " +
                "givesMinors_losing_400, " +
                "givesMinors_losing_200, " +
                "givesMinors_losing_50, " +
                "givesMinors_even, " +
                "givesMinors_winning_50, " +
                "givesMinors_winning_200, " +
                "givesMinors_winning_400, " +
                "givesMinors_winning_600, " +
                "givesMinors_winning_800, " +
                "givesMinors_winning_1000, " +
                "givesMinors_winning_mate, " +
                "givesMinors_winning, " +
                "givesMinors_losing, " +
                "givesMinors_total, " +

                "givesPawns_losing_mate, " +
                "givesPawns_losing_1000, " +
                "givesPawns_losing_800, " +
                "givesPawns_losing_600, " +
                "givesPawns_losing_400, " +
                "givesPawns_losing_200, " +
                "givesPawns_losing_50, " +
                "givesPawns_even, " +
                "givesPawns_winning_50, " +
                "givesPawns_winning_200, " +
                "givesPawns_winning_400, " +
                "givesPawns_winning_600, " +
                "givesPawns_winning_800, " +
                "givesPawns_winning_1000, " +
                "givesPawns_winning_mate, " +
                "givesPawns_winning, " +
                "givesPawns_losing, " +
                "givesPawns_total) " +

        "VALUES (:stats.playerId, " +

                ":stats.nWhenLosingWithMate, " +
                ":stats.nWhenLosingBy1000, " +
                ":stats.nWhenLosingBy800to999, " +
                ":stats.nWhenLosingBy600to799, " +
                ":stats.nWhenLosingBy400to599, " +
                ":stats.nWhenLosingBy200to399, " +
                ":stats.nWhenLosingBy50to199, " +
                ":stats.nWhenEven, " +
                ":stats.nWhenWinningBy50to199, " +
                ":stats.nWhenWinningBy200to399, " +
                ":stats.nWhenWinningBy400to599, " +
                ":stats.nWhenWinningBy600to799, " +
                ":stats.nWhenWinningBy800to999, " +
                ":stats.nWhenWinningBy1000, " +
                ":stats.nWhenWinningWithMate, " +
                ":stats.nWhenWinning, " +
                ":stats.nWhenLosing, " +
                ":stats.nTotal, " +
                
                ":stats.t1WhenLosingWithMate, " +
                ":stats.t1WhenLosingBy1000, " +
                ":stats.t1WhenLosingBy800to999, " +
                ":stats.t1WhenLosingBy600to799, " +
                ":stats.t1WhenLosingBy400to599, " +
                ":stats.t1WhenLosingBy200to399, " +
                ":stats.t1WhenLosingBy50to199, " +
                ":stats.t1WhenEven, " +
                ":stats.t1WhenWinningBy50to199, " +
                ":stats.t1WhenWinningBy200to399, " +
                ":stats.t1WhenWinningBy400to599, " +
                ":stats.t1WhenWinningBy600to799, " +
                ":stats.t1WhenWinningBy800to999, " +
                ":stats.t1WhenWinningBy1000, " +
                ":stats.t1WhenWinningWithMate, " +
                ":stats.t1WhenWinning, " +
                ":stats.t1WhenLosing, " +
                ":stats.t1Total, " +

                ":stats.t2WhenLosingWithMate, " +
                ":stats.t2WhenLosingBy1000, " +
                ":stats.t2WhenLosingBy800to999, " +
                ":stats.t2WhenLosingBy600to799, " +
                ":stats.t2WhenLosingBy400to599, " +
                ":stats.t2WhenLosingBy200to399, " +
                ":stats.t2WhenLosingBy50to199, " +
                ":stats.t2WhenEven, " +
                ":stats.t2WhenWinningBy50to199, " +
                ":stats.t2WhenWinningBy200to399, " +
                ":stats.t2WhenWinningBy400to599, " +
                ":stats.t2WhenWinningBy600to799, " +
                ":stats.t2WhenWinningBy800to999, " +
                ":stats.t2WhenWinningBy1000, " +
                ":stats.t2WhenWinningWithMate, " +
                ":stats.t2WhenWinning, " +
                ":stats.t2WhenLosing, " +
                ":stats.t2Total, " +

                ":stats.t3WhenLosingWithMate, " +
                ":stats.t3WhenLosingBy1000, " +
                ":stats.t3WhenLosingBy800to999, " +
                ":stats.t3WhenLosingBy600to799, " +
                ":stats.t3WhenLosingBy400to599, " +
                ":stats.t3WhenLosingBy200to399, " +
                ":stats.t3WhenLosingBy50to199, " +
                ":stats.t3WhenEven, " +
                ":stats.t3WhenWinningBy50to199, " +
                ":stats.t3WhenWinningBy200to399, " +
                ":stats.t3WhenWinningBy400to599, " +
                ":stats.t3WhenWinningBy600to799, " +
                ":stats.t3WhenWinningBy800to999, " +
                ":stats.t3WhenWinningBy1000, " +
                ":stats.t3WhenWinningWithMate, " +
                ":stats.t3WhenWinning, " +
                ":stats.t3WhenLosing, " +
                ":stats.t3Total, " +

                ":stats.givesMajorsWhenLosingWithMate, " +
                ":stats.givesMajorsWhenLosingBy1000, " +
                ":stats.givesMajorsWhenLosingBy800to999, " +
                ":stats.givesMajorsWhenLosingBy600to799, " +
                ":stats.givesMajorsWhenLosingBy400to599, " +
                ":stats.givesMajorsWhenLosingBy200to399, " +
                ":stats.givesMajorsWhenLosingBy50to199, " +
                ":stats.givesMajorsWhenEven, " +
                ":stats.givesMajorsWhenWinningBy50to199, " +
                ":stats.givesMajorsWhenWinningBy200to399, " +
                ":stats.givesMajorsWhenWinningBy400to599, " +
                ":stats.givesMajorsWhenWinningBy600to799, " +
                ":stats.givesMajorsWhenWinningBy800to999, " +
                ":stats.givesMajorsWhenWinningBy1000, " +
                ":stats.givesMajorsWhenWinningWithMate, " +
                ":stats.givesMajorsWhenWinning, " +
                ":stats.givesMajorsWhenLosing, " +
                ":stats.givesMajorsTotal, " +

                ":stats.givesMinorsWhenLosingWithMate, " +
                ":stats.givesMinorsWhenLosingBy1000, " +
                ":stats.givesMinorsWhenLosingBy800to999, " +
                ":stats.givesMinorsWhenLosingBy600to799, " +
                ":stats.givesMinorsWhenLosingBy400to599, " +
                ":stats.givesMinorsWhenLosingBy200to399, " +
                ":stats.givesMinorsWhenLosingBy50to199, " +
                ":stats.givesMinorsWhenEven, " +
                ":stats.givesMinorsWhenWinningBy50to199, " +
                ":stats.givesMinorsWhenWinningBy200to399, " +
                ":stats.givesMinorsWhenWinningBy400to599, " +
                ":stats.givesMinorsWhenWinningBy600to799, " +
                ":stats.givesMinorsWhenWinningBy800to999, " +
                ":stats.givesMinorsWhenWinningBy1000, " +
                ":stats.givesMinorsWhenWinningWithMate, " +
                ":stats.givesMinorsWhenWinning, " +
                ":stats.givesMinorsWhenLosing, " +
                ":stats.givesMinorsTotal, " +

                ":stats.givesPawnsWhenLosingWithMate, " +
                ":stats.givesPawnsWhenLosingBy1000, " +
                ":stats.givesPawnsWhenLosingBy800to999, " +
                ":stats.givesPawnsWhenLosingBy600to799, " +
                ":stats.givesPawnsWhenLosingBy400to599, " +
                ":stats.givesPawnsWhenLosingBy200to399, " +
                ":stats.givesPawnsWhenLosingBy50to199, " +
                ":stats.givesPawnsWhenEven, " +
                ":stats.givesPawnsWhenWinningBy50to199, " +
                ":stats.givesPawnsWhenWinningBy200to399, " +
                ":stats.givesPawnsWhenWinningBy400to599, " +
                ":stats.givesPawnsWhenWinningBy600to799, " +
                ":stats.givesPawnsWhenWinningBy800to999, " +
                ":stats.givesPawnsWhenWinningBy1000, " +
                ":stats.givesPawnsWhenWinningWithMate, " +
                ":stats.givesPawnsWhenWinning, " +
                ":stats.givesPawnsWhenLosing, " +
                ":stats.givesPawnsTotal)"
    )
    public void insertPlayerStats(@BindBean("stats") ChessPlayerStats stats);

    @SqlQuery(
        "SELECT s.id, s.player_id AS playerId, " +

                "n_losing_mate AS nWhenLosingWithMate, " +
                "n_losing_1000 AS nWhenLosingBy1000, " +
                "n_losing_800 AS nWhenLosingBy800to999, " +
                "n_losing_600 AS nWhenLosingBy600to799, " +
                "n_losing_400 AS nWhenLosingBy400to599, " +
                "n_losing_200 AS nWhenLosingBy200to399, " +
                "n_losing_50 AS nWhenLosingBy50to199, " +
                "n_even AS nWhenEven, " +
                "n_winning_50 AS nWhenWinningBy50to199, " +
                "n_winning_200 AS nWhenWinningBy200to399, " +
                "n_winning_400 AS nWhenWinningBy400to599, " +
                "n_winning_600 AS nWhenWinningBy600to799, " +
                "n_winning_800 nWhenWinningBy800to999, " +
                "n_winning_1000 nWhenWinningBy1000, " +
                "n_winning_mate nWhenWinningWithMate, " +
                "n_winning AS nWhenWinning, " +
                "n_losing AS nWhenLosing, " +
                "n_total AS nTotal, " +
                
                "t1_losing_mate AS t1WhenLosingWithMate, " +
                "t1_losing_1000 AS t1WhenLosingBy1000, " +
                "t1_losing_800 AS t1WhenLosingBy800to999, " +
                "t1_losing_600 AS t1WhenLosingBy600to799, " +
                "t1_losing_400 AS t1WhenLosingBy400to599, " +
                "t1_losing_200 AS t1WhenLosingBy200to399, " +
                "t1_losing_50 AS t1WhenLosingBy50to199, " +
                "t1_even AS t1WhenEven, " +
                "t1_winning_50 AS t1WhenWinningBy50to199, " +
                "t1_winning_200 AS t1WhenWinningBy200to399, " +
                "t1_winning_400 AS t1WhenWinningBy400to599, " +
                "t1_winning_600 AS t1WhenWinningBy600to799, " +
                "t1_winning_800 t1WhenWinningBy800to999, " +
                "t1_winning_1000 t1WhenWinningBy1000, " +
                "t1_winning_mate t1WhenWinningWithMate, " +
                "t1_winning AS t1WhenWinning, " +
                "t1_losing AS t1WhenLosing, " +
                "t1_total AS t1Total, " +

                "t2_losing_mate AS t2WhenLosingWithMate, " +
                "t2_losing_1000 AS t2WhenLosingBy1000, " +
                "t2_losing_800 AS t2WhenLosingBy800to999, " +
                "t2_losing_600 AS t2WhenLosingBy600to799, " +
                "t2_losing_400 AS t2WhenLosingBy400to599, " +
                "t2_losing_200 AS t2WhenLosingBy200to399, " +
                "t2_losing_50 AS t2WhenLosingBy50to199, " +
                "t2_even AS t2WhenEven, " +
                "t2_winning_50 AS t2WhenWinningBy50to199, " +
                "t2_winning_200 AS t2WhenWinningBy200to399, " +
                "t2_winning_400 AS t2WhenWinningBy400to599, " +
                "t2_winning_600 AS t2WhenWinningBy600to799, " +
                "t2_winning_800 AS t2WhenWinningBy800to999, " +
                "t2_winning_1000 AS t2WhenWinningBy1000, " +
                "t2_winning_mate AS t2WhenWinningWithMate, " +
                "t2_winning AS t2WhenWinning, " +
                "t2_losing AS t2WhenLosing, " +
                "t2_total AS t2Total, " +

                "t3_losing_mate AS t3WhenLosingWithMate, " +
                "t3_losing_1000 AS t3WhenLosingBy1000, " +
                "t3_losing_800 AS t3WhenLosingBy800to999, " +
                "t3_losing_600 AS t3WhenLosingBy600to799, " +
                "t3_losing_400 AS t3WhenLosingBy400to599, " +
                "t3_losing_200 AS t3WhenLosingBy200to399, " +
                "t3_losing_50 AS t3WhenLosingBy50to199, " +
                "t3_even AS t3WhenEven, " +
                "t3_winning_50 AS t3WhenWinningBy50to199, " +
                "t3_winning_200 AS t3WhenWinningBy200to399, " +
                "t3_winning_400 AS t3WhenWinningBy400to599, " +
                "t3_winning_600 AS t3WhenWinningBy600to799, " +
                "t3_winning_800 AS t3WhenWinningBy800to999, " +
                "t3_winning_1000 AS t3WhenWinningBy1000, " +
                "t3_winning_mate AS t3WhenWinningWithMate, " +
                "t3_winning AS t3WhenWinning, " +
                "t3_losing AS t3WhenLosing, " +
                "t3_total AS t3Total," +

                "givesMajors_losing_mate AS givesMajorsWhenLosingWithMate, " +
                "givesMajors_losing_1000 AS givesMajorsWhenLosingBy1000, " +
                "givesMajors_losing_800 AS givesMajorsWhenLosingBy800to999, " +
                "givesMajors_losing_600 AS givesMajorsWhenLosingBy600to799, " +
                "givesMajors_losing_400 AS givesMajorsWhenLosingBy400to599, " +
                "givesMajors_losing_200 AS givesMajorsWhenLosingBy200to399, " +
                "givesMajors_losing_50 AS givesMajorsWhenLosingBy50to199, " +
                "givesMajors_even AS givesMajorsWhenEven, " +
                "givesMajors_winning_50 AS givesMajorsWhenWinningBy50to199, " +
                "givesMajors_winning_200 AS givesMajorsWhenWinningBy200to399, " +
                "givesMajors_winning_400 AS givesMajorsWhenWinningBy400to599, " +
                "givesMajors_winning_600 AS givesMajorsWhenWinningBy600to799, " +
                "givesMajors_winning_800 AS givesMajorsWhenWinningBy800to999, " +
                "givesMajors_winning_1000 AS givesMajorsWhenWinningBy1000, " +
                "givesMajors_winning_mate AS givesMajorsWhenWinningWithMate, " +
                "givesMajors_winning AS givesMajorsWhenLosing, " +
                "givesMajors_losing AS givesMajorsWhenWinning, " +
                "givesMajors_total AS givesMajorsTotal," +

                "givesMinors_losing_mate AS givesMinorsWhenLosingWithMate, " +
                "givesMinors_losing_1000 AS givesMinorsWhenLosingBy1000, " +
                "givesMinors_losing_800 AS givesMinorsWhenLosingBy800to999, " +
                "givesMinors_losing_600 AS givesMinorsWhenLosingBy600to799, " +
                "givesMinors_losing_400 AS givesMinorsWhenLosingBy400to599, " +
                "givesMinors_losing_200 AS givesMinorsWhenLosingBy200to399, " +
                "givesMinors_losing_50 AS givesMinorsWhenLosingBy50to199, " +
                "givesMinors_even AS givesMinorsWhenEven, " +
                "givesMinors_winning_50 AS givesMinorsWhenWinningBy50to199, " +
                "givesMinors_winning_200 AS givesMinorsWhenWinningBy200to399, " +
                "givesMinors_winning_400 AS givesMinorsWhenWinningBy400to599, " +
                "givesMinors_winning_600 AS givesMinorsWhenWinningBy600to799, " +
                "givesMinors_winning_800 AS givesMinorsWhenWinningBy800to999, " +
                "givesMinors_winning_1000 AS givesMinorsWhenWinningBy1000, " +
                "givesMinors_winning_mate AS givesMinorsWhenWinningWithMate, " +
                "givesMinors_winning AS givesMinorsWhenLosing, " +
                "givesMinors_losing AS givesMinorsWhenWinning, " +
                "givesMinors_total AS givesMinorsTotal, " +

                "givesPawns_losing_mate AS givesPawnsWhenLosingWithMate, " +
                "givesPawns_losing_1000 AS givesPawnsWhenLosingBy1000, " +
                "givesPawns_losing_800 AS givesPawnsWhenLosingBy800to999, " +
                "givesPawns_losing_600 AS givesPawnsWhenLosingBy600to799, " +
                "givesPawns_losing_400 AS givesPawnsWhenLosingBy400to599, " +
                "givesPawns_losing_200 AS givesPawnsWhenLosingBy200to399, " +
                "givesPawns_losing_50 AS givesPawnsWhenLosingBy50to199, " +
                "givesPawns_even AS givesPawnsWhenEven, " +
                "givesPawns_winning_50 AS givesPawnsWhenWinningBy50to199, " +
                "givesPawns_winning_200 AS givesPawnsWhenWinningBy200to399, " +
                "givesPawns_winning_400 AS givesPawnsWhenWinningBy400to599, " +
                "givesPawns_winning_600 AS givesPawnsWhenWinningBy600to799, " +
                "givesPawns_winning_800 AS givesPawnsWhenWinningBy800to999, " +
                "givesPawns_winning_1000 AS givesPawnsWhenWinningBy1000, " +
                "givesPawns_winning_mate AS givesPawnsWhenWinningWithMate, " +
                "givesPawns_winning AS givesPawnsWhenLosing, " +
                "givesPawns_losing AS givesPawnsWhenWinning, " +
                "givesPawns_total AS givesPawnsTotal " +
        "FROM `players` p " +
                "INNER JOIN `players_stats` s ON p.id = s.player_id " +
        "WHERE p.site = :site " +
                "AND p.username = :player"
    )
    @RegisterBeanMapper(ChessPlayerStats.class)
    public ChessPlayerStats getTargetPlayerStats(@Bind("site") GameSite site, @Bind("player") String player);

    @SqlQuery(
        "SELECT s.id, s.player_id AS playerId, " +

                "n_losing_mate AS nWhenLosingWithMate, " +
                "n_losing_1000 AS nWhenLosingBy1000, " +
                "n_losing_800 AS nWhenLosingBy800to999, " +
                "n_losing_600 AS nWhenLosingBy600to799, " +
                "n_losing_400 AS nWhenLosingBy400to599, " +
                "n_losing_200 AS nWhenLosingBy200to399, " +
                "n_losing_50 AS nWhenLosingBy50to199, " +
                "n_even AS nWhenEven, " +
                "n_winning_50 AS nWhenWinningBy50to199, " +
                "n_winning_200 AS nWhenWinningBy200to399, " +
                "n_winning_400 AS nWhenWinningBy400to599, " +
                "n_winning_600 AS nWhenWinningBy600to799, " +
                "n_winning_800 nWhenWinningBy800to999, " +
                "n_winning_1000 nWhenWinningBy1000, " +
                "n_winning_mate nWhenWinningWithMate, " +
                "n_winning AS nWhenWinning, " +
                "n_losing AS nWhenLosing, " +
                "n_total AS nTotal, " +

                "t1_losing_mate AS t1WhenLosingWithMate, " +
                "t1_losing_1000 AS t1WhenLosingBy1000, " +
                "t1_losing_800 AS t1WhenLosingBy800to999, " +
                "t1_losing_600 AS t1WhenLosingBy600to799, " +
                "t1_losing_400 AS t1WhenLosingBy400to599, " +
                "t1_losing_200 AS t1WhenLosingBy200to399, " +
                "t1_losing_50 AS t1WhenLosingBy50to199, " +
                "t1_even AS t1WhenEven, " +
                "t1_winning_50 AS t1WhenWinningBy50to199, " +
                "t1_winning_200 AS t1WhenWinningBy200to399, " +
                "t1_winning_400 AS t1WhenWinningBy400to599, " +
                "t1_winning_600 AS t1WhenWinningBy600to799, " +
                "t1_winning_800 t1WhenWinningBy800to999, " +
                "t1_winning_1000 t1WhenWinningBy1000, " +
                "t1_winning_mate t1WhenWinningWithMate, " +
                "t1_winning AS t1WhenWinning, " +
                "t1_losing AS t1WhenLosing, " +
                "t1_total AS t1Total, " +

                "t2_losing_mate AS t2WhenLosingWithMate, " +
                "t2_losing_1000 AS t2WhenLosingBy1000, " +
                "t2_losing_800 AS t2WhenLosingBy800to999, " +
                "t2_losing_600 AS t2WhenLosingBy600to799, " +
                "t2_losing_400 AS t2WhenLosingBy400to599, " +
                "t2_losing_200 AS t2WhenLosingBy200to399, " +
                "t2_losing_50 AS t2WhenLosingBy50to199, " +
                "t2_even AS t2WhenEven, " +
                "t2_winning_50 AS t2WhenWinningBy50to199, " +
                "t2_winning_200 AS t2WhenWinningBy200to399, " +
                "t2_winning_400 AS t2WhenWinningBy400to599, " +
                "t2_winning_600 AS t2WhenWinningBy600to799, " +
                "t2_winning_800 AS t2WhenWinningBy800to999, " +
                "t2_winning_1000 AS t2WhenWinningBy1000, " +
                "t2_winning_mate AS t2WhenWinningWithMate, " +
                "t2_winning AS t2WhenWinning, " +
                "t2_losing AS t2WhenLosing, " +
                "t2_total AS t2Total, " +

                "t3_losing_mate AS t3WhenLosingWithMate, " +
                "t3_losing_1000 AS t3WhenLosingBy1000, " +
                "t3_losing_800 AS t3WhenLosingBy800to999, " +
                "t3_losing_600 AS t3WhenLosingBy600to799, " +
                "t3_losing_400 AS t3WhenLosingBy400to599, " +
                "t3_losing_200 AS t3WhenLosingBy200to399, " +
                "t3_losing_50 AS t3WhenLosingBy50to199, " +
                "t3_even AS t3WhenEven, " +
                "t3_winning_50 AS t3WhenWinningBy50to199, " +
                "t3_winning_200 AS t3WhenWinningBy200to399, " +
                "t3_winning_400 AS t3WhenWinningBy400to599, " +
                "t3_winning_600 AS t3WhenWinningBy600to799, " +
                "t3_winning_800 AS t3WhenWinningBy800to999, " +
                "t3_winning_1000 AS t3WhenWinningBy1000, " +
                "t3_winning_mate AS t3WhenWinningWithMate, " +
                "t3_winning AS t3WhenWinning, " +
                "t3_losing AS t3WhenLosing, " +
                "t3_total AS t3Total," +

                "givesMajors_losing_mate AS givesMajorsWhenLosingWithMate, " +
                "givesMajors_losing_1000 AS givesMajorsWhenLosingBy1000, " +
                "givesMajors_losing_800 AS givesMajorsWhenLosingBy800to999, " +
                "givesMajors_losing_600 AS givesMajorsWhenLosingBy600to799, " +
                "givesMajors_losing_400 AS givesMajorsWhenLosingBy400to599, " +
                "givesMajors_losing_200 AS givesMajorsWhenLosingBy200to399, " +
                "givesMajors_losing_50 AS givesMajorsWhenLosingBy50to199, " +
                "givesMajors_even AS givesMajorsWhenEven, " +
                "givesMajors_winning_50 AS givesMajorsWhenWinningBy50to199, " +
                "givesMajors_winning_200 AS givesMajorsWhenWinningBy200to399, " +
                "givesMajors_winning_400 AS givesMajorsWhenWinningBy400to599, " +
                "givesMajors_winning_600 AS givesMajorsWhenWinningBy600to799, " +
                "givesMajors_winning_800 AS givesMajorsWhenWinningBy800to999, " +
                "givesMajors_winning_1000 AS givesMajorsWhenWinningBy1000, " +
                "givesMajors_winning_mate AS givesMajorsWhenWinningWithMate, " +
                "givesMajors_winning AS givesMajorsWhenLosing, " +
                "givesMajors_losing AS givesMajorsWhenWinning, " +
                "givesMajors_total AS givesMajorsTotal," +

                "givesMinors_losing_mate AS givesMinorsWhenLosingWithMate, " +
                "givesMinors_losing_1000 AS givesMinorsWhenLosingBy1000, " +
                "givesMinors_losing_800 AS givesMinorsWhenLosingBy800to999, " +
                "givesMinors_losing_600 AS givesMinorsWhenLosingBy600to799, " +
                "givesMinors_losing_400 AS givesMinorsWhenLosingBy400to599, " +
                "givesMinors_losing_200 AS givesMinorsWhenLosingBy200to399, " +
                "givesMinors_losing_50 AS givesMinorsWhenLosingBy50to199, " +
                "givesMinors_even AS givesMinorsWhenEven, " +
                "givesMinors_winning_50 AS givesMinorsWhenWinningBy50to199, " +
                "givesMinors_winning_200 AS givesMinorsWhenWinningBy200to399, " +
                "givesMinors_winning_400 AS givesMinorsWhenWinningBy400to599, " +
                "givesMinors_winning_600 AS givesMinorsWhenWinningBy600to799, " +
                "givesMinors_winning_800 AS givesMinorsWhenWinningBy800to999, " +
                "givesMinors_winning_1000 AS givesMinorsWhenWinningBy1000, " +
                "givesMinors_winning_mate AS givesMinorsWhenWinningWithMate, " +
                "givesMinors_winning AS givesMinorsWhenLosing, " +
                "givesMinors_losing AS givesMinorsWhenWinning, " +
                "givesMinors_total AS givesMinorsTotal, " +

                "givesPawns_losing_mate AS givesPawnsWhenLosingWithMate, " +
                "givesPawns_losing_1000 AS givesPawnsWhenLosingBy1000, " +
                "givesPawns_losing_800 AS givesPawnsWhenLosingBy800to999, " +
                "givesPawns_losing_600 AS givesPawnsWhenLosingBy600to799, " +
                "givesPawns_losing_400 AS givesPawnsWhenLosingBy400to599, " +
                "givesPawns_losing_200 AS givesPawnsWhenLosingBy200to399, " +
                "givesPawns_losing_50 AS givesPawnsWhenLosingBy50to199, " +
                "givesPawns_even AS givesPawnsWhenEven, " +
                "givesPawns_winning_50 AS givesPawnsWhenWinningBy50to199, " +
                "givesPawns_winning_200 AS givesPawnsWhenWinningBy200to399, " +
                "givesPawns_winning_400 AS givesPawnsWhenWinningBy400to599, " +
                "givesPawns_winning_600 AS givesPawnsWhenWinningBy600to799, " +
                "givesPawns_winning_800 AS givesPawnsWhenWinningBy800to999, " +
                "givesPawns_winning_1000 AS givesPawnsWhenWinningBy1000, " +
                "givesPawns_winning_mate AS givesPawnsWhenWinningWithMate, " +
                "givesPawns_winning AS givesPawnsWhenLosing, " +
                "givesPawns_losing AS givesPawnsWhenWinning, " +
                "givesPawns_total AS givesPawnsTotal " +
        "FROM `players` p " +
                "INNER JOIN `players_stats` s ON p.id = s.player_id " +
        "WHERE p.site = :site " +
                "AND p.flagged = 0"
    )
    @RegisterBeanMapper(ChessPlayerStats.class)
    public List<ChessPlayerStats> getComparablePlayersStats(@Bind("site") GameSite site);

}
