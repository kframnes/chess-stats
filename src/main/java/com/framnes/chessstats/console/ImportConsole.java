package com.framnes.chessstats.console;

import com.google.common.base.Strings;

public class ImportConsole {

    private static int PROGRESS_BAR_WIDTH = 20;
    private static int INFO_WIDTH = 50;

    private int totalGames;
    private int importedGames;
    private boolean stop = false;

    public ImportConsole(int totalGames, int importThreads) {

        // Initialize console environment
        this.totalGames = totalGames;
        this.importedGames = 0;

        // Start console refresher thread
        new Thread(new ConsoleRefresher());

    }

    public void stop() {
        stop = true;
    }



    /**
     * A {@code Runnable} acting in a separate thread to keep the current
     * console up to date, refreshing at a specific frequency.
     */
    class ConsoleRefresher implements Runnable {

        @Override
        public void run() {
            while (!stop) {

                clearScreen();

                // Overall progress
                String importedStatement = String.format("Importing %d/%d games", importedGames, totalGames);
                String importedProgress = progressBar(importedGames, totalGames, PROGRESS_BAR_WIDTH);
                System.out.printf("%-"+INFO_WIDTH+ "s%s%n", importedStatement, importedProgress);
                blankLine();

                // Importing Progress


            }
        }

        private void clearScreen() {
            System.out.print("\033[2J");
        }

        private void blankLine() {
            System.out.println("");
        }

        private String progressBar(int progress, int total, int width) {
            String bars = Strings.repeat("=", (progress * width) / total);
            String percent = String.format("%d%%", (progress * 100) / (total * 100));
            return String.format("[%-" + width + "s] %s", bars, percent);
        }

    }



}
