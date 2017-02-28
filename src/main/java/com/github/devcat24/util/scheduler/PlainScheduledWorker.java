package com.github.devcat24.util.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component("PlainScheduledWorker")
public class PlainScheduledWorker {

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
	@Scheduled(cron="5 * * * * ?")
	public void doWorkByCron(){
		System.out.println("[PlainScheduledWorker: Cron Worker] - "+ (new SimpleDateFormat("HH:mm:ss")).format(new Date()));
	}


    // Set Cron expression using application.properties
    @Scheduled(cron="${spring.template.quartz.job1.cron}")
    public void doWorkByCronProperties(){
        System.out.println("[PlainScheduledWorker: Cron Worker] - "+ (new SimpleDateFormat("HH:mm:ss")).format(new Date()));
    }


    @Scheduled(fixedRate = 3000)
	public void doWorkByFixedRate(){
		System.out.println("[PlainScheduledWorker: FixedRate Worker] - "+ (new SimpleDateFormat("HH:mm:ss")).format(new Date()));
	}

}
