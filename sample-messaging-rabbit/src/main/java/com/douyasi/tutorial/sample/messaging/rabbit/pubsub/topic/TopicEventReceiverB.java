package com.douyasi.tutorial.sample.messaging.rabbit.pubsub.topic;

import com.douyasi.tutorial.sample.messaging.config.RabbitConfig;
import com.douyasi.tutorial.sample.messaging.rabbit.pubsub.EventVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * EventReceiverB
 *
 * @author raoyc
 */
@Component
@Slf4j
public class TopicEventReceiverB {

    /**
     * only `pay` event type message will routing here
     * see `bindingTopicExchangeToEventBQueue()` function in RabbitConfig
     *
     * @param eventVO
     */
    @RabbitListener(queues = RabbitConfig.TOPIC_EVENT_B_QUEUE)
    @RabbitHandler
    public void process(EventVO eventVO) {
        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("<TOPIC_EVENT_B_QUEUE> receive event: " + eventVO.toString());
        log.info("<TOPIC_EVENT_B_QUEUE> receive event: {}", eventVO);
    }
}
