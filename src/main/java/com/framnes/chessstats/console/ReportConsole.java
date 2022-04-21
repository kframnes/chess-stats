package com.framnes.chessstats.console;

import com.framnes.chessstats.model.ChessPlayerStats;
import com.google.common.base.Strings;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class ReportConsole {

    private static final String tRowPattern = "%-40s%-20s%-40s%-40s%-40s\n";
    private static final String giveawayPattern = "%-40s%-20s%-30s%-30s%-30s\n";

    // How many values must be present in a sample to be significant enough to be included in the mean
    // and standard deviation calculations.
    private static final int SIGNIFICANT_N = 20;

    private final List<ChessPlayerStats> comparableStats;
    private final ChessPlayerStats targetStats;

    public ReportConsole(List<ChessPlayerStats> comparableStats, ChessPlayerStats targetStats) {
        this.comparableStats = comparableStats;
        this.targetStats = targetStats;
    }

    public void run() {

        reportHeader();

        tStatHeader();
        printTStatRow("Losing with forced mate", calculateTStatRow(comparableStats, targetStats,
                "getnWhenLosingWithMate", "getT1WhenLosingWithMate", "getT2WhenLosingWithMate", "getT3WhenLosingWithMate"));
        printTStatRow("Losing by 1000+ CP", calculateTStatRow(comparableStats, targetStats,
                "getnWhenLosingBy1000","getT1WhenLosingBy1000", "getT2WhenLosingBy1000", "getT3WhenLosingBy1000"));
        printTStatRow("Losing by 800-999 CP", calculateTStatRow(comparableStats, targetStats,
                "getnWhenLosingBy800to999","getT1WhenLosingBy800to999", "getT2WhenLosingBy800to999", "getT3WhenLosingBy800to999"));
        printTStatRow("Losing by 600-799 CP", calculateTStatRow(comparableStats, targetStats,
                "getnWhenLosingBy600to799","getT1WhenLosingBy600to799", "getT2WhenLosingBy600to799", "getT3WhenLosingBy600to799"));
        printTStatRow("Losing by 400-599 CP", calculateTStatRow(comparableStats, targetStats,
                "getnWhenLosingBy400to599","getT1WhenLosingBy400to599", "getT2WhenLosingBy400to599", "getT3WhenLosingBy400to599"));
        printTStatRow("Losing by 200-399 CP", calculateTStatRow(comparableStats, targetStats,
                "getnWhenLosingBy200to399","getT1WhenLosingBy200to399", "getT2WhenLosingBy200to399", "getT3WhenLosingBy200to399"));
        printTStatRow("Losing by 50-199 CP", calculateTStatRow(comparableStats, targetStats,
                "getnWhenLosingBy50to199","getT1WhenLosingBy50to199", "getT2WhenLosingBy50to199", "getT3WhenLosingBy50to199"));
        printTStatRow("Even +/- 50 CP", calculateTStatRow(comparableStats, targetStats,
                "getnWhenEven","getT1WhenEven", "getT2WhenEven", "getT3WhenEven"));
        printTStatRow("Winning by 50-199 CP", calculateTStatRow(comparableStats, targetStats,
                "getnWhenWinningBy50to199","getT1WhenWinningBy50to199", "getT2WhenWinningBy50to199", "getT3WhenWinningBy50to199"));
        printTStatRow("Winning by 200-399 CP", calculateTStatRow(comparableStats, targetStats,
                "getnWhenWinningBy200to399","getT1WhenWinningBy200to399", "getT2WhenWinningBy200to399", "getT3WhenWinningBy200to399"));
        printTStatRow("Winning by 400-599 CP", calculateTStatRow(comparableStats, targetStats,
                "getnWhenWinningBy400to599","getT1WhenWinningBy400to599", "getT2WhenWinningBy400to599", "getT3WhenWinningBy400to599"));
        printTStatRow("Winning by 600-799 CP", calculateTStatRow(comparableStats, targetStats,
                "getnWhenWinningBy600to799","getT1WhenWinningBy600to799", "getT2WhenWinningBy600to799", "getT3WhenWinningBy600to799"));
        printTStatRow("Winning by 800-999 CP", calculateTStatRow(comparableStats, targetStats,
                "getnWhenWinningBy800to999","getT1WhenWinningBy800to999", "getT2WhenWinningBy800to999", "getT3WhenWinningBy800to999"));
        printTStatRow("Winning by 1000+ CP", calculateTStatRow(comparableStats, targetStats,
                "getnWhenWinningBy1000","getT1WhenWinningBy1000", "getT2WhenWinningBy1000", "getT3WhenWinningBy1000"));
        printTStatRow("Winning with forced mate", calculateTStatRow(comparableStats, targetStats,
                "getnWhenWinningWithMate","getT1WhenWinningWithMate", "getT2WhenWinningWithMate", "getT3WhenWinningWithMate"));
        printTStatRow("Losing", calculateTStatRow(comparableStats, targetStats,
                "getnWhenLosing", "getT1WhenLosing","getT2WhenLosing", "getT3WhenLosing"));
        printTStatRow("Winning", calculateTStatRow(comparableStats, targetStats,
                "getnWhenWinning", "getT1WhenWinning","getT2WhenWinning", "getT3WhenWinning"));
        printTStatRow("Total", calculateTStatRow(comparableStats, targetStats,
                "getnTotal", "getT1Total","getT2Total", "getT3Total"));

        System.out.println();

//        giveAwayHeader();
//        printTStatRow("Losing with forced mate", calculateGiveawayRow(comparableStats, targetStats, "getT1WhenLosingWithMate", "getT2WhenLosingWithMate", "getT3WhenLosingWithMate"));
//        printTStatRow("Losing by 1000+ CP", calculateGiveawayRow(comparableStats, targetStats, "getT1WhenLosingBy1000", "getT2WhenLosingBy1000", "getT3WhenLosingBy1000"));
//        printTStatRow("Losing by 800-999 CP", calculateGiveawayRow(comparableStats, targetStats, "getT1WhenLosingBy800to999", "getT2WhenLosingBy800to999", "getT3WhenLosingBy800to999"));
//        printTStatRow("Losing by 600-799 CP", calculateGiveawayRow(comparableStats, targetStats, "getT1WhenLosingBy600to799", "getT2WhenLosingBy600to799", "getT3WhenLosingBy600to799"));
//        printTStatRow("Losing by 400-599 CP", calculateGiveawayRow(comparableStats, targetStats, "getT1WhenLosingBy400to599", "getT2WhenLosingBy400to599", "getT3WhenLosingBy400to599"));
//        printTStatRow("Losing by 200-399 CP", calculateGiveawayRow(comparableStats, targetStats, "getT1WhenLosingBy200to399", "getT2WhenLosingBy200to399", "getT3WhenLosingBy200to399"));
//        printTStatRow("Losing by 50-199 CP", calculateGiveawayRow(comparableStats, targetStats, "getT1WhenLosingBy50to199", "getT2WhenLosingBy50to199", "getT3WhenLosingBy50to199"));
//        printTStatRow("Even +/- 50 CP", calculateGiveawayRow(comparableStats, targetStats, "getT1WhenEven", "getT2WhenEven", "getT3WhenEven"));
//        printTStatRow("Winning by 50-199 CP", calculateGiveawayRow(comparableStats, targetStats, "getT1WhenWinningBy50to199", "getT2WhenWinningBy50to199", "getT3WhenWinningBy50to199"));
//        printTStatRow("Winning by 200-399 CP", calculateGiveawayRow(comparableStats, targetStats, "getT1WhenWinningBy200to399", "getT2WhenWinningBy200to399", "getT3WhenWinningBy200to399"));
//        printTStatRow("Winning by 400-599 CP", calculateGiveawayRow(comparableStats, targetStats, "getT1WhenWinningBy400to599", "getT2WhenWinningBy400to599", "getT3WhenWinningBy400to599"));
//        printTStatRow("Winning by 600-799 CP", calculateGiveawayRow(comparableStats, targetStats, "getT1WhenWinningBy600to799", "getT2WhenWinningBy600to799", "getT3WhenWinningBy600to799"));
//        printTStatRow("Winning by 800-999 CP", calculateGiveawayRow(comparableStats, targetStats, "getT1WhenWinningBy800to999", "getT2WhenWinningBy800to999", "getT3WhenWinningBy800to999"));
//        printTStatRow("Winning by 1000+ CP", calculateGiveawayRow(comparableStats, targetStats, "getT1WhenWinningBy1000", "getT2WhenWinningBy1000", "getT3WhenWinningBy1000"));
//        printTStatRow("Winning with forced mate", calculateGiveawayRow(comparableStats, targetStats, "getT1WhenWinningWithMate", "getT2WhenWinningWithMate", "getT3WhenWinningWithMate"));
//        printTStatRow("Losing", calculateGiveawayRow(comparableStats, targetStats, "getT1WhenLosing", "getT2WhenLosing", "getT3WhenLosing"));
//        printTStatRow("Winning", calculateGiveawayRow(comparableStats, targetStats, "getT1WhenWinning", "getT2WhenWinning", "getT3WhenWinning"));
//        printTStatRow("Total", calculateGiveawayRow(comparableStats, targetStats, "getT1Total", "getT2Total", "getT3Total"));

        System.out.println();

    }

    private void reportHeader() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private void tStatHeader() {
        System.out.println(Strings.repeat("=", 145));
        System.out.printf(tRowPattern, "Position Eval", "N", wrapInWhite("T1%"), wrapInWhite("T2%"),
                wrapInWhite("T3%"));
        System.out.println(Strings.repeat("=", 145));
    }

    /**
     * Calculates the data used to populate a single T-Stat row.
     *
     * @return a double[] with the following values, by index:
     *      0 -- number of moves for target player
     *      1 -- number of moves for comparable players
     *      2 -- T1% for target player
     *      3 -- T1% mean for comparable players (excluding players where n=0)
     *      4 -- T1% standard deviation
     *      5 -- T2% for target player
     *      6 -- T2% mean for comparable players (excluding players where n=0)
     *      7 -- T2% standard deviation
     *      8 -- T3% for target player
     *      9 -- T3% mean for comparable players (excluding players where n=0)
     *      10 -- T3% standard deviation
     */
    private double[] calculateTStatRow(List<ChessPlayerStats> comparableStats, ChessPlayerStats targetStats,
                                       String nGetter, String t1Getter, String t2Getter, String t3Getter) {

        try {

            Method nMethod = ChessPlayerStats.class.getDeclaredMethod(nGetter);
            Method t1Method = ChessPlayerStats.class.getDeclaredMethod(t1Getter);
            Method t2Method = ChessPlayerStats.class.getDeclaredMethod(t2Getter);
            Method t3Method = ChessPlayerStats.class.getDeclaredMethod(t3Getter);

            double[] stats = new double[11];

            stats[0] = 1.0 * quietInvoke(targetStats, nMethod, Integer.class, 0);
            stats[1] = 1.0 * comparableStats.stream()
                    .filter((s) -> quietInvoke(s, nMethod, Integer.class, 0) >= SIGNIFICANT_N)
                    .mapToDouble((s) -> quietInvoke(s, nMethod, Integer.class, 0))
                    .sum();

            stats[2] = quietInvoke(targetStats, t1Method, Double.class, 0.0);
            stats[3] = comparableStats.stream()
                    .filter((s) -> quietInvoke(s, nMethod, Integer.class, 0) >= SIGNIFICANT_N)
                    .mapToDouble((s) -> quietInvoke(s, t1Method, Double.class, 0.0))
                    .average().orElseGet(() -> 0.0);
            stats[4] = calculateStandardDeviation(comparableStats, t1Method, nMethod, stats[3]);

            stats[5] = quietInvoke(targetStats, t2Method, Double.class, 0.0);
            stats[6] = comparableStats.stream()
                    .filter((s) -> quietInvoke(s, nMethod, Integer.class, 0) >= SIGNIFICANT_N)
                    .mapToDouble((s) -> quietInvoke(s, t2Method, Double.class, 0.0))
                    .average().orElseGet(() -> 0.0);
            stats[7] = calculateStandardDeviation(comparableStats, t2Method, nMethod, stats[6]);

            stats[8] = quietInvoke(targetStats, t3Method, Double.class, 0.0);
            stats[9] = comparableStats.stream()
                    .filter((s) -> quietInvoke(s, nMethod, Integer.class, 0) >= SIGNIFICANT_N)
                    .mapToDouble((s) -> quietInvoke(s, t3Method, Double.class, 0.0))
                    .average().orElseGet(() -> 0.0);
            stats[10] = calculateStandardDeviation(comparableStats, t3Method, nMethod, stats[9]);

            return stats;

        } catch (NoSuchMethodException e) {

            // Somehow we're looking for an invalid method, so just return defaults.
            return new double[] {
                    0.0, 0.0, 0.0,
                    0.0, 0.0, 0.0,
                    0.0, 0.0, 0.0,
                    0.0, 0.0
            };

        }

    }

    private void printTStatRow(String name, double[] stats) {

        String statN = String.format("%-5d (%-6d)", (int) stats[0], (int) stats[1]);
        String t1 = tStat((int) stats[0], stats[2], stats[3], stats[4]);
        String t2 = tStat((int) stats[0], stats[5], stats[6], stats[7]);
        String t3 = tStat((int) stats[0], stats[8], stats[9], stats[10]);

        System.out.printf(tRowPattern, name, statN, t1, t2, t3);

    }

    private String tStat(Integer n, double target, double comparable, double standardDeviation) {

        String tStatString = String.format("%-6.2f (%5.2f | s=%5.2f)", target, comparable, standardDeviation);

        // If we don't have enough moves to consider this slice significant, print it to de-emphasize it
        if (n < SIGNIFICANT_N) {
            return wrapAsInsignificance(tStatString);
        }

        if (target < comparable) {
            return wrapInWhite(tStatString);
        } else if (target - comparable < standardDeviation) {
            return wrapInGreen(tStatString);
        } else if (target - comparable < (standardDeviation * 2)) {
            return wrapInYellow(tStatString);
        } else {
            return wrapInRed(tStatString);
        }

    }

    private void giveAwayHeader() {
        System.out.println(Strings.repeat("=", 112));
        System.out.printf(tRowPattern, "Position Eval", "N",
                wrapInWhite("▼ 500+ CP"),
                wrapInWhite("▼ 300+ CP"),
                wrapInWhite("▼ 100+ CP"));

        System.out.println(Strings.repeat("=", 112));
    }

    private void printGiveawayRow(String name, double[] stats) {

        String statN = String.format("%-5d (%-6d)", 0, 0);
        String pawns = giveawayStat(0.0, 0.0, 0.0);
        String minors = giveawayStat(0.0, 0.0, 0.0);
        String majors = giveawayStat(0.0, 0.0, 0.0);

        System.out.printf(giveawayPattern, name, statN, majors, minors, pawns);

    }

    private String giveawayStat(double target, double comparable, double standardDeviation) {

        String tStatString = String.format("%-6.2f (%5.2f)", target, comparable);

        if (target < comparable) {
            return wrapInWhite(tStatString);
        } else if (target - comparable < standardDeviation) {
            return wrapInGreen(tStatString);
        } else if (target - comparable < (standardDeviation * 2)) {
            return wrapInYellow(tStatString);
        } else {
            return wrapInRed(tStatString);
        }

    }

    private String wrapAsInsignificance(String text) {
        return String.format("\033[0;37m%s\033[0m", text);
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

    /**
     * Calculates the data used to populate a single giveaway row.
     *
     * @return a double[] with the following values, by index:
     *      0 -- number of moves for target player
     *      1 -- number of moves for comparable players
     *      2 -- % of moves that the target player gives away > 500 CP
     *      3 -- % mean of moves that comparable players gives away > 500 CP
     *      4 -- standard deviation of moves that comparable player gives away > 500 CP
     *      5 -- % of moves that the target player gives away > 300 CP
     *      6 -- % mean of moves that comparable players gives away > 300 CP
     *      7 -- standard deviation of moves that comparable player gives away > 300 CP
     *      8 -- % of moves that the target player gives away > 100 CP
     *      9 -- % mean of moves that comparable players gives away > 100 CP
     *      10 -- standard deviation of moves that comparable player gives away > 100 CP
     */
    private double[] calculateGiveawayRow(List<ChessPlayerStats> comparableStats, ChessPlayerStats targetStats,
                                       String t1Getter, String t2Getter, String t3Getter) {

        return new double[] {
                0.0, 0.0, 0.0,
                0.0, 0.0, 0.0,
                0.0, 0.0, 0.0,
                0.0, 0.0
        };

    }

    /**
     * Calculate the standard deviation for the statistic provided by the {@code Method}, given the mean of this
     * statistic.
     *
     * Formula: SD = [(Sum of (xi – x)^2)/n-1]^1/2
     *
     */
    private double calculateStandardDeviation(List<ChessPlayerStats> comparableStats,
                                              Method statMethod, Method nMethod,
                                              double mean) {

        double size = comparableStats.stream()
                .filter((s) -> quietInvoke(s, nMethod, Integer.class, 0) >= SIGNIFICANT_N)
                .count();
        if (size < 2) return 0.0;

        double sumOfVarianceSquared = comparableStats.stream()
                .filter((s) -> quietInvoke(s, nMethod, Integer.class, 0) >= SIGNIFICANT_N)
                .mapToDouble((s) -> quietInvoke(s, statMethod, Double.class, 0.0))
                .map((stat) -> stat - mean)
                .map((variance) -> Math.pow(variance, 2))
                .sum();
        return Math.sqrt(sumOfVarianceSquared / (size - 1));

    }

    /**
     * Helper function to quietly invoke the given {@code Method} on our instance of {@code ChessPlayerStats}, which
     * is necessary considering our usage of reflected invocation inside of Streams.
     */
    private <T> T quietInvoke(ChessPlayerStats stats, Method method, Class<T> returnType, T defaultValue) {
        try {
            return returnType.cast(method.invoke(stats));
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

}