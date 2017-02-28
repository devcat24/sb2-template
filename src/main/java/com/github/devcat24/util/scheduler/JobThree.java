package com.github.devcat24.util.scheduler;

import org.quartz.*;

import java.util.Date;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class JobThree implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            System.out.println(" SchedJob #2 - 1 > execute on " + new Date());
            Thread.sleep(3000);
            System.out.println(" SchedJob #2 - 2 > execute on " + new Date());
            Thread.sleep(3000);
            System.out.println(" SchedJob #2 - 3 > execute on " + new Date());
            Thread.sleep(3000);
            System.out.println(" SchedJob #2 - 4 > execute on " + new Date());
            Thread.sleep(3000);
            System.out.println(" SchedJob #2 - 5 > execute on " + new Date());
        }   catch(Exception e){
            e.printStackTrace();
        }
    }
}
