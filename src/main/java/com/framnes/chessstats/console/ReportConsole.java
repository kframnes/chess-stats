package com.framnes.chessstats.console;

import com.framnes.chessstats.model.ChessMove;
import com.google.common.base.Strings;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ReportConsole {

    private static final String rowPattern = "%-40s%-20s%-30s%-30s%-30s\n";

    private final List<ChessMove> comparableMoves;
    private final List<ChessMove> targetMoves;

    public ReportConsole(List<ChessMove> comparableMove, List<ChessMove> targetMoves) {
        this.comparableMoves = comparableMove;
        this.targetMoves = targetMoves;
    }

    public void run() {

        header();
        printRow("Losing with forced mate", (move) -> move.getMateInBefore() != null && move.getMateInBefore() < 0);
        printRow("Losing by 1000+ CP", (move) -> move.getPositionEvalBefore() <= -1000);
        printRow("Losing by 800-999 CP", (move) -> move.getPositionEvalBefore() <= -800 && move.getPositionEvalBefore() > -1000);
        printRow("Losing by 600-799 CP", (move) -> move.getPositionEvalBefore() <= -600 && move.getPositionEvalBefore() > -800);
        printRow("Losing by 400-599 CP", (move) -> move.getPositionEvalBefore() <= -400 && move.getPositionEvalBefore() > -600);
        printRow("Losing by 200-399 CP", (move) -> move.getPositionEvalBefore() <= -200 && move.getPositionEvalBefore() > -400);
        printRow("Losing by 50-199 CP", (move) -> move.getPositionEvalBefore() <= -50 && move.getPositionEvalBefore() > -200);
        printRow("Even +/- 50 CP", (move) -> move.getPositionEvalBefore() < 50 && move.getPositionEvalBefore() > -50);
        printRow("Winning by 50-199 CP", (move) -> move.getPositionEvalBefore() >= 50 && move.getPositionEvalBefore() < 200);
        printRow("Winning by 200-399 CP", (move) -> move.getPositionEvalBefore() >= 200 && move.getPositionEvalBefore() < 400);
        printRow("Winning by 400-599 CP", (move) -> move.getPositionEvalBefore() >= 400 && move.getPositionEvalBefore() < 600);
        printRow("Winning by 600-799 CP", (move) -> move.getPositionEvalBefore() >= 600 && move.getPositionEvalBefore() < 800);
        printRow("Winning by 800-999 CP", (move) -> move.getPositionEvalBefore() >= 800 && move.getPositionEvalBefore() < 1000);
        printRow("Winning by 1000+ CP", (move) -> move.getPositionEvalBefore() >= 1000);
        printRow("Winning with forced mate", (move) -> move.getMateInBefore() != null && move.getMateInBefore() > 0);
        printRow("Losing", (move) -> move.getPositionEvalBefore() <= 50);
        printRow("Winning", (move) -> move.getPositionEvalBefore() >= 50);
        printRow("Total", (move) -> true);
        System.out.println();
        System.out.println();

    }

    private void printRow(String name, Predicate<ChessMove> predicate) {

        List<ChessMove> targetSliceMoves = this.targetMoves.stream()
                .filter(predicate)
                .collect(Collectors.toList());
        List<ChessMove> comparableSliceMoves = this.comparableMoves.stream()
                .filter(predicate)
                .collect(Collectors.toList());

        long targetN = targetSliceMoves.size();
        long compN = comparableSliceMoves.size();

        String statN = String.format("%-5d (%-6d)",targetN, compN);
        String t1 = tStat(comparableSliceMoves, targetSliceMoves, compN, targetN, 1);
        String t2 = tStat(comparableSliceMoves, targetSliceMoves, compN, targetN, 2);
        String t3 = tStat(comparableSliceMoves, targetSliceMoves, compN, targetN, 3);

        System.out.printf(rowPattern, name, statN, t1, t2, t3);

    }

    private void header() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println(Strings.repeat("=", 120));
        System.out.printf(rowPattern, "Position Eval", "N", wrapInWhite("T1%"), wrapInWhite("T2%"),
                wrapInWhite("T3%"));
        System.out.println(Strings.repeat("=", 120));
    }

    private String tStat(List<ChessMove> comparableSliceMoves, List<ChessMove> targetSliceMoves,
                         long comparableN, long targetN, int tScore) {

        long targetTCounts = targetSliceMoves.stream()
                .filter(move -> move.getMoveEngineRank() != null && move.getMoveEngineRank() <= tScore)
                .count();
        long compTCounts = comparableSliceMoves.stream()
                .filter(move -> move.getMoveEngineRank() != null && move.getMoveEngineRank() <= tScore)
                .count();
        double targetT = (targetN > 0) ? 100.0 * targetTCounts / (double) targetN : 0;
        double compT = (comparableN > 0) ? 100.0 * compTCounts / (double) comparableN : 0;

        String tStatString = String.format("%-6.2f (%5.2f)", targetT, compT);

        if (targetT < compT) {
            return wrapInWhite(tStatString);
        } else if (targetT - compT < 1) {
            return wrapInGreen(tStatString);
        } else if (targetT - compT < 5) {
            return wrapInYellow(tStatString);
        } else {
            return wrapInRed(tStatString);
        }

    }

    private String wrapInWhite(String text) {
        return String.format("\033[1;97m%s\033[0m", text);
    }

    private String wrapInGreen(String text) {
        return String.format("\033[1;92m%s\033[0m", text);
    }

    private String wrapInYellow(String text) {
        return String.format("\033[1;93m%s\033[0m", text);
    }

    private String wrapInRed(String text) {
        return String.format("\033[1;91m%s\033[0m", text);
    }

}