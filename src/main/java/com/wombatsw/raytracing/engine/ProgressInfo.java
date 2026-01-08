package com.wombatsw.raytracing.engine;

import java.time.Duration;

/**
 * Utility class for printing progress information
 */
public class ProgressInfo {
    /**
     * Print the remaining time in human-readable format. Will replace any previous message on the same display line
     *
     * @param start The start time in millis
     * @param row The current row being processed
     * @param numRows The total number of rows
     */
    public static void displayRemainingRowsMessage(final long start, int row, int numRows) {
        int remainingRows = numRows - row;
        if (row < 1) {
            System.out.printf("\rRemaining: %d rows", remainingRows);
        }
        else {
            long elapsedTimeMs = System.currentTimeMillis() - start;
            long remainingTimeMs = elapsedTimeMs * remainingRows / row;

            System.out.printf("\rRemaining: %d rows - Est %s", remainingRows, getDuration(remainingTimeMs));
        }
        System.out.flush();
    }

    /**
     * Print completion message with the total time. Will replace any previous message on the same display line
     *
     * @param start The start time in millis
     */
    public static void displayCompletionMessage(final long start) {
        long totalRenderTimeMs = System.currentTimeMillis() - start;
        System.out.printf("\rDone in %s\n", ProgressInfo.getDuration(totalRenderTimeMs));
    }

    /**
     * Return the duration in a human-readable format
     *
     * @param millis The duration in millis
     * @return The duration in a human-readable format
     */
    private static String getDuration(final long millis) {
        Duration duration = Duration.ofMillis(millis);

        if (duration.toHours() > 0) {
            return String.format("%dh %dm %ds", duration.toHours(), duration.toMinutesPart(), duration.toSecondsPart());
        }

        if (duration.toMinutes() > 0) {
            return String.format("%dm %ds", duration.toMinutes(), duration.toSecondsPart());
        }

        return String.format("%ds", duration.toSeconds());
    }
}
