package com.douyasi.tutorial.sample.task.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * TaskSchedulerExample
 * see: https://www.baeldung.com/spring-task-scheduler
 *
 * @author raoyc
 */
@Component
@ConditionalOnExpression("'${project.service.type}'.equals('task')")
public class TaskSchedulerExample {

    @Autowired
    private ThreadPoolTaskScheduler taskScheduler;

    @PostConstruct
    public void scheduleRunnableWithCronTrigger() {
        taskScheduler.schedule(new RunnableTask("Current Date"), new Date());
        taskScheduler.scheduleWithFixedDelay(new RunnableTask("Fixed 1 second Delay"), 1000);
        //// taskScheduler.scheduleWithFixedDelay(new RunnableTask("Current Date Fixed 1 second Delay"), new Date(), 1000);
        //// taskScheduler.scheduleAtFixedRate(new RunnableTask("Fixed Rate of 2 seconds"), new Date(), 2000);
        taskScheduler.scheduleAtFixedRate(new RunnableTask("Fixed Rate of 2 seconds"), 2000);
        taskScheduler.schedule(new RunnableTask("Cron Trigger"), new CronTrigger("*/10 * * * * ?"));
        PeriodicTrigger periodicTrigger = new PeriodicTrigger(10, TimeUnit.SECONDS);
        periodicTrigger.setFixedRate(true);
        periodicTrigger.setInitialDelay(1000);
        taskScheduler.schedule(new RunnableTask("Periodic Trigger"), periodicTrigger);
    }

    class RunnableTask implements Runnable {

        private String message;

        public RunnableTask(String message) {
            this.message = message;
        }

        @Override
        public void run() {
            System.out.println("Runnable Task with " + message + " on thread " + Thread.currentThread().getName());
        }
    }
}
