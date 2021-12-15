package com.framnes.chessstats.console;

import com.google.common.base.Strings;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

public class ImportConsole {

    private static final int PROGRESS_BAR_WIDTH = 20;
    private static final int INFO_WIDTH = 50;

    private final List<ITrackableProgress> trackables;
    private final ThreadPoolExecutor executor;
    private final Object LOCK;

    private boolean stop = false;

    public ImportConsole(ExecutorService executor, Object LOCK) {

        // Initialize console environment
        trackables = new ArrayList<>();
        this.executor = (ThreadPoolExecutor) executor;
        this.LOCK = LOCK;

    }

    public void start() {
        new Thread(new ConsoleRefresher()).start();
    }

    public void track(ITrackableProgress trackable) {
        synchronized (this.trackables) {
            this.trackables.add(trackable);
        }
    }

    public void untrack(ITrackableProgress trackable) {
        synchronized (this.trackables) {
            if (!trackable.isFailed()) {
                this.trackables.remove(trackable);
            }
        }
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
                String importedStatement = String.format("Imported %d/%d games", executor.getCompletedTaskCount(),
                        executor.getTaskCount());
                String importedProgress = progressBar((int) executor.getCompletedTaskCount(),
                        (int) executor.getTaskCount());
                hr();
                System.out.printf("%-" + INFO_WIDTH + "s%s%n", importedStatement, importedProgress);
                hr();

                if (executor.getCompletedTaskCount() == executor.getTaskCount()) {
                    stop = true;
                    synchronized (LOCK) {
                        LOCK.notify();
                    }
                }

                // Importing Progress
                synchronized (trackables) {
                    for (ITrackableProgress trackable : trackables) {

                        if (!trackable.isFailed()) {
                            String trackableProgress = progressBar(trackable.completedWorkUnits(),
                                    trackable.totalWorkUnits());
                            System.out.printf("%-" + INFO_WIDTH + "s%s%n", trackable.trackableName(), trackableProgress);
                        } else {
                            System.out.printf("%-" + INFO_WIDTH + "s%s%n", trackable.trackableName(), failIndicator());
                        }
                    }
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }

        private void clearScreen() {
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }

        private void hr() {
            System.out.println(Strings.repeat("=", INFO_WIDTH + PROGRESS_BAR_WIDTH + 15));
        }

        /**
         * Create a progress bar indicating the given progress.
         *
         * @param progress - units of work completed.
         * @param total - total units of work.
         * @return formatted string for output.
         */
        private String progressBar(int progress, int total) {
            String bars = total > 0 ? Strings.repeat("█", (progress * PROGRESS_BAR_WIDTH) / total) : "";
            String percent = total > 0 ? String.format("%d%%", (int) (100 * ((float) progress / (float) total))) : "NA%";
            return String.format("[%-" + PROGRESS_BAR_WIDTH + "s] %s", bars, percent);
        }

        /**
         * Create a status indicator in the space typically used for the status bar:
         *
         * [-------FAILED-------]
         *
         * @return formatted string for output.
         */
        private String failIndicator() {

            String status = "FAILED";
            int statusHalfLength = status.length() / 2;

            // Add in extra open space for padding
            int leftFiller = (PROGRESS_BAR_WIDTH / 2) - statusHalfLength;
            int rightFiller = (PROGRESS_BAR_WIDTH / 2) - statusHalfLength;

            // Account for oddness
            if (leftFiller + rightFiller + status.length() > PROGRESS_BAR_WIDTH) {
                leftFiller--;
            } else if (leftFiller + rightFiller + status.length() < PROGRESS_BAR_WIDTH) {
                rightFiller++;
            }

            return String.format("[%s%s%s]", Strings.repeat("-", leftFiller), status,
                    Strings.repeat("-", rightFiller));

        }

    }

}
