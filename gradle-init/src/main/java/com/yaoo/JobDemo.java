package com.yaoo;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

public class JobDemo implements Job {
    public static void main(String[] args) throws Exception {
        SchedulerFactory factory = new StdSchedulerFactory();
        Scheduler scheduler = factory.getScheduler();

        JobDetail job = JobBuilder.newJob(JobDemo.class).withIdentity("job","group").build();

        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger","group")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(5)
                        .repeatForever()
                ).build();

        System.out.println("-----start-----");
        scheduler.scheduleJob(job,trigger);
        scheduler.start();
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
    }
}
