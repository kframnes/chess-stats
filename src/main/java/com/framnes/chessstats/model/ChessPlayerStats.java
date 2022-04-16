package com.framnes.chessstats.model;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Represents a players stats, which are recalculated after new games are imported.
 */
public class ChessPlayerStats {

    private int id;
    private int playerId;

    private int nWhenLosingWithMate;
    private double t1WhenLosingWithMate;
    private double t2WhenLosingWithMate;
    private double t3WhenLosingWithMate;
    private double givesMajorsWhenLosingWithMate;
    private double givesMinorsWhenLosingWithMate;
    private double givesPawnsWhenLosingWithMate;

    private int nWhenLosingBy1000;
    private double t1WhenLosingBy1000;
    private double t2WhenLosingBy1000;
    private double t3WhenLosingBy1000;
    private double givesMajorsWhenLosingBy1000;
    private double givesMinorsWhenLosingBy1000;
    private double givesPawnsWhenLosingBy1000;

    private int nWhenLosingBy800to999;
    private double t1WhenLosingBy800to999;
    private double t2WhenLosingBy800to999;
    private double t3WhenLosingBy800to999;
    private double givesMajorsWhenLosingBy800to999;
    private double givesMinorsWhenLosingBy800to999;
    private double givesPawnsWhenLosingBy800to999;

    private int nWhenLosingBy600to799;
    private double t1WhenLosingBy600to799;
    private double t2WhenLosingBy600to799;
    private double t3WhenLosingBy600to799;
    private double givesMajorsWhenLosingBy600to799;
    private double givesMinorsWhenLosingBy600to799;
    private double givesPawnsWhenLosingBy600to799;

    private int nWhenLosingBy400to599;
    private double t1WhenLosingBy400to599;
    private double t2WhenLosingBy400to599;
    private double t3WhenLosingBy400to599;
    private double givesMajorsWhenLosingBy400to599;
    private double givesMinorsWhenLosingBy400to599;
    private double givesPawnsWhenLosingBy400to599;

    private int nWhenLosingBy200to399;
    private double t1WhenLosingBy200to399;
    private double t2WhenLosingBy200to399;
    private double t3WhenLosingBy200to399;
    private double givesMajorsWhenLosingBy200to399;
    private double givesMinorsWhenLosingBy200to399;
    private double givesPawnsWhenLosingBy200to399;

    private int nWhenLosingBy50to199;
    private double t1WhenLosingBy50to199;
    private double t2WhenLosingBy50to199;
    private double t3WhenLosingBy50to199;
    private double givesMajorsWhenLosingBy50to199;
    private double givesMinorsWhenLosingBy50to199;
    private double givesPawnsWhenLosingBy50to199;

    private int nWhenEven;
    private double t1WhenEven;
    private double t2WhenEven;
    private double t3WhenEven;
    private double givesMajorsWhenEven;
    private double givesMinorsWhenEven;
    private double givesPawnsWhenEven;

    private int nWhenWinningBy50to199;
    private double t1WhenWinningBy50to199;
    private double t2WhenWinningBy50to199;
    private double t3WhenWinningBy50to199;
    private double givesMajorsWhenWinningBy50to199;
    private double givesMinorsWhenWinningBy50to199;
    private double givesPawnsWhenWinningBy50to199;

    private int nWhenWinningBy200to399;
    private double t1WhenWinningBy200to399;
    private double t2WhenWinningBy200to399;
    private double t3WhenWinningBy200to399;
    private double givesMajorsWhenWinningBy200to399;
    private double givesMinorsWhenWinningBy200to399;
    private double givesPawnsWhenWinningBy200to399;

    private int nWhenWinningBy400to599;
    private double t1WhenWinningBy400to599;
    private double t2WhenWinningBy400to599;
    private double t3WhenWinningBy400to599;
    private double givesMajorsWhenWinningBy400to599;
    private double givesMinorsWhenWinningBy400to599;
    private double givesPawnsWhenWinningBy400to599;

    private int nWhenWinningBy600to799;
    private double t1WhenWinningBy600to799;
    private double t2WhenWinningBy600to799;
    private double t3WhenWinningBy600to799;
    private double givesMajorsWhenWinningBy600to799;
    private double givesMinorsWhenWinningBy600to799;
    private double givesPawnsWhenWinningBy600to799;

    private int nWhenWinningBy800to999;
    private double t1WhenWinningBy800to999;
    private double t2WhenWinningBy800to999;
    private double t3WhenWinningBy800to999;
    private double givesMajorsWhenWinningBy800to999;
    private double givesMinorsWhenWinningBy800to999;
    private double givesPawnsWhenWinningBy800to999;

    private int nWhenWinningBy1000;
    private double t1WhenWinningBy1000;
    private double t2WhenWinningBy1000;
    private double t3WhenWinningBy1000;
    private double givesMajorsWhenWinningBy1000;
    private double givesMinorsWhenWinningBy1000;
    private double givesPawnsWhenWinningBy1000;

    private int nWhenWinningWithMate;
    private double t1WhenWinningWithMate;
    private double t2WhenWinningWithMate;
    private double t3WhenWinningWithMate;
    private double givesMajorsWhenWinningWithMate;
    private double givesMinorsWhenWinningWithMate;
    private double givesPawnsWhenWinningWithMate;
    
    private int nWhenLosing;
    private double t1WhenLosing;
    private double t2WhenLosing;
    private double t3WhenLosing;
    private double givesMajorsWhenLosing;
    private double givesMinorsWhenLosing;
    private double givesPawnsWhenLosing;

    private int nWhenWinning;
    private double t1WhenWinning;
    private double t2WhenWinning;
    private double t3WhenWinning;
    private double givesMajorsWhenWinning;
    private double givesMinorsWhenWinning;
    private double givesPawnsWhenWinning;

    private int nTotal;
    private double t1Total;
    private double t2Total;
    private double t3Total;
    private double givesMajorsTotal;
    private double givesMinorsTotal;
    private double givesPawnsTotal;

    public ChessPlayerStats() {}

