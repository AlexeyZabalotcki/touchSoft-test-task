package com.touchsoft;

import java.util.Deque;
import java.util.LinkedList;

public class Consumer {
    private static final long FIVE_MINUTES_IN_MILLIS = 5 * 60 * 1000;
    private Deque<DataPoint> window;
    private long sum;
    private long startTime;

    public Consumer() {
        this.window = new LinkedList<>();
        this.sum = 0;
        this.startTime = System.currentTimeMillis();
    }

    public void accept(int number) {
        long currentTime = System.currentTimeMillis();

        while (!window.isEmpty() && currentTime - window.peekFirst().time >= FIVE_MINUTES_IN_MILLIS) {
            DataPoint oldData = window.pollFirst();
            sum -= oldData.value;
        }

        window.addLast(new DataPoint(number, currentTime));
        sum += number;
    }

    public double mean() {
        if (window.isEmpty()) {
            return 0;
        }
        return sum / (double) window.size();
    }

    private static class DataPoint {
        int value;
        long time;

        public DataPoint(int value, long time) {
            this.value = value;
            this.time = time;
        }
    }
}
