package org.chao.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Slf4j
@DisallowConcurrentExecution
public class Job1 implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("Job1 执行" + new Date());
        try {
            TimeUnit.MINUTES.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("Job1 执行完成");
    }
}