    public ChessPlayerStats(ChessPlayer player, List<ChessMove> moves) {

        this.playerId = player.getId();

        setStats(moves, (move) -> move.getMateInBefore() != null && move.getMateInBefore() < 0,
                this::setnWhenLosingWithMate,
                this::setT1WhenLosingWithMate, this::setT2WhenLosingWithMate, this::setT3WhenLosingWithMate,
                this::setGivesPawnsWhenLosingWithMate, this::setGivesMinorsWhenLosingWithMate, this::setGivesMajorsWhenLosingWithMate);
        setStats(moves, (move) -> move.getPositionEvalBefore() <= -1000,
                this::setnWhenLosingBy1000,
                this::setT1WhenLosingBy1000, this::setT2WhenLosingBy1000, this::setT3WhenLosingBy1000,
                this::setGivesPawnsWhenLosingBy1000, this::setGivesMinorsWhenLosingBy1000, this::setGivesMajorsWhenLosingBy1000);
        setStats(moves, (move) -> move.getPositionEvalBefore() <= -800 && move.getPositionEvalBefore() > -1000,
                this::setnWhenLosingBy800to999,
                this::setT1WhenLosingBy800to999, this::setT2WhenLosingBy800to999, this::setT3WhenLosingBy800to999,
                this::setGivesPawnsWhenLosingBy800to999, this::setGivesMinorsWhenLosingBy800to999, this::setGivesMajorsWhenLosingBy800to999);
        setStats(moves, (move) -> move.getPositionEvalBefore() <= -600 && move.getPositionEvalBefore() > -800,
                this::setnWhenLosingBy600to799,
                this::setT1WhenLosingBy600to799, this::setT2WhenLosingBy600to799, this::setT3WhenLosingBy600to799,
                this::setGivesPawnsWhenLosingBy600to799, this::setGivesMinorsWhenLosingBy600to799, this::setGivesMajorsWhenLosingBy600to799);
        setStats(moves, (move) -> move.getPositionEvalBefore() <= -400 && move.getPositionEvalBefore() > -600,
                this::setnWhenLosingBy400to599,
                this::setT1WhenLosingBy400to599, this::setT2WhenLosingBy400to599, this::setT3WhenLosingBy400to599,
                this::setGivesPawnsWhenLosingBy400to599, this::setGivesMinorsWhenLosingBy400to599, this::setGivesMajorsWhenLosingBy400to599);
        setStats(moves, (move) -> move.getPositionEvalBefore() <= -200 && move.getPositionEvalBefore() > -400,
                this::setnWhenLosingBy200to399,
                this::setT1WhenLosingBy200to399, this::setT2WhenLosingBy200to399, this::setT3WhenLosingBy200to399,
                this::setGivesPawnsWhenLosingBy200to399, this::setGivesMinorsWhenLosingBy200to399, this::setGivesMajorsWhenLosingBy200to399);
        setStats(moves, (move) -> move.getPositionEvalBefore() <= -50 && move.getPositionEvalBefore() > -200,
                this::setnWhenLosingBy50to199,
                this::setT1WhenLosingBy50to199, this::setT2WhenLosingBy50to199, this::setT3WhenLosingBy50to199,
                this::setGivesPawnsWhenLosingBy50to199, this::setGivesMinorsWhenLosingBy50to199, this::setGivesMajorsWhenLosingBy50to199);
        setStats(moves, (move) -> move.getPositionEvalBefore() < 50 && move.getPositionEvalBefore() > -50,
                this::setnWhenEven,
                this::setT1WhenEven, this::setT2WhenEven, this::setT3WhenEven,
                this::setGivesPawnsWhenEven, this::setGivesMinorsWhenEven, this::setGivesMajorsWhenEven);
        setStats(moves, (move) -> move.getPositionEvalBefore() >= 50 && move.getPositionEvalBefore() < 200,
                this::setnWhenWinningBy50to199,
                this::setT1WhenWinningBy50to199, this::setT2WhenWinningBy50to199, this::setT3WhenWinningBy50to199,
                this::setGivesPawnsWhenWinningBy50to199, this::setGivesMinorsWhenWinningBy50to199, this::setGivesMajorsWhenWinningBy50to199);
        setStats(moves, (move) -> move.getPositionEvalBefore() >= 200 && move.getPositionEvalBefore() < 400,
                this::setnWhenWinningBy200to399,
                this::setT1WhenWinningBy200to399, this::setT2WhenWinningBy200to399, this::setT3WhenWinningBy200to399,
                this::setGivesPawnsWhenWinningBy200to399, this::setGivesMinorsWhenWinningBy200to399, this::setGivesMajorsWhenWinningBy200to399);
        setStats(moves, (move) -> move.getPositionEvalBefore() >= 400 && move.getPositionEvalBefore() < 600,
                this::setnWhenWinningBy400to599,
                this::setT1WhenWinningBy400to599, this::setT2WhenWinningBy400to599, this::setT3WhenWinningBy400to599,
                this::setGivesPawnsWhenWinningBy400to599, this::setGivesMinorsWhenWinningBy400to599, this::setGivesMajorsWhenWinningBy400to599);
        setStats(moves, (move) -> move.getPositionEvalBefore() >= 600 && move.getPositionEvalBefore() < 800,
                this::setnWhenWinningBy600to799,
                this::setT1WhenWinningBy600to799, this::setT2WhenWinningBy600to799, this::setT3WhenWinningBy600to799,
                this::setGivesPawnsWhenWinningBy600to799, this::setGivesMinorsWhenWinningBy600to799, this::setGivesMajorsWhenWinningBy600to799);
        setStats(moves, (move) -> move.getPositionEvalBefore() >= 800 && move.getPositionEvalBefore() < 1000,
                this::setnWhenWinningBy800to999,
                this::setT1WhenWinningBy800to999, this::setT2WhenWinningBy800to999, this::setT3WhenWinningBy800to999,
                this::setGivesPawnsWhenWinningBy800to999, this::setGivesMinorsWhenWinningBy800to999, this::setGivesMajorsWhenWinningBy800to999);
        setStats(moves, (move) -> move.getPositionEvalBefore() >= 1000,
                this::setnWhenWinningBy1000,
                this::setT1WhenWinningBy1000, this::setT2WhenWinningBy1000, this::setT3WhenWinningBy1000,
                this::setGivesPawnsWhenWinningBy1000, this::setGivesMinorsWhenWinningBy1000, this::setGivesMajorsWhenWinningBy1000);
        setStats(moves, (move) -> move.getMateInBefore() != null && move.getMateInBefore() > 0,
                this::setnWhenWinningWithMate,
                this::setT1WhenWinningWithMate, this::setT2WhenWinningWithMate, this::setT3WhenWinningWithMate,
                this::setGivesPawnsWhenWinningWithMate, this::setGivesMinorsWhenWinningWithMate, this::setGivesMajorsWhenWinningWithMate);
        setStats(moves, (move) -> move.getPositionEvalBefore() <= -50,
                this::setnWhenLosing,
                this::setT1WhenLosing, this::setT2WhenLosing, this::setT3WhenLosing,
                this::setGivesPawnsWhenLosing, this::setGivesMinorsWhenLosing, this::setGivesMajorsWhenLosing);
        setStats(moves, (move) -> move.getPositionEvalBefore() >= 50,
                this::setnWhenWinning,
                this::setT1WhenWinning, this::setT2WhenWinning, this::setT3WhenWinning,
                this::setGivesPawnsWhenWinning, this::setGivesMinorsWhenWinning, this::setGivesMajorsWhenWinning);
        setStats(moves, (move) -> true,
                this::setnTotal,
                this::setT1Total, this::setT2Total, this::setT3Total,
                this::setGivesPawnsTotal, this::setGivesMinorsTotal, this::setGivesMajorsTotal);

    }

