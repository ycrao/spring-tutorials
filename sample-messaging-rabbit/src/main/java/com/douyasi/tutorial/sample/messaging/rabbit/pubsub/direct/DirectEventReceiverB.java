package com.douyasi.tutorial.sample.messaging.rabbit.pubsub.direct;

import com.douyasi.tutorial.sample.messaging.config.RabbitConfig;
import com.douyasi.tutorial.sample.messaging.rabbit.pubsub.EventVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * DirectEventReceiverB
 *
 * @author raoyc
 */
@Component
@Slf4j
public class DirectEventReceiverB {

    /**
     * only `checkout` event type message will routing here
     * see `bindingDirectExchangeToEventBQueue()` function in RabbitConfig
     *
     * @param eventVO
     */
    @RabbitListener(queues = RabbitConfig.DIRECT_EVENT_B_QUEUE)
    @RabbitHandler
    public void process(EventVO eventVO) {
        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("<DIRECT_EVENT_A_QUEUE> receive event: " + eventVO.toString());
        log.info("<DIRECT_EVENT_A_QUEUE> receive event: {}", eventVO);
    }
}
