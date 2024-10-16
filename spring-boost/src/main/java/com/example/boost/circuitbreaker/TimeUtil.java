package com.example.boost.circuitbreaker;

public class TimeUtil {

    private static volatile long currentTime;

    static {
        currentTime = System.currentTimeMillis();
        Thread thread = new Thread(() -> {
            while (true) {
                currentTime = System.currentTimeMillis();
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        thread.setName("breaker-time-daemon-thread");
        thread.setDaemon(true);
        thread.start();
    }

    public static long currentTime() {
        return currentTime;
    }

}
