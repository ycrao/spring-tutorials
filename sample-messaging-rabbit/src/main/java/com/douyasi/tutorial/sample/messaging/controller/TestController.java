package com.douyasi.tutorial.sample.messaging.controller;

import com.douyasi.tutorial.sample.messaging.rabbit.simple.HelloSender;
import com.douyasi.tutorial.sample.messaging.rabbit.worker.CalcJobProducer;
import com.douyasi.tutorial.sample.messaging.rabbit.worker.vo.CalcJobVO;
import com.douyasi.tutorial.sample.messaging.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.IntStream;

/**
 * TestController
 *
 * @author raoyc
 */
@RestController
public class TestController {

    @Autowired
    private HelloSender simpleSender;

    @Autowired
    private CalcJobProducer workerProducer;


    /**
     * do some tests
     */
    @GetMapping(value="/test", produces = "text/plain")
    public String getTest() {
        simpleSender.send("Hello World to RabbitMQ");
        final CalcJobVO[] calcJobs = new CalcJobVO[20];
        final String[] operations = new String[] {"+", "-", "*", "%"};
        IntStream.range(0, 20).forEach(i -> {
            calcJobs[i] = new CalcJobVO();
            calcJobs[i].setA(RandomUtil.randomInt(1, 100));
            calcJobs[i].setB(RandomUtil.randomInt(1, 100));
            calcJobs[i].setOp(operations[i%4]);
            workerProducer.send(calcJobs[i]);
        });
        return "just for test";
    }
}

