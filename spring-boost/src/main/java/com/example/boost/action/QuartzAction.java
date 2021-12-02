package com.example.boost.action;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class QuartzAction {
    @Scheduled(cron = "0 * * * * ?")
    public void printTime() {
        Date date = new Date();
        System.out.println(date.getTime());
    }
}
