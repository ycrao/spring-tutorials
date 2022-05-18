package com.douyasi.tutorial.sample.messaging.rabbit.pubsub.fanout;

import com.douyasi.tutorial.sample.messaging.config.RabbitConfig;
import com.douyasi.tutorial.sample.messaging.rabbit.pubsub.EventVO;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * FanoutEventSender
 *
 * @author raoyc
 */
@Component
public class FanoutEventSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public boolean send(EventVO eventVO) {
        if (eventVO != null) {
            rabbitTemplate.convertAndSend(RabbitConfig.FANOUT_EVENTS_EXCHANGE, "", eventVO);
            return true;
        }
        return false;
    }
}
