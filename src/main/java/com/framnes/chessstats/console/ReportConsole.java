package com.framnes.chessstats.console;

import com.framnes.chessstats.model.ChessMove;
import com.google.common.base.Strings;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ReportConsole {

    private static final String tRowPattern = "%-40s%-20s%-30s%-30s%-30s\n";
    private static final String flipRowPattern = "%-40s%-30s\n";

    private final List<ChessMove> comparableMoves;
    private final List<ChessMove> targetMoves;

    private final int bookMoves;
    private final int minCompElo;
    private final int maxCompElo;

    public ReportConsole(List<ChessMove> comparableMove, List<ChessMove> targetMoves,
                         int bookMoves, int minCompElo, int maxCompElo) {
        this.comparableMoves = comparableMove;
        this.targetMoves = targetMoves;
        this.bookMoves = bookMoves;
        this.minCompElo = minCompElo;
        this.maxCompElo = maxCompElo;
    }

    public void run() {

        header();
        printTStatRow("Losing with forced mate", (move) -> move.getMateInBefore() != null && move.getMateInBefore() < 0);
        printTStatRow("Losing by 1000+ CP", (move) -> move.getPositionEvalBefore() <= -1000);
        printTStatRow("Losing by 800-999 CP", (move) -> move.getPositionEvalBefore() <= -800 && move.getPositionEvalBefore() > -1000);
        printTStatRow("Losing by 600-799 CP", (move) -> move.getPositionEvalBefore() <= -600 && move.getPositionEvalBefore() > -800);
        printTStatRow("Losing by 400-599 CP", (move) -> move.getPositionEvalBefore() <= -400 && move.getPositionEvalBefore() > -600);
        printTStatRow("Losing by 200-399 CP", (move) -> move.getPositionEvalBefore() <= -200 && move.getPositionEvalBefore() > -400);
        printTStatRow("Losing by 50-199 CP", (move) -> move.getPositionEvalBefore() <= -50 && move.getPositionEvalBefore() > -200);
        printTStatRow("Even +/- 50 CP", (move) -> move.getPositionEvalBefore() < 50 && move.getPositionEvalBefore() > -50);
        printTStatRow("Winning by 50-199 CP", (move) -> move.getPositionEvalBefore() >= 50 && move.getPositionEvalBefore() < 200);
        printTStatRow("Winning by 200-399 CP", (move) -> move.getPositionEvalBefore() >= 200 && move.getPositionEvalBefore() < 400);
        printTStatRow("Winning by 400-599 CP", (move) -> move.getPositionEvalBefore() >= 400 && move.getPositionEvalBefore() < 600);
        printTStatRow("Winning by 600-799 CP", (move) -> move.getPositionEvalBefore() >= 600 && move.getPositionEvalBefore() < 800);
        printTStatRow("Winning by 800-999 CP", (move) -> move.getPositionEvalBefore() >= 800 && move.getPositionEvalBefore() < 1000);
        printTStatRow("Winning by 1000+ CP", (move) -> move.getPositionEvalBefore() >= 1000);
        printTStatRow("Winning with forced mate", (move) -> move.getMateInBefore() != null && move.getMateInBefore() > 0);
        printTStatRow("Losing", (move) -> move.getPositionEvalBefore() <= 50);
        printTStatRow("Winning", (move) -> move.getPositionEvalBefore() >= 50);
        printTStatRow("Total", (move) -> true);
        System.out.println();
        printGameFlips();
        System.out.println();

    }

    private void printGameFlips() {

        // How many moves did target go from winning --> Losing
        long targetLosingPositions = this.targetMoves.stream()
                .filter(move -> move.getPositionEvalBefore() <= 50)
                .count();
        long compLosingPositions = this.comparableMoves.stream()
                .filter(move -> move.getPositionEvalBefore() <= 50)
                .count();
        long targetLosingPositionsFlipped = this.targetMoves.stream()
                .filter(move -> move.getPositionEvalBefore() <= 50)
                .filter(move -> move.getPositionEvalAfter() >= 50)
                .count();
        long compLosingPositionsFlipped = this.comparableMoves.stream()
                .filter(move -> move.getPositionEvalBefore() <= 50)
                .filter(move -> move.getPositionEvalAfter() >= 50)
                .count();

        double targetLosingPositionsFlippedPercentage = (targetLosingPositions > 0)
                ? 100.0 * targetLosingPositionsFlipped / (double) targetLosingPositions
                : 0;
        double compLosingPositionsFlippedPercentage = (compLosingPositions > 0)
                ? 100.0 * compLosingPositionsFlipped / (double) compLosingPositions
                : 0;

        String losingToWinning = String.format("%.2f%% (%.2f%%)",
                targetLosingPositionsFlippedPercentage, compLosingPositionsFlippedPercentage);

        // How many moves did target go from winning --> Losing
        long targetWinningPositions = this.targetMoves.stream()
                .filter(move -> move.getPositionEvalBefore() >= 50)
                .count();
        long compWinningPositions = this.comparableMoves.stream()
                .filter(move -> move.getPositionEvalBefore() >= 50)
                .count();
        long targetWinningPositionsFlipped = this.targetMoves.stream()
                .filter(move -> move.getPositionEvalBefore() >= 50)
                .filter(move -> move.getPositionEvalAfter() <= 50)
                .count();
        long compWinningPositionsFlipped = this.comparableMoves.stream()
                .filter(move -> move.getPositionEvalBefore() >= 50)
                .filter(move -> move.getPositionEvalAfter() <= 50)
                .count();

        double targetWinningPositionsFlippedPercentage = (targetWinningPositions > 0)
                ? 100.0 * targetWinningPositionsFlipped / (double) targetWinningPositions
                : 0;
        double compWinningPositionsFlippedPercentage = (compWinningPositions > 0)
                ? 100.0 * compWinningPositionsFlipped / (double) compWinningPositions
                : 0;

        String winningToLosing = String.format("%.2f%% (%.2f%%)",
                targetWinningPositionsFlippedPercentage, compWinningPositionsFlippedPercentage);

        System.out.printf(flipRowPattern, "Winning --> Losing (" + targetWinningPositionsFlipped + "/" + targetWinningPositions + ")",
                winningToLosing);
        System.out.printf(flipRowPattern, "Losing --> Winning (" + targetLosingPositionsFlipped + "/" + targetLosingPositions + ")",
                losingToWinning);


    }

    private void printTStatRow(String name, Predicate<ChessMove> predicate) {

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

        System.out.printf(tRowPattern, name, statN, t1, t2, t3);

    }

    private void header() {
        System.out.print("\033[H\033[2J");
        System.out.flush();

        System.out.println(Strings.repeat("=", 120));
        System.out.printf("%-119s=\n", "= Book Moves: " + bookMoves);
        System.out.printf("%-119s=\n", "= Comp Elo: " + minCompElo + "-" + maxCompElo);
        System.out.println(Strings.repeat("=", 120));
        System.out.printf(tRowPattern, "Position Eval", "N", wrapInWhite("T1%"), wrapInWhite("T2%"),
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