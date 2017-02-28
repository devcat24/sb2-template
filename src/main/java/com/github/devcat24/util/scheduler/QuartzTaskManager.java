package com.github.devcat24.util.scheduler;


import org.quartz.*;
import org.quartz.impl.StdScheduler;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;

// Quartz Cron Expression
// (Seconds) (Minutes) (Hours) (Day of month) (Month) (Day of week) (Year - optional)
// Second : 0-59
// Minutes : 0-59
// Hours : 0-23
// Day of month : 1-31
// Month : 1-12 or JAN-DEC
// Day of week : 1-7 or SUN-SAT
// Year : empty or 1970-2099
// allowed special characters ->  , - * /
//      ex. 0 15 10 ? * *  -> Fire at 10:15am every day
//          0 0/5 14 * * ? -> Fire every 5 minutes starting at 2pm and ending at 2:55pm, every day
//          0 10,44 14 ? 3 WED -> Fire at 2:10pm and at 2:44pm every Wednesday in the month of March.
//          -> http://www.quartz-scheduler.org/documentation/quartz-1.x/tutorials/crontrigger
@SuppressWarnings("unused")
@Component("QuartzTaskManager")  // -> used for invoke 'manageTasks()' method on spring-boot startup.
public class QuartzTaskManager {
    private final String quartz_prop_file = "quartz.properties";

    @PostConstruct // -> used for invoke 'manageTasks()' method on spring-boot startup.
    public void initScheduler(){
        try {
            StdScheduler scheduler = (StdScheduler) (new StdSchedulerFactory(quartz_prop_file)).getScheduler();
            scheduler.start();
        }	catch(SchedulerException se){
            se.printStackTrace();
        }
    }

    public void stopScheduler(){
        try {
            StdScheduler scheduler = (StdScheduler) (new StdSchedulerFactory(quartz_prop_file)).getScheduler();

            //scheduler.pauseAll();
            //scheduler.resumeAll();

            scheduler.shutdown();
        }	catch(SchedulerException se){
            se.printStackTrace();
        }
    }


