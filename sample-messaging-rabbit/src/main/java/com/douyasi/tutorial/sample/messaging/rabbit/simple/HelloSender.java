package com.douyasi.tutorial.sample.messaging.rabbit.simple;

import com.douyasi.tutorial.sample.messaging.config.RabbitConfig;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * HelloSender
 *
 * see Controller/TestController
 *
 * @author raoyc
 */
@Component
public class HelloSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    /**
     * send - send string message
     *
     * @param message
     * @return boolean
     */
    public boolean send(String message) {
        if (message == null) {
            // if empty message
            // return false
            return false;
        }

        rabbitTemplate.convertAndSend(RabbitConfig.ECHO_STRING_QUEUE, message);
        
        return true;
    }
}
