package com.douyasi.tutorial.sample.messaging.rabbit.pubsub.direct;

import com.douyasi.tutorial.sample.messaging.config.RabbitConfig;
import com.douyasi.tutorial.sample.messaging.rabbit.pubsub.EventVO;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * EventsSender
 *
 * @author raoyc
 */
@Component
public class DirectEventSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    /**
     * only take care about `login` event
     * consumer by DIRECT_EVENT_A_QUEUE
     *
     * @param eventVO eventVO
     * @return boolean
     */
    public boolean sendLoginEvent(EventVO eventVO) {
        if ((eventVO != null) && (eventVO.getType() == EventVO.EVENT_TYPE_LOGIN)) {
            rabbitTemplate.convertAndSend(RabbitConfig.DIRECT_EVENTS_EXCHANGE, RabbitConfig.ROUTING_KEY_LOGIN, eventVO);
            return true;
        }
        return false;
    }

    /**
     * only take care about `checkout` event
     * consumer by DIRECT_EVENT_B_QUEUE
     *
     * @param eventVO eventVO
     * @return boolean
     */
    public boolean sendCheckoutEvent(EventVO eventVO) {
        if ((eventVO != null) && (eventVO.getType() == EventVO.EVENT_TYPE_CHECKOUT)) {
            rabbitTemplate.convertAndSend(RabbitConfig.DIRECT_EVENTS_EXCHANGE, RabbitConfig.ROUTING_KEY_CHECKOUT, eventVO);
            return true;
        }
        return false;
    }
}
