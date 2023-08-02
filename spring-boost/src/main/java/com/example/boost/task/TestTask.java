package com.example.boost.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TestTask implements Runnable {

    @Scheduled(fixedDelay = 3600*1000L)
    public void trigger() {
        this.run();
    }

    @Override
    public void run() {
        System.out.println(new Date());
    }
}
