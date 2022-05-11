package com.douyasi.tutorial.sample.messaging.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitConfig
 *
 * @author raoyc
 */
@Configuration
public class RabbitConfig {

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }


    public final static String ECHO_STRING_QUEUE = "echo-string.que";

    public final static String CALC_JOB_QUEUE = "calc-job.que";

    @Bean
    public Queue echoStringQueue() {
        return QueueBuilder.durable(RabbitConfig.ECHO_STRING_QUEUE)
                // exclusive
                .exclusive()
                // auto-delete
                .autoDelete()
                .build();
    }

    @Bean
    public Queue calcJobQueue() {
        return QueueBuilder.durable(RabbitConfig.CALC_JOB_QUEUE).build();
    }
}