    public String getAllTasks(){
        String jobDesc = "";
        //String newline = System.getProperty("line.separator");
        String newline = "<br />";
        int idx = 1;

        try {
            StdScheduler scheduler = (StdScheduler) (new StdSchedulerFactory(quartz_prop_file)).getScheduler();


            for(String jobGroupName : scheduler.getJobGroupNames()){
                for(JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(jobGroupName))){
                    String jobNm = jobKey.getName();
                    //String jobGroup = jobKey.getGroup();
                    String jobStatus = "running";

                    @SuppressWarnings("unchecked")
                    List<Trigger> triggers = (List<Trigger>) scheduler.getTriggersOfJob(jobKey);
                    Date nextFireTime = triggers.get(0).getNextFireTime();
                    //JobDetail jobDetail = scheduler.getJobDetail(jobKey);

                    for(Trigger trigger : triggers){
                        Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                        if(triggerState.equals(Trigger.TriggerState.PAUSED)){
                            jobStatus = "paused";
                        }
                    }
                    jobDesc = jobDesc + idx + ". [" + jobNm + " in (" + jobGroupName + ")] : " + jobStatus + " (Next Fire Time:  " + nextFireTime + ")" + newline;
                    idx++;
                }
            }
        }	catch(SchedulerException se){
            se.printStackTrace();
        }
        return jobDesc;
    }


    // StdScheduler.getCurrentlyExecutingJobs()
    //    -> only returns current running jobs (does not means un-paused jobs !!!)
    //    -> to get the paused lists -> should use 'scheduler.getTriggerState(trigger.getKey())'
    public String getAllExecutionTasks(){

        String jobKeys = "";

        try {
            StdScheduler scheduler = (StdScheduler) (new StdSchedulerFactory(quartz_prop_file)).getScheduler();
            for(JobExecutionContext jobExecutionContext : scheduler.getCurrentlyExecutingJobs()){
                jobKeys = jobKeys + jobExecutionContext.getJobDetail().getKey().getName() + "     ";
            }
        }	catch(SchedulerException se){
            se.printStackTrace();
        }
        return jobKeys;
    }


    public boolean addTask(){
        boolean result = false;
        try {
            StdScheduler scheduler = (StdScheduler) (new StdSchedulerFactory(quartz_prop_file)).getScheduler();
            JobDetail jobDetail = JobBuilder.newJob(JobFour.class).withIdentity("Job03", "group01").build();
            Trigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("CronTrigger03", "group01").withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?")).build();
            scheduler.scheduleJob(jobDetail, cronTrigger);
            result=true;
        }	catch(SchedulerException se){
            se.printStackTrace();
        }
        return result;
    }


    public boolean deleteTask(String jobNameToDelete){
        boolean result = false;
        try {
            StdScheduler scheduler = (StdScheduler) (new StdSchedulerFactory(quartz_prop_file)).getScheduler();
            for(String jobGroupName : scheduler.getJobGroupNames()){
                for(JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(jobGroupName))){
                    String jobNm = jobKey.getName();
                    if(jobNm.equals(jobNameToDelete)){
                        scheduler.deleteJob(jobKey);
                    }
                }
            }
            result=true;

        }	catch(SchedulerException se){
            se.printStackTrace();
        }
        return result;
    }

    public void manageTasks(){
        try {
            //StdScheduler scheduler = (StdScheduler) (new StdSchedulerFactory()).getScheduler();
            StdScheduler scheduler = (StdScheduler) (new StdSchedulerFactory(quartz_prop_file)).getScheduler();

            // Scheduler Type #1
            JobDetail jobDetail = JobBuilder.newJob(JobTwo.class).withIdentity("Job01", "group01").build();
            ScheduleBuilder<?> scheduleBuilder = SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(3).repeatForever();
            Trigger scheduleTrigger = TriggerBuilder.newTrigger().withIdentity("SchedTrigger01", "group01").startNow().withSchedule(scheduleBuilder).build();
            scheduler.scheduleJob(jobDetail, scheduleTrigger);

            // Scheduler Type #2
            JobDetail jobDetail2 = JobBuilder.newJob(JobThree.class).withIdentity("Job02", "group01").build();
            Trigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("CronTrigger02", "group01").withSchedule(CronScheduleBuilder.cronSchedule("0/2 * * * * ?")).build();
            scheduler.scheduleJob(jobDetail2, cronTrigger);
            scheduler.start();

            // scheduler.shutdown();

        }	catch(SchedulerException se){
            se.printStackTrace();
        }
    }

    // StdScheduler.pauseJob does not means to stop the Thread status of Job
    //   -> once job is entered to execution it runs until finish even though StdScheduler.pauseJob invoked
    //   -> using 'deleteJob' is more efficient to manage 'scheduler' & 'job'
    public boolean pauseTask(String jobNameToPause){
        boolean result = false;
        try {
            StdScheduler scheduler = (StdScheduler) (new StdSchedulerFactory(quartz_prop_file)).getScheduler();
            for(String jobGroupName : scheduler.getJobGroupNames()){
                for(JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(jobGroupName))){
                    String jobNm = jobKey.getName();
                    if(jobNm.equals(jobNameToPause)){
                        scheduler.pauseJob(jobKey);
                    }
                }
            }
            result=true;

        }	catch(SchedulerException se){
            se.printStackTrace();
        }
        return result;
    }

    public boolean resumeTask(String jobNameToResume){
        boolean result = false;
        try {
            StdScheduler scheduler = (StdScheduler) (new StdSchedulerFactory(quartz_prop_file)).getScheduler();
            for(String jobGroupName : scheduler.getJobGroupNames()){
                for(JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(jobGroupName))){
                    String jobNm = jobKey.getName();
                    if(jobNm.equals(jobNameToResume)){
                        scheduler.resumeJob(jobKey);
                    }
                }
            }
            result=true;
        }	catch(SchedulerException se){
            se.printStackTrace();
        }
        return result;
    }

    /*
    // -> Don't use this pattern -> scheduler doesn't work properly
    public JobKey getJobKeyFromName(StdScheduler scheduler, String name){
        JobKey returnJobKey = null;
        try {
            searchLoop :
            for(String jobGroupName : scheduler.getJobGroupNames()){
                for(JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(jobGroupName))){
                    String jobNm = jobKey.getName();
                    if(jobNm.equals(name)){
                        returnJobKey = jobKey;
                    }
                    break searchLoop;
                }
            }
        }	catch(SchedulerException se){
            se.printStackTrace();
        }
        return returnJobKey;
    }*/


}