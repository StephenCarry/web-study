package com.example.boost.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class TestTask implements Runnable {

    private static final ExecutorService threadPool = Executors.newFixedThreadPool(1);

    @Scheduled(fixedDelay = 10*1000L)
    public void trigger() {
        threadPool.submit(this);
    }

    @Override
    public void run() {
        System.out.println(new Date());
    }
}
