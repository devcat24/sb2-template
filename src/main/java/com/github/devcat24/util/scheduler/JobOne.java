package com.github.devcat24.util.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

@SuppressWarnings("unused")
public class JobOne implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println(" SchedJob #000 > execute on " + new Date());
    }
}
