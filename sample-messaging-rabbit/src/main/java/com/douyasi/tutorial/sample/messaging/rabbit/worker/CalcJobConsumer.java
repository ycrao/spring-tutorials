package com.douyasi.tutorial.sample.messaging.rabbit.worker;

import com.douyasi.tutorial.sample.messaging.config.RabbitConfig;
import com.douyasi.tutorial.sample.messaging.rabbit.worker.vo.CalcJobVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * CalcJobConsumer
 *
 * @author raoyc
 */
@Slf4j
@Component
public class CalcJobConsumer {

    private static AtomicInteger atomicInteger = new AtomicInteger();

    @RabbitListener(queues = RabbitConfig.CALC_JOB_QUEUE, concurrency = "2")
    @RabbitHandler
    public void process(CalcJobVO calcJob) throws Exception {
        int index = atomicInteger.getAndIncrement();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String startTime = sdf.format(new Date());
        log.info("<CalcJobConsumer> start @" + startTime + "......");
        System.out.println("atomicInteger value => " + index);
        Thread.sleep(5000);
        Integer a = calcJob.getA();
        Integer b = calcJob.getB();
        String op = calcJob.getOp();
        Integer c = 0;

        switch (op) {
            case "+":
                c = a + b;
                break;
            case "-":
                c = a - b;
                break;
            case "*":
                c = a * b;
                break;
            case "%":
                c = a % b;
                break;
            default:
                throw new Exception("op not supported!");
        }
        System.out.printf("Arithmetic problem is: %d %s %d = ?, the result is: %d ------\n", a, op, b, c);
        String endTime = sdf.format(new Date());
        log.info("<CalcJobConsumer> end @" + endTime + "......");
    }
}
