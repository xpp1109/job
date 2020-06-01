package org.chao.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class JobAction {
    @Autowired
    private Scheduler scheduler;
    public static final String TRIGGER_KEY_NAME="shizc-trigger-key-name";
    public static final String TRIGGER_KEY_GROUP = "shizc-trigger-key-group";

    public static final String JOB_NAME="shizc-job-name";
    public static final String JOB_GROUP = "shizc-job-group";


    @PostMapping("/add")
    public void add() throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(TRIGGER_KEY_NAME, TRIGGER_KEY_GROUP);
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        if (null == trigger) {
            Class<Job1> clazz = Job1.class;
            JobKey jobKey = JobKey.jobKey(JOB_NAME, JOB_GROUP);
            JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(jobKey).build();
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0/10 * * * * ?");
            trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey)
                    .withSchedule(scheduleBuilder).build();
            scheduler.scheduleJob(jobDetail, trigger);
            scheduler.start();
        } else {
            log.info("job已存在:{}", trigger.getKey());
        }
    }
}
