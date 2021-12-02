package com.itranswarp.learnjava.web;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@EnableScheduling
public class QuartzAction {
    @Scheduled(cron = "0 * * * * ?")
    public void printTime() {
        Date date = new Date();
        System.out.println(date.getTime());
    }
}
