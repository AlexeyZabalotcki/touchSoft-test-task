package com.report.generator.demo;

import java.util.Deque;
import java.util.LinkedList;

public class Consumer {
    private static final long FIVE_MINUTES_IN_MILLIS = 5 * 60 * 1000; // 5 minutes in milliseconds
    private Deque<DataPoint> window; // Deque to store data points
    private long sum; // Sum of values in the current window
    private long startTime; // Start time of the window

    public Consumer() {
        this.window = new LinkedList<>();
        this.sum = 0;
        this.startTime = System.currentTimeMillis();
    }

    /**
     * Called periodically to consume an integer.
     */
    public void accept(int number) {
        long currentTime = System.currentTimeMillis();
        // Remove elements older than 5 minutes from the start of the deque
        while (!window.isEmpty() && currentTime - window.peekFirst().time >= FIVE_MINUTES_IN_MILLIS) {
            DataPoint oldData = window.pollFirst();
            sum -= oldData.value;
        }
        // Add the new number to the end of the deque
        window.addLast(new DataPoint(number, currentTime));
        sum += number;
    }

    /**
     * Returns the mean (aka average) of numbers consumed in the last 5 minute period.
     */
    public double mean() {
        if (window.isEmpty()) {
            return 0; // No elements in the window
        }
        return sum / (double) window.size(); // Calculate mean
    }

    /**
     * Data structure to store each number along with the timestamp it was consumed.
     */
    private static class DataPoint {
        int value;
        long time;

        public DataPoint(int value, long time) {
            this.value = value;
            this.time = time;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Consumer consumer = new Consumer();

        // Simulate accepting numbers
        consumer.accept(10);
        consumer.accept(20);
        consumer.accept(30);

        // Wait for some time (e.g., 1 minute)
        Thread.sleep(60 * 1000);

        // Accept more numbers
        consumer.accept(40);
        consumer.accept(50);

        // Get the mean of numbers consumed in the last 5 minutes
        double mean = consumer.mean();
        System.out.println("Mean of numbers consumed in the last 5 minutes: " + mean);

        consumer.accept(50);

        // Wait for more than 5 minutes to see the effect on the mean
        Thread.sleep(5 * 60 * 1000);

        // Get the mean again after the 5-minute window has passed
        mean = consumer.mean();
        System.out.println("Mean of numbers consumed in the last 5 minutes (after waiting): " + mean);
    }
}
