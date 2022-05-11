package com.douyasi.tutorial.sample.task.schedule.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * EchoTaskJob
 * scheduling by using `@Scheduled` annotation
 *
 * @author raoyc
 */
@Component
@Slf4j
@ConditionalOnExpression("'${project.service.type}'.equals('task')")
public class EchoTaskJob {

    @Scheduled(cron = "0/10 * * * * ?")
    public void job1() {
        log.debug("[job1] run every 10 seconds by cron %s", new Date());
        // TODO you can put some other code to handle more complex job
        System.out.println("[job1] run every 10 seconds by cron " + new Date());
    }

    @Scheduled(fixedRate = 2000)
    public void job2() {
        log.debug("[job2] run every 2 seconds by fixedRate %s", new Date());
        // TODO you can put some other code to handle more complex job
        System.out.println("[job2] run every 2 seconds by fixedRate " + new Date());
    }

    @Scheduled(fixedDelay = 4000, initialDelay = 5000)
    public void job3() {
        log.debug("[job3] run every 4 seconds by fixedRate, but delay 5 seconds when start %s", new Date());
        // TODO you can put some other code to handle more complex job
        System.out.println("[job3] run every 4 seconds by fixedRate, but delay 5 seconds when start " + new Date());
    }
}
