package com.touchsoft;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Consumer consumer = new Consumer();

        consumer.accept(10);
        consumer.accept(20);
        consumer.accept(30);

        Thread.sleep(60 * 1000);

        consumer.accept(40);
        consumer.accept(50);

        double mean = consumer.mean();
        System.out.println("Mean of numbers consumed in the last 5 minutes: " + mean);

        consumer.accept(50);

        Thread.sleep(5 * 60 * 1000);

        mean = consumer.mean();
        System.out.println("Mean of numbers consumed in the last 5 minutes (after waiting): " + mean);
    }
}
