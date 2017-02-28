package com.github.devcat24.util.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

public class JobTwo implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println(" SchedJob #1 > execute on " + new Date());
    }
    //[Job #1: Cron Worker] -
}