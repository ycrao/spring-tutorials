package com.douyasi.tutorial.sample.task.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * SpringSchedulingConfig
 *
 * If you're using spring scheduling in a all-in-one project,
 * but multiple replicas deployed and make them isolated by different service type.
 * Such as one for `web/api` service, another copy for `cron/task/job` service,
 * and last one for `async/messaging/queue` service.
 * You activate it by setting `project.service.type` property value to "task".
 *
 * @author raoyc
 */
@Configuration
@ConditionalOnExpression("'${project.service.type}'.equals('task')")
@EnableScheduling
public class SpringSchedulingConfig {
    
}
