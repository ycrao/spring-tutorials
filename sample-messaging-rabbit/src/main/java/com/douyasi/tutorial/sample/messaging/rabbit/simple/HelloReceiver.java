package com.douyasi.tutorial.sample.messaging.rabbit.simple;

import com.douyasi.tutorial.sample.messaging.config.RabbitConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * HelloReceiver
 *
 * @author raoyc
 */
@Component
@Slf4j
public class HelloReceiver {

    @RabbitListener(queues = RabbitConfig.ECHO_STRING_QUEUE)
    @RabbitHandler
    public void process(String message) {
        try {
            Thread.sleep(5000);
        } catch (Exception e) {
             e.printStackTrace();
        }
        log.info("<HelloReceiver> message => " + message);
    }
}