    private void setStats(List<ChessMove> moves, Predicate<ChessMove> predicate,
                          Consumer<Integer> setN,
                          Consumer<Double> setT1,
                          Consumer<Double> setT2,
                          Consumer<Double> setT3,
                          Consumer<Double> setPawnsLoss,
                          Consumer<Double> setMinorsLoss,
                          Consumer<Double> setMajorsLoss) {

        // Collect in-scope moves
        List<ChessMove> targetSliceMoves = moves.stream()
                .filter(predicate)
                .collect(Collectors.toList());

        int targetN = targetSliceMoves.size();
        long targetT1Counts = targetSliceMoves.stream()
                .filter(move -> move.getMoveEngineRank() != null && move.getMoveEngineRank() <= 1)
                .count();
        long targetT2Counts = targetSliceMoves.stream()
                .filter(move -> move.getMoveEngineRank() != null && move.getMoveEngineRank() <= 2)
                .count();
        long targetT3Counts = targetSliceMoves.stream()
                .filter(move -> move.getMoveEngineRank() != null && move.getMoveEngineRank() <= 3)
                .count();
        long targetPawnsLossCounts = targetSliceMoves.stream()
                .filter(move -> move.getPositionEvalAfter() - move.getPositionEvalBefore() < -100)
                .count();
        long targetMinorsLossCounts = targetSliceMoves.stream()
                .filter(move -> move.getPositionEvalAfter() - move.getPositionEvalBefore() < -300)
                .count();
        long targetMajorsLossCounts = targetSliceMoves.stream()
                .filter(move -> move.getPositionEvalAfter() - move.getPositionEvalBefore() < -500)
                .count();

        setN.accept(targetN);
        setT1.accept((targetN > 0) ? 100.0 * targetT1Counts / (double) targetN : 0);
        setT2.accept((targetN > 0) ? 100.0 * targetT2Counts / (double) targetN : 0);
        setT3.accept((targetN > 0) ? 100.0 * targetT3Counts / (double) targetN : 0);
        setPawnsLoss.accept((targetN > 0) ? 100.0 * targetPawnsLossCounts / (double) targetN : 0);
        setMinorsLoss.accept((targetN > 0) ? 100.0 * targetMinorsLossCounts / (double) targetN : 0);
        setMajorsLoss.accept((targetN > 0) ? 100.0 * targetMajorsLossCounts / (double) targetN : 0);

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int getnWhenLosingWithMate() {
        return nWhenLosingWithMate;
    }

    public void setnWhenLosingWithMate(int nWhenLosingWithMate) {
        this.nWhenLosingWithMate = nWhenLosingWithMate;
    }

    public double getT1WhenLosingWithMate() {
        return t1WhenLosingWithMate;
    }

    public void setT1WhenLosingWithMate(double t1WhenLosingWithMate) {
        this.t1WhenLosingWithMate = t1WhenLosingWithMate;
    }

    public double getT2WhenLosingWithMate() {
        return t2WhenLosingWithMate;
    }

    public void setT2WhenLosingWithMate(double t2WhenLosingWithMate) {
        this.t2WhenLosingWithMate = t2WhenLosingWithMate;
    }

    public double getT3WhenLosingWithMate() {
        return t3WhenLosingWithMate;
    }

    public void setT3WhenLosingWithMate(double t3WhenLosingWithMate) {
        this.t3WhenLosingWithMate = t3WhenLosingWithMate;
    }

    public double getGivesMajorsWhenLosingWithMate() {
        return givesMajorsWhenLosingWithMate;
    }

    public void setGivesMajorsWhenLosingWithMate(double givesMajorsWhenLosingWithMate) {
        this.givesMajorsWhenLosingWithMate = givesMajorsWhenLosingWithMate;
    }

    public double getGivesMinorsWhenLosingWithMate() {
        return givesMinorsWhenLosingWithMate;
    }

    public void setGivesMinorsWhenLosingWithMate(double givesMinorsWhenLosingWithMate) {
        this.givesMinorsWhenLosingWithMate = givesMinorsWhenLosingWithMate;
    }

    public double getGivesPawnsWhenLosingWithMate() {
        return givesPawnsWhenLosingWithMate;
    }

    public void setGivesPawnsWhenLosingWithMate(double givesPawnsWhenLosingWithMate) {
        this.givesPawnsWhenLosingWithMate = givesPawnsWhenLosingWithMate;
    }

    public int getnWhenLosingBy1000() {
        return nWhenLosingBy1000;
    }

    public void setnWhenLosingBy1000(int nWhenLosingBy1000) {
        this.nWhenLosingBy1000 = nWhenLosingBy1000;
    }

    public double getT1WhenLosingBy1000() {
        return t1WhenLosingBy1000;
    }

    public void setT1WhenLosingBy1000(double t1WhenLosingBy1000) {
        this.t1WhenLosingBy1000 = t1WhenLosingBy1000;
    }

    public double getT2WhenLosingBy1000() {
        return t2WhenLosingBy1000;
    }

    public void setT2WhenLosingBy1000(double t2WhenLosingBy1000) {
        this.t2WhenLosingBy1000 = t2WhenLosingBy1000;
    }

    public double getT3WhenLosingBy1000() {
        return t3WhenLosingBy1000;
    }

    public void setT3WhenLosingBy1000(double t3WhenLosingBy1000) {
        this.t3WhenLosingBy1000 = t3WhenLosingBy1000;
    }

    public double getGivesMajorsWhenLosingBy1000() {
        return givesMajorsWhenLosingBy1000;
    }

    public void setGivesMajorsWhenLosingBy1000(double givesMajorsWhenLosingBy1000) {
        this.givesMajorsWhenLosingBy1000 = givesMajorsWhenLosingBy1000;
    }

    public double getGivesMinorsWhenLosingBy1000() {
        return givesMinorsWhenLosingBy1000;
    }

    public void setGivesMinorsWhenLosingBy1000(double givesMinorsWhenLosingBy1000) {
        this.givesMinorsWhenLosingBy1000 = givesMinorsWhenLosingBy1000;
    }

    public double getGivesPawnsWhenLosingBy1000() {
        return givesPawnsWhenLosingBy1000;
    }

    public void setGivesPawnsWhenLosingBy1000(double givesPawnsWhenLosingBy1000) {
        this.givesPawnsWhenLosingBy1000 = givesPawnsWhenLosingBy1000;
    }

    public int getnWhenLosingBy800to999() {
        return nWhenLosingBy800to999;
    }

    public void setnWhenLosingBy800to999(int nWhenLosingBy800to999) {
        this.nWhenLosingBy800to999 = nWhenLosingBy800to999;
    }

    public double getT1WhenLosingBy800to999() {
        return t1WhenLosingBy800to999;
    }

    public void setT1WhenLosingBy800to999(double t1WhenLosingBy800to999) {
        this.t1WhenLosingBy800to999 = t1WhenLosingBy800to999;
    }

    public double getT2WhenLosingBy800to999() {
        return t2WhenLosingBy800to999;
    }

    public void setT2WhenLosingBy800to999(double t2WhenLosingBy800to999) {
        this.t2WhenLosingBy800to999 = t2WhenLosingBy800to999;
    }

    public double getT3WhenLosingBy800to999() {
        return t3WhenLosingBy800to999;
    }

    public void setT3WhenLosingBy800to999(double t3WhenLosingBy800to999) {
        this.t3WhenLosingBy800to999 = t3WhenLosingBy800to999;
    }

    public double getGivesMajorsWhenLosingBy800to999() {
        return givesMajorsWhenLosingBy800to999;
    }

    public void setGivesMajorsWhenLosingBy800to999(double givesMajorsWhenLosingBy800to999) {
        this.givesMajorsWhenLosingBy800to999 = givesMajorsWhenLosingBy800to999;
    }

    public double getGivesMinorsWhenLosingBy800to999() {
        return givesMinorsWhenLosingBy800to999;
    }

    public void setGivesMinorsWhenLosingBy800to999(double givesMinorsWhenLosingBy800to999) {
        this.givesMinorsWhenLosingBy800to999 = givesMinorsWhenLosingBy800to999;
    }

    public double getGivesPawnsWhenLosingBy800to999() {
        return givesPawnsWhenLosingBy800to999;
    }

    public void setGivesPawnsWhenLosingBy800to999(double givesPawnsWhenLosingBy800to999) {
        this.givesPawnsWhenLosingBy800to999 = givesPawnsWhenLosingBy800to999;
    }

    public int getnWhenLosingBy600to799() {
        return nWhenLosingBy600to799;
    }

    public void setnWhenLosingBy600to799(int nWhenLosingBy600to799) {
        this.nWhenLosingBy600to799 = nWhenLosingBy600to799;
    }

    public double getT1WhenLosingBy600to799() {
        return t1WhenLosingBy600to799;
    }

    public void setT1WhenLosingBy600to799(double t1WhenLosingBy600to799) {
        this.t1WhenLosingBy600to799 = t1WhenLosingBy600to799;
    }

    public double getT2WhenLosingBy600to799() {
        return t2WhenLosingBy600to799;
    }

    public void setT2WhenLosingBy600to799(double t2WhenLosingBy600to799) {
        this.t2WhenLosingBy600to799 = t2WhenLosingBy600to799;
    }

    public double getT3WhenLosingBy600to799() {
        return t3WhenLosingBy600to799;
    }

    public void setT3WhenLosingBy600to799(double t3WhenLosingBy600to799) {
        this.t3WhenLosingBy600to799 = t3WhenLosingBy600to799;
    }

    public double getGivesMajorsWhenLosingBy600to799() {
        return givesMajorsWhenLosingBy600to799;
    }

    public void setGivesMajorsWhenLosingBy600to799(double givesMajorsWhenLosingBy600to799) {
        this.givesMajorsWhenLosingBy600to799 = givesMajorsWhenLosingBy600to799;
    }

    public double getGivesMinorsWhenLosingBy600to799() {
        return givesMinorsWhenLosingBy600to799;
    }

    public void setGivesMinorsWhenLosingBy600to799(double givesMinorsWhenLosingBy600to799) {
        this.givesMinorsWhenLosingBy600to799 = givesMinorsWhenLosingBy600to799;
    }

    public double getGivesPawnsWhenLosingBy600to799() {
        return givesPawnsWhenLosingBy600to799;
    }

    public void setGivesPawnsWhenLosingBy600to799(double givesPawnsWhenLosingBy600to799) {
        this.givesPawnsWhenLosingBy600to799 = givesPawnsWhenLosingBy600to799;
    }

    public int getnWhenLosingBy400to599() {
        return nWhenLosingBy400to599;
    }

    public void setnWhenLosingBy400to599(int nWhenLosingBy400to599) {
        this.nWhenLosingBy400to599 = nWhenLosingBy400to599;
    }

    public double getT1WhenLosingBy400to599() {
        return t1WhenLosingBy400to599;
    }

    public void setT1WhenLosingBy400to599(double t1WhenLosingBy400to599) {
        this.t1WhenLosingBy400to599 = t1WhenLosingBy400to599;
    }

    public double getT2WhenLosingBy400to599() {
        return t2WhenLosingBy400to599;
    }

    public void setT2WhenLosingBy400to599(double t2WhenLosingBy400to599) {
        this.t2WhenLosingBy400to599 = t2WhenLosingBy400to599;
    }

    public double getT3WhenLosingBy400to599() {
        return t3WhenLosingBy400to599;
    }

    public void setT3WhenLosingBy400to599(double t3WhenLosingBy400to599) {
        this.t3WhenLosingBy400to599 = t3WhenLosingBy400to599;
    }

    public double getGivesMajorsWhenLosingBy400to599() {
        return givesMajorsWhenLosingBy400to599;
    }

    public void setGivesMajorsWhenLosingBy400to599(double givesMajorsWhenLosingBy400to599) {
        this.givesMajorsWhenLosingBy400to599 = givesMajorsWhenLosingBy400to599;
    }

    public double getGivesMinorsWhenLosingBy400to599() {
        return givesMinorsWhenLosingBy400to599;
    }

    public void setGivesMinorsWhenLosingBy400to599(double givesMinorsWhenLosingBy400to599) {
        this.givesMinorsWhenLosingBy400to599 = givesMinorsWhenLosingBy400to599;
    }

    public double getGivesPawnsWhenLosingBy400to599() {
        return givesPawnsWhenLosingBy400to599;
    }

    public void setGivesPawnsWhenLosingBy400to599(double givesPawnsWhenLosingBy400to599) {
        this.givesPawnsWhenLosingBy400to599 = givesPawnsWhenLosingBy400to599;
    }

    public int getnWhenLosingBy200to399() {
        return nWhenLosingBy200to399;
    }

    public void setnWhenLosingBy200to399(int nWhenLosingBy200to399) {
        this.nWhenLosingBy200to399 = nWhenLosingBy200to399;
    }

    public double getT1WhenLosingBy200to399() {
        return t1WhenLosingBy200to399;
    }

    public void setT1WhenLosingBy200to399(double t1WhenLosingBy200to399) {
        this.t1WhenLosingBy200to399 = t1WhenLosingBy200to399;
    }

    public double getT2WhenLosingBy200to399() {
        return t2WhenLosingBy200to399;
    }

    public void setT2WhenLosingBy200to399(double t2WhenLosingBy200to399) {
        this.t2WhenLosingBy200to399 = t2WhenLosingBy200to399;
    }

    public double getT3WhenLosingBy200to399() {
        return t3WhenLosingBy200to399;
    }

    public void setT3WhenLosingBy200to399(double t3WhenLosingBy200to399) {
        this.t3WhenLosingBy200to399 = t3WhenLosingBy200to399;
    }

    public double getGivesMajorsWhenLosingBy200to399() {
        return givesMajorsWhenLosingBy200to399;
    }

    public void setGivesMajorsWhenLosingBy200to399(double givesMajorsWhenLosingBy200to399) {
        this.givesMajorsWhenLosingBy200to399 = givesMajorsWhenLosingBy200to399;
    }

    public double getGivesMinorsWhenLosingBy200to399() {
        return givesMinorsWhenLosingBy200to399;
    }

    public void setGivesMinorsWhenLosingBy200to399(double givesMinorsWhenLosingBy200to399) {
        this.givesMinorsWhenLosingBy200to399 = givesMinorsWhenLosingBy200to399;
    }

    public double getGivesPawnsWhenLosingBy200to399() {
        return givesPawnsWhenLosingBy200to399;
    }

    public void setGivesPawnsWhenLosingBy200to399(double givesPawnsWhenLosingBy200to399) {
        this.givesPawnsWhenLosingBy200to399 = givesPawnsWhenLosingBy200to399;
    }

    public int getnWhenLosingBy50to199() {
        return nWhenLosingBy50to199;
    }

    public void setnWhenLosingBy50to199(int nWhenLosingBy50to199) {
        this.nWhenLosingBy50to199 = nWhenLosingBy50to199;
    }

    public double getT1WhenLosingBy50to199() {
        return t1WhenLosingBy50to199;
    }

    public void setT1WhenLosingBy50to199(double t1WhenLosingBy50to199) {
        this.t1WhenLosingBy50to199 = t1WhenLosingBy50to199;
    }

    public double getT2WhenLosingBy50to199() {
        return t2WhenLosingBy50to199;
    }

    public void setT2WhenLosingBy50to199(double t2WhenLosingBy50to199) {
        this.t2WhenLosingBy50to199 = t2WhenLosingBy50to199;
    }

    public double getT3WhenLosingBy50to199() {
        return t3WhenLosingBy50to199;
    }

    public void setT3WhenLosingBy50to199(double t3WhenLosingBy50to199) {
        this.t3WhenLosingBy50to199 = t3WhenLosingBy50to199;
    }

    public double getGivesMajorsWhenLosingBy50to199() {
        return givesMajorsWhenLosingBy50to199;
    }

    public void setGivesMajorsWhenLosingBy50to199(double givesMajorsWhenLosingBy50to199) {
        this.givesMajorsWhenLosingBy50to199 = givesMajorsWhenLosingBy50to199;
    }

    public double getGivesMinorsWhenLosingBy50to199() {
        return givesMinorsWhenLosingBy50to199;
    }

    public void setGivesMinorsWhenLosingBy50to199(double givesMinorsWhenLosingBy50to199) {
        this.givesMinorsWhenLosingBy50to199 = givesMinorsWhenLosingBy50to199;
    }

    public double getGivesPawnsWhenLosingBy50to199() {
        return givesPawnsWhenLosingBy50to199;
    }

    public void setGivesPawnsWhenLosingBy50to199(double givesPawnsWhenLosingBy50to199) {
        this.givesPawnsWhenLosingBy50to199 = givesPawnsWhenLosingBy50to199;
    }

    public int getnWhenEven() {
        return nWhenEven;
    }

    public void setnWhenEven(int nWhenEven) {
        this.nWhenEven = nWhenEven;
    }

    public double getT1WhenEven() {
        return t1WhenEven;
    }

    public void setT1WhenEven(double t1WhenEven) {
        this.t1WhenEven = t1WhenEven;
    }

    public double getT2WhenEven() {
        return t2WhenEven;
    }

    public void setT2WhenEven(double t2WhenEven) {
        this.t2WhenEven = t2WhenEven;
    }

    public double getT3WhenEven() {
        return t3WhenEven;
    }

    public void setT3WhenEven(double t3WhenEven) {
        this.t3WhenEven = t3WhenEven;
    }

    public double getGivesMajorsWhenEven() {
        return givesMajorsWhenEven;
    }

    public void setGivesMajorsWhenEven(double givesMajorsWhenEven) {
        this.givesMajorsWhenEven = givesMajorsWhenEven;
    }

    public double getGivesMinorsWhenEven() {
        return givesMinorsWhenEven;
    }

    public void setGivesMinorsWhenEven(double givesMinorsWhenEven) {
        this.givesMinorsWhenEven = givesMinorsWhenEven;
    }

    public double getGivesPawnsWhenEven() {
        return givesPawnsWhenEven;
    }

    public void setGivesPawnsWhenEven(double givesPawnsWhenEven) {
        this.givesPawnsWhenEven = givesPawnsWhenEven;
    }

    public int getnWhenWinningBy50to199() {
        return nWhenWinningBy50to199;
    }

    public void setnWhenWinningBy50to199(int nWhenWinningBy50to199) {
        this.nWhenWinningBy50to199 = nWhenWinningBy50to199;
    }

    public double getT1WhenWinningBy50to199() {
        return t1WhenWinningBy50to199;
    }

    public void setT1WhenWinningBy50to199(double t1WhenWinningBy50to199) {
        this.t1WhenWinningBy50to199 = t1WhenWinningBy50to199;
    }

    public double getT2WhenWinningBy50to199() {
        return t2WhenWinningBy50to199;
    }

    public void setT2WhenWinningBy50to199(double t2WhenWinningBy50to199) {
        this.t2WhenWinningBy50to199 = t2WhenWinningBy50to199;
    }

    public double getT3WhenWinningBy50to199() {
        return t3WhenWinningBy50to199;
    }

    public void setT3WhenWinningBy50to199(double t3WhenWinningBy50to199) {
        this.t3WhenWinningBy50to199 = t3WhenWinningBy50to199;
    }

    public double getGivesMajorsWhenWinningBy50to199() {
        return givesMajorsWhenWinningBy50to199;
    }

    public void setGivesMajorsWhenWinningBy50to199(double givesMajorsWhenWinningBy50to199) {
        this.givesMajorsWhenWinningBy50to199 = givesMajorsWhenWinningBy50to199;
    }

    public double getGivesMinorsWhenWinningBy50to199() {
        return givesMinorsWhenWinningBy50to199;
    }

    public void setGivesMinorsWhenWinningBy50to199(double givesMinorsWhenWinningBy50to199) {
        this.givesMinorsWhenWinningBy50to199 = givesMinorsWhenWinningBy50to199;
    }

    public double getGivesPawnsWhenWinningBy50to199() {
        return givesPawnsWhenWinningBy50to199;
    }

    public void setGivesPawnsWhenWinningBy50to199(double givesPawnsWhenWinningBy50to199) {
        this.givesPawnsWhenWinningBy50to199 = givesPawnsWhenWinningBy50to199;
    }

    public int getnWhenWinningBy200to399() {
        return nWhenWinningBy200to399;
    }

    public void setnWhenWinningBy200to399(int nWhenWinningBy200to399) {
        this.nWhenWinningBy200to399 = nWhenWinningBy200to399;
    }

    public double getT1WhenWinningBy200to399() {
        return t1WhenWinningBy200to399;
    }

    public void setT1WhenWinningBy200to399(double t1WhenWinningBy200to399) {
        this.t1WhenWinningBy200to399 = t1WhenWinningBy200to399;
    }

    public double getT2WhenWinningBy200to399() {
        return t2WhenWinningBy200to399;
    }

    public void setT2WhenWinningBy200to399(double t2WhenWinningBy200to399) {
        this.t2WhenWinningBy200to399 = t2WhenWinningBy200to399;
    }

    public double getT3WhenWinningBy200to399() {
        return t3WhenWinningBy200to399;
    }

    public void setT3WhenWinningBy200to399(double t3WhenWinningBy200to399) {
        this.t3WhenWinningBy200to399 = t3WhenWinningBy200to399;
    }

    public double getGivesMajorsWhenWinningBy200to399() {
        return givesMajorsWhenWinningBy200to399;
    }

    public void setGivesMajorsWhenWinningBy200to399(double givesMajorsWhenWinningBy200to399) {
        this.givesMajorsWhenWinningBy200to399 = givesMajorsWhenWinningBy200to399;
    }

    public double getGivesMinorsWhenWinningBy200to399() {
        return givesMinorsWhenWinningBy200to399;
    }

    public void setGivesMinorsWhenWinningBy200to399(double givesMinorsWhenWinningBy200to399) {
        this.givesMinorsWhenWinningBy200to399 = givesMinorsWhenWinningBy200to399;
    }

    public double getGivesPawnsWhenWinningBy200to399() {
        return givesPawnsWhenWinningBy200to399;
    }

    public void setGivesPawnsWhenWinningBy200to399(double givesPawnsWhenWinningBy200to399) {
        this.givesPawnsWhenWinningBy200to399 = givesPawnsWhenWinningBy200to399;
    }

    public int getnWhenWinningBy400to599() {
        return nWhenWinningBy400to599;
    }

    public void setnWhenWinningBy400to599(int nWhenWinningBy400to599) {
        this.nWhenWinningBy400to599 = nWhenWinningBy400to599;
    }

    public double getT1WhenWinningBy400to599() {
        return t1WhenWinningBy400to599;
    }

    public void setT1WhenWinningBy400to599(double t1WhenWinningBy400to599) {
        this.t1WhenWinningBy400to599 = t1WhenWinningBy400to599;
    }

    public double getT2WhenWinningBy400to599() {
        return t2WhenWinningBy400to599;
    }

    public void setT2WhenWinningBy400to599(double t2WhenWinningBy400to599) {
        this.t2WhenWinningBy400to599 = t2WhenWinningBy400to599;
    }

    public double getT3WhenWinningBy400to599() {
        return t3WhenWinningBy400to599;
    }

    public void setT3WhenWinningBy400to599(double t3WhenWinningBy400to599) {
        this.t3WhenWinningBy400to599 = t3WhenWinningBy400to599;
    }

    public double getGivesMajorsWhenWinningBy400to599() {
        return givesMajorsWhenWinningBy400to599;
    }

    public void setGivesMajorsWhenWinningBy400to599(double givesMajorsWhenWinningBy400to599) {
        this.givesMajorsWhenWinningBy400to599 = givesMajorsWhenWinningBy400to599;
    }

    public double getGivesMinorsWhenWinningBy400to599() {
        return givesMinorsWhenWinningBy400to599;
    }

    public void setGivesMinorsWhenWinningBy400to599(double givesMinorsWhenWinningBy400to599) {
        this.givesMinorsWhenWinningBy400to599 = givesMinorsWhenWinningBy400to599;
    }

    public double getGivesPawnsWhenWinningBy400to599() {
        return givesPawnsWhenWinningBy400to599;
    }

    public void setGivesPawnsWhenWinningBy400to599(double givesPawnsWhenWinningBy400to599) {
        this.givesPawnsWhenWinningBy400to599 = givesPawnsWhenWinningBy400to599;
    }

    public int getnWhenWinningBy600to799() {
        return nWhenWinningBy600to799;
    }

    public void setnWhenWinningBy600to799(int nWhenWinningBy600to799) {
        this.nWhenWinningBy600to799 = nWhenWinningBy600to799;
    }

    public double getT1WhenWinningBy600to799() {
        return t1WhenWinningBy600to799;
    }

    public void setT1WhenWinningBy600to799(double t1WhenWinningBy600to799) {
        this.t1WhenWinningBy600to799 = t1WhenWinningBy600to799;
    }

    public double getT2WhenWinningBy600to799() {
        return t2WhenWinningBy600to799;
    }

    public void setT2WhenWinningBy600to799(double t2WhenWinningBy600to799) {
        this.t2WhenWinningBy600to799 = t2WhenWinningBy600to799;
    }

    public double getT3WhenWinningBy600to799() {
        return t3WhenWinningBy600to799;
    }

    public void setT3WhenWinningBy600to799(double t3WhenWinningBy600to799) {
        this.t3WhenWinningBy600to799 = t3WhenWinningBy600to799;
    }

    public double getGivesMajorsWhenWinningBy600to799() {
        return givesMajorsWhenWinningBy600to799;
    }

    public void setGivesMajorsWhenWinningBy600to799(double givesMajorsWhenWinningBy600to799) {
        this.givesMajorsWhenWinningBy600to799 = givesMajorsWhenWinningBy600to799;
    }

    public double getGivesMinorsWhenWinningBy600to799() {
        return givesMinorsWhenWinningBy600to799;
    }

    public void setGivesMinorsWhenWinningBy600to799(double givesMinorsWhenWinningBy600to799) {
        this.givesMinorsWhenWinningBy600to799 = givesMinorsWhenWinningBy600to799;
    }

    public double getGivesPawnsWhenWinningBy600to799() {
        return givesPawnsWhenWinningBy600to799;
    }

    public void setGivesPawnsWhenWinningBy600to799(double givesPawnsWhenWinningBy600to799) {
        this.givesPawnsWhenWinningBy600to799 = givesPawnsWhenWinningBy600to799;
    }

    public int getnWhenWinningBy800to999() {
        return nWhenWinningBy800to999;
    }

    public void setnWhenWinningBy800to999(int nWhenWinningBy800to999) {
        this.nWhenWinningBy800to999 = nWhenWinningBy800to999;
    }

    public double getT1WhenWinningBy800to999() {
        return t1WhenWinningBy800to999;
    }

    public void setT1WhenWinningBy800to999(double t1WhenWinningBy800to999) {
        this.t1WhenWinningBy800to999 = t1WhenWinningBy800to999;
    }

    public double getT2WhenWinningBy800to999() {
        return t2WhenWinningBy800to999;
    }

    public void setT2WhenWinningBy800to999(double t2WhenWinningBy800to999) {
        this.t2WhenWinningBy800to999 = t2WhenWinningBy800to999;
    }

    public double getT3WhenWinningBy800to999() {
        return t3WhenWinningBy800to999;
    }

    public void setT3WhenWinningBy800to999(double t3WhenWinningBy800to999) {
        this.t3WhenWinningBy800to999 = t3WhenWinningBy800to999;
    }

    public double getGivesMajorsWhenWinningBy800to999() {
        return givesMajorsWhenWinningBy800to999;
    }

    public void setGivesMajorsWhenWinningBy800to999(double givesMajorsWhenWinningBy800to999) {
        this.givesMajorsWhenWinningBy800to999 = givesMajorsWhenWinningBy800to999;
    }

    public double getGivesMinorsWhenWinningBy800to999() {
        return givesMinorsWhenWinningBy800to999;
    }

    public void setGivesMinorsWhenWinningBy800to999(double givesMinorsWhenWinningBy800to999) {
        this.givesMinorsWhenWinningBy800to999 = givesMinorsWhenWinningBy800to999;
    }

    public double getGivesPawnsWhenWinningBy800to999() {
        return givesPawnsWhenWinningBy800to999;
    }

    public void setGivesPawnsWhenWinningBy800to999(double givesPawnsWhenWinningBy800to999) {
        this.givesPawnsWhenWinningBy800to999 = givesPawnsWhenWinningBy800to999;
    }

    public int getnWhenWinningBy1000() {
        return nWhenWinningBy1000;
    }

    public void setnWhenWinningBy1000(int nWhenWinningBy1000) {
        this.nWhenWinningBy1000 = nWhenWinningBy1000;
    }

    public double getT1WhenWinningBy1000() {
        return t1WhenWinningBy1000;
    }

    public void setT1WhenWinningBy1000(double t1WhenWinningBy1000) {
        this.t1WhenWinningBy1000 = t1WhenWinningBy1000;
    }

    public double getT2WhenWinningBy1000() {
        return t2WhenWinningBy1000;
    }

    public void setT2WhenWinningBy1000(double t2WhenWinningBy1000) {
        this.t2WhenWinningBy1000 = t2WhenWinningBy1000;
    }

    public double getT3WhenWinningBy1000() {
        return t3WhenWinningBy1000;
    }

    public void setT3WhenWinningBy1000(double t3WhenWinningBy1000) {
        this.t3WhenWinningBy1000 = t3WhenWinningBy1000;
    }

    public double getGivesMajorsWhenWinningBy1000() {
        return givesMajorsWhenWinningBy1000;
    }

    public void setGivesMajorsWhenWinningBy1000(double givesMajorsWhenWinningBy1000) {
        this.givesMajorsWhenWinningBy1000 = givesMajorsWhenWinningBy1000;
    }

    public double getGivesMinorsWhenWinningBy1000() {
        return givesMinorsWhenWinningBy1000;
    }

    public void setGivesMinorsWhenWinningBy1000(double givesMinorsWhenWinningBy1000) {
        this.givesMinorsWhenWinningBy1000 = givesMinorsWhenWinningBy1000;
    }

    public double getGivesPawnsWhenWinningBy1000() {
        return givesPawnsWhenWinningBy1000;
    }

    public void setGivesPawnsWhenWinningBy1000(double givesPawnsWhenWinningBy1000) {
        this.givesPawnsWhenWinningBy1000 = givesPawnsWhenWinningBy1000;
    }

    public int getnWhenWinningWithMate() {
        return nWhenWinningWithMate;
    }

    public void setnWhenWinningWithMate(int nWhenWinningWithMate) {
        this.nWhenWinningWithMate = nWhenWinningWithMate;
    }

    public double getT1WhenWinningWithMate() {
        return t1WhenWinningWithMate;
    }

    public void setT1WhenWinningWithMate(double t1WhenWinningWithMate) {
        this.t1WhenWinningWithMate = t1WhenWinningWithMate;
    }

    public double getT2WhenWinningWithMate() {
        return t2WhenWinningWithMate;
    }

    public void setT2WhenWinningWithMate(double t2WhenWinningWithMate) {
        this.t2WhenWinningWithMate = t2WhenWinningWithMate;
    }

    public double getT3WhenWinningWithMate() {
        return t3WhenWinningWithMate;
    }

    public void setT3WhenWinningWithMate(double t3WhenWinningWithMate) {
        this.t3WhenWinningWithMate = t3WhenWinningWithMate;
    }

    public double getGivesMajorsWhenWinningWithMate() {
        return givesMajorsWhenWinningWithMate;
    }

    public void setGivesMajorsWhenWinningWithMate(double givesMajorsWhenWinningWithMate) {
        this.givesMajorsWhenWinningWithMate = givesMajorsWhenWinningWithMate;
    }

    public double getGivesMinorsWhenWinningWithMate() {
        return givesMinorsWhenWinningWithMate;
    }

    public void setGivesMinorsWhenWinningWithMate(double givesMinorsWhenWinningWithMate) {
        this.givesMinorsWhenWinningWithMate = givesMinorsWhenWinningWithMate;
    }

    public double getGivesPawnsWhenWinningWithMate() {
        return givesPawnsWhenWinningWithMate;
    }

    public void setGivesPawnsWhenWinningWithMate(double givesPawnsWhenWinningWithMate) {
        this.givesPawnsWhenWinningWithMate = givesPawnsWhenWinningWithMate;
    }

    public int getnWhenLosing() {
        return nWhenLosing;
    }

    public void setnWhenLosing(int nWhenLosing) {
        this.nWhenLosing = nWhenLosing;
    }

    public double getT1WhenLosing() {
        return t1WhenLosing;
    }

    public void setT1WhenLosing(double t1WhenLosing) {
        this.t1WhenLosing = t1WhenLosing;
    }

    public double getT2WhenLosing() {
        return t2WhenLosing;
    }

    public void setT2WhenLosing(double t2WhenLosing) {
        this.t2WhenLosing = t2WhenLosing;
    }

    public double getT3WhenLosing() {
        return t3WhenLosing;
    }

    public void setT3WhenLosing(double t3WhenLosing) {
        this.t3WhenLosing = t3WhenLosing;
    }

    public double getGivesMajorsWhenLosing() {
        return givesMajorsWhenLosing;
    }

    public void setGivesMajorsWhenLosing(double givesMajorsWhenLosing) {
        this.givesMajorsWhenLosing = givesMajorsWhenLosing;
    }

    public double getGivesMinorsWhenLosing() {
        return givesMinorsWhenLosing;
    }

    public void setGivesMinorsWhenLosing(double givesMinorsWhenLosing) {
        this.givesMinorsWhenLosing = givesMinorsWhenLosing;
    }

    public double getGivesPawnsWhenLosing() {
        return givesPawnsWhenLosing;
    }

    public void setGivesPawnsWhenLosing(double givesPawnsWhenLosing) {
        this.givesPawnsWhenLosing = givesPawnsWhenLosing;
    }

    public int getnWhenWinning() {
        return nWhenWinning;
    }

    public void setnWhenWinning(int nWhenWinning) {
        this.nWhenWinning = nWhenWinning;
    }

    public double getT1WhenWinning() {
        return t1WhenWinning;
    }

    public void setT1WhenWinning(double t1WhenWinning) {
        this.t1WhenWinning = t1WhenWinning;
    }

    public double getT2WhenWinning() {
        return t2WhenWinning;
    }

    public void setT2WhenWinning(double t2WhenWinning) {
        this.t2WhenWinning = t2WhenWinning;
    }

    public double getT3WhenWinning() {
        return t3WhenWinning;
    }

    public void setT3WhenWinning(double t3WhenWinning) {
        this.t3WhenWinning = t3WhenWinning;
    }

    public double getGivesMajorsWhenWinning() {
        return givesMajorsWhenWinning;
    }

    public void setGivesMajorsWhenWinning(double givesMajorsWhenWinning) {
        this.givesMajorsWhenWinning = givesMajorsWhenWinning;
    }

    public double getGivesMinorsWhenWinning() {
        return givesMinorsWhenWinning;
    }

    public void setGivesMinorsWhenWinning(double givesMinorsWhenWinning) {
        this.givesMinorsWhenWinning = givesMinorsWhenWinning;
    }

    public double getGivesPawnsWhenWinning() {
        return givesPawnsWhenWinning;
    }

    public void setGivesPawnsWhenWinning(double givesPawnsWhenWinning) {
        this.givesPawnsWhenWinning = givesPawnsWhenWinning;
    }

    public int getnTotal() {
        return nTotal;
    }

    public void setnTotal(int nTotal) {
        this.nTotal = nTotal;
    }

    public double getT1Total() {
        return t1Total;
    }

    public void setT1Total(double t1Total) {
        this.t1Total = t1Total;
    }

    public double getT2Total() {
        return t2Total;
    }

    public void setT2Total(double t2Total) {
        this.t2Total = t2Total;
    }

    public double getT3Total() {
        return t3Total;
    }

    public void setT3Total(double t3Total) {
        this.t3Total = t3Total;
    }

    public double getGivesMajorsTotal() {
        return givesMajorsTotal;
    }

    public void setGivesMajorsTotal(double givesMajorsTotal) {
        this.givesMajorsTotal = givesMajorsTotal;
    }

    public double getGivesMinorsTotal() {
        return givesMinorsTotal;
    }

    public void setGivesMinorsTotal(double givesMinorsTotal) {
        this.givesMinorsTotal = givesMinorsTotal;
    }

    public double getGivesPawnsTotal() {
        return givesPawnsTotal;
    }

    public void setGivesPawnsTotal(double givesPawnsTotal) {
        this.givesPawnsTotal = givesPawnsTotal;
    }
}
