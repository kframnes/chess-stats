package com.framnes.chessstats.console;

import com.framnes.chessstats.model.ChessMove;
import com.google.common.base.Strings;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ReportConsole {

    private static final String tRowPattern = "%-40s%-20s%-30s%-30s%-30s\n";
    private static final String giveawayPattern = "%-40s%-20s%-30s%-30s%-30s\n";

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

        reportHeader();

        tStatHeader();
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
        printTStatRow("Losing", (move) -> move.getPositionEvalBefore() <= -50);
        printTStatRow("Winning", (move) -> move.getPositionEvalBefore() >= 50);
        printTStatRow("Total", (move) -> true);

        System.out.println();
        printAdvantageTransitions();
        System.out.println();

        giveAwayHeader();
        printGiveawayRow("Losing with forced mate", (move) -> move.getMateInBefore() != null && move.getMateInBefore() < 0);
        printGiveawayRow("Losing by 1000+ CP", (move) -> move.getPositionEvalBefore() <= -1000);
        printGiveawayRow("Losing by 800-999 CP", (move) -> move.getPositionEvalBefore() <= -800 && move.getPositionEvalBefore() > -1000);
        printGiveawayRow("Losing by 600-799 CP", (move) -> move.getPositionEvalBefore() <= -600 && move.getPositionEvalBefore() > -800);
        printGiveawayRow("Losing by 400-599 CP", (move) -> move.getPositionEvalBefore() <= -400 && move.getPositionEvalBefore() > -600);
        printGiveawayRow("Losing by 200-399 CP", (move) -> move.getPositionEvalBefore() <= -200 && move.getPositionEvalBefore() > -400);
        printGiveawayRow("Losing by 50-199 CP", (move) -> move.getPositionEvalBefore() <= -50 && move.getPositionEvalBefore() > -200);
        printGiveawayRow("Even +/- 50 CP", (move) -> move.getPositionEvalBefore() < 50 && move.getPositionEvalBefore() > -50);
        printGiveawayRow("Winning by 50-199 CP", (move) -> move.getPositionEvalBefore() >= 50 && move.getPositionEvalBefore() < 200);
        printGiveawayRow("Winning by 200-399 CP", (move) -> move.getPositionEvalBefore() >= 200 && move.getPositionEvalBefore() < 400);
        printGiveawayRow("Winning by 400-599 CP", (move) -> move.getPositionEvalBefore() >= 400 && move.getPositionEvalBefore() < 600);
        printGiveawayRow("Winning by 600-799 CP", (move) -> move.getPositionEvalBefore() >= 600 && move.getPositionEvalBefore() < 800);
        printGiveawayRow("Winning by 800-999 CP", (move) -> move.getPositionEvalBefore() >= 800 && move.getPositionEvalBefore() < 1000);
        printGiveawayRow("Winning by 1000+ CP", (move) -> move.getPositionEvalBefore() >= 1000);
        printGiveawayRow("Winning with forced mate", (move) -> move.getMateInBefore() != null && move.getMateInBefore() > 0);
        printGiveawayRow("Losing", (move) -> move.getPositionEvalBefore() <= -50);
        printGiveawayRow("Winning", (move) -> move.getPositionEvalBefore() >= 50);
        printGiveawayRow("Total", (move) -> true);

        System.out.println();

    }

    private void reportHeader() {
        System.out.print("\033[H\033[2J");
        System.out.flush();

        System.out.println(Strings.repeat("=", 112));
        System.out.printf("%-111s=\n", "= Book Moves: " + bookMoves);
        System.out.printf("%-111s=\n", "= Comp Elo: " + minCompElo + "-" + maxCompElo);
    }

    private void tStatHeader() {
        System.out.println(Strings.repeat("=", 112));
        System.out.printf(tRowPattern, "Position Eval", "N", wrapInWhite("T1%"), wrapInWhite("T2%"),
                wrapInWhite("T3%"));
        System.out.println(Strings.repeat("=", 112));
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
        } else if (targetT - compT < 2) {
            return wrapInGreen(tStatString);
        } else if (targetT - compT < 10) {
            return wrapInYellow(tStatString);
        } else {
            return wrapInRed(tStatString);
        }

    }

    private void printAdvantageTransitions() {

        String losingToEvenStat = transitionStat(
                move -> move.getPositionEvalBefore() <= -50,
                move -> move.getPositionEvalAfter() > -50 && move.getPositionEvalAfter() < 50
        );
        String evenToWinningStat = transitionStat(
                move -> move.getPositionEvalBefore() > -50 && move.getPositionEvalBefore() < 50,
                move -> move.getPositionEvalAfter() >= 50
        );

        String winningToEven = transitionStat(
                move -> move.getPositionEvalBefore() >= 50,
                move -> move.getPositionEvalAfter() > -50 && move.getPositionEvalAfter() < 50
        );
        String evenToLosingStat = transitionStat(
                move -> move.getPositionEvalBefore() > -50 && move.getPositionEvalAfter() < 50,
                move -> move.getPositionEvalAfter() >= 50
        );

        // Directional Pieces
        String losingToEvenArrow = Strings.repeat("—", 14) +
                losingToEvenStat +
                Strings.repeat("—", 15) + "▶";
        String evenToWinningArrow = Strings.repeat("—", 14) +
                evenToWinningStat +
                Strings.repeat("—", 15) + "▶";
        String winningToEvenArrow = "◀" + Strings.repeat("—", 13) +
                evenToLosingStat +
                Strings.repeat("—", 16);
        String evenToLosingArrow = "◀" + Strings.repeat("—", 13) +
                winningToEven +
                Strings.repeat("—", 16);

        // Middle Legend
        String losingLabel = String.format("%-12s", "LOSING");
        String evenLabel = "EVEN";
        String winningLabel = String.format("%12s", "WINNING");

        System.out.printf("%s%7s%s%-7s\n",
                Strings.repeat(" ", 8),
                losingToEvenArrow,
                Strings.repeat(" ", 8),
                evenToWinningArrow
        );
        System.out.printf("%-54s%s%54s\n", losingLabel, evenLabel, winningLabel);
        System.out.printf("%s%7s%s%-7s\n",
                Strings.repeat(" ", 8),
                evenToLosingArrow,
                Strings.repeat(" ", 8),
                winningToEvenArrow
        );

    }

    private String transitionStat(Predicate<ChessMove> basePredicate, Predicate<ChessMove> flippedPredicate) {

        long targetPositionsN = this.targetMoves.stream()
                .filter(basePredicate)
                .count();
        long targetPositionsF = this.targetMoves.stream()
                .filter(basePredicate)
                .filter(flippedPredicate)
                .count();

        long compPositionsN = this.comparableMoves.stream()
                .filter(basePredicate)
                .count();
        long compPositionsF = this.comparableMoves.stream()
                .filter(basePredicate)
                .filter(flippedPredicate)
                .count();

        double targetPositionsPerc = (targetPositionsN > 0)
                ? 100.0 * targetPositionsF / (double) targetPositionsN
                : 0;
        double compPositionsPerc = (compPositionsN > 0)
                ? 100.0 * compPositionsF / (double) compPositionsN
                : 0;

        String transitionStat = String.format("%-6.2f (%5.2f)", targetPositionsPerc, compPositionsPerc);
        return wrapInWhite(transitionStat);

    }

    private void giveAwayHeader() {
        System.out.println(Strings.repeat("=", 112));
        System.out.printf(tRowPattern, "Position Eval", "N",
                wrapInWhite("▼ 500+ CP"),
                wrapInWhite("▼ 300+ CP"),
                wrapInWhite("▼ 100+ CP"));

        System.out.println(Strings.repeat("=", 112));
    }

    private void printGiveawayRow(String name, Predicate<ChessMove> predicate) {

        List<ChessMove> targetSliceMoves = this.targetMoves.stream()
                .filter(predicate)
                .collect(Collectors.toList());
        List<ChessMove> comparableSliceMoves = this.comparableMoves.stream()
                .filter(predicate)
                .collect(Collectors.toList());

        long targetN = targetSliceMoves.size();
        long compN = comparableSliceMoves.size();

        String statN = String.format("%-5d (%-6d)",targetN, compN);
        String pawns = giveawayStat(comparableSliceMoves, targetSliceMoves, compN, targetN, -100);
        String minors = giveawayStat(comparableSliceMoves, targetSliceMoves, compN, targetN, -300);
        String majors = giveawayStat(comparableSliceMoves, targetSliceMoves, compN, targetN, -500);

        System.out.printf(giveawayPattern, name, statN, majors, minors, pawns);

    }

    private String giveawayStat(List<ChessMove> comparableSliceMoves, List<ChessMove> targetSliceMoves,
                                long comparableN, long targetN, int cpDelta) {

        long targetLossCounts = targetSliceMoves.stream()
                .filter(move -> move.getPositionEvalAfter() - move.getPositionEvalBefore() < cpDelta)
                .count();
        long compLossCounts = comparableSliceMoves.stream()
                .filter(move -> move.getPositionEvalAfter() - move.getPositionEvalBefore() < cpDelta)
                .count();
        double targetLoss = (targetN > 0) ? 100.0 * targetLossCounts / (double) targetN : 0;
        double compLoss = (comparableN > 0) ? 100.0 * compLossCounts / (double) comparableN : 0;

        String tStatString = String.format("%-6.2f (%5.2f)", targetLoss, compLoss);

        if (targetLoss > compLoss) {
            return wrapInWhite(tStatString);
        } else if (compLoss - targetLoss < 2) {
            return wrapInGreen(tStatString);
        } else if (compLoss - targetLoss < 10) {
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