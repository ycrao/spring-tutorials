package com.douyasi.tutorial.sample.messaging.rabbit.pubsub.fanout;

import com.douyasi.tutorial.sample.messaging.config.RabbitConfig;
import com.douyasi.tutorial.sample.messaging.rabbit.pubsub.EventVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * EventReceiverA
 *
 * @author raoyc
 */
@Component
@Slf4j
public class EventReceiverA {

    @RabbitListener(queues = RabbitConfig.FANOUT_EVENT_A_QUEUE)
    @RabbitHandler
    public void process(EventVO eventVO) {
        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("<FANOUT_EVENT_A_QUEUE> receive event: " + eventVO.toString());
        log.info("<FANOUT_EVENT_A_QUEUE> receive event: {}", eventVO);
    }
}
