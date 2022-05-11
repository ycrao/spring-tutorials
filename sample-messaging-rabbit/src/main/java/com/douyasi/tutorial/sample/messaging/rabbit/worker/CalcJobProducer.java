package com.douyasi.tutorial.sample.messaging.rabbit.worker;

import com.douyasi.tutorial.sample.messaging.config.RabbitConfig;
import com.douyasi.tutorial.sample.messaging.rabbit.worker.vo.CalcJobVO;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * CalcJobProducer
 *
 * @author raoyc
 */
@Component
public class CalcJobProducer {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public boolean send(CalcJobVO calcJob) {
        rabbitTemplate.convertAndSend(RabbitConfig.CALC_JOB_QUEUE, calcJob);
        return true;
    }
}
