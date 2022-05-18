package com.douyasi.tutorial.sample.messaging.rabbit.pubsub.topic;

import com.douyasi.tutorial.sample.messaging.config.RabbitConfig;
import com.douyasi.tutorial.sample.messaging.rabbit.pubsub.EventVO;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * TopicSender
 *
 * @author raoyc
 */
@Component
public class TopicEventSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    /**
     * auto push message to topic exchange with different routing key by event type
     *
     * @param eventVO
     * @return
     */
    public boolean send(EventVO eventVO) {
        if (eventVO != null) {
            // event.login, event.add-to-cart, event.checkout, event.pay, event.logout ...
            String routingKey = "event." + eventVO.getType();
            rabbitTemplate.convertAndSend(RabbitConfig.TOPIC_EVENTS_EXCHANGE, routingKey, eventVO);
            return true;
        }
        return false;
    }
}
