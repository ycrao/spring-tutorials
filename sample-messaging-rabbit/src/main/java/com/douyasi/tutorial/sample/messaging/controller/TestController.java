package com.douyasi.tutorial.sample.messaging.controller;

import com.douyasi.tutorial.sample.messaging.rabbit.pubsub.EventVO;
import com.douyasi.tutorial.sample.messaging.rabbit.pubsub.direct.DirectEventSender;
import com.douyasi.tutorial.sample.messaging.rabbit.pubsub.fanout.FanoutEventSender;
import com.douyasi.tutorial.sample.messaging.rabbit.pubsub.topic.TopicEventSender;
import com.douyasi.tutorial.sample.messaging.rabbit.simple.HelloSender;
import com.douyasi.tutorial.sample.messaging.rabbit.worker.CalcJobProducer;
import com.douyasi.tutorial.sample.messaging.rabbit.worker.vo.CalcJobVO;
import com.douyasi.tutorial.sample.messaging.util.RandomUtil;
import com.douyasi.tutorial.sample.messaging.util.UuidUtil;
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

    @Autowired
    private FanoutEventSender fanoutEventSender;

    @Autowired
    private TopicEventSender topicEventSender;

    @Autowired
    private DirectEventSender directEventsSender;


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
            calcJobs[i].setB(RandomUtil.randomInt(1, 20));
            calcJobs[i].setOp(operations[i%4]);
            workerProducer.send(calcJobs[i]);
        });
        return "just for testing rabbitmq in simple and worker mode";
    }

    /**
     * testing rabbitmq pubsub mode
     */
    @GetMapping(value="/test-pub-sub", produces = "text/plain")
    public String getTestPubSub() {
        final EventVO[] events = new EventVO[5];
        final String[] eventTypes = {
                EventVO.EVENT_TYPE_LOGIN,
                EventVO.EVENT_TYPE_ADD_TO_CART,
                EventVO.EVENT_TYPE_CHECKOUT,
                EventVO.EVENT_TYPE_PAY,
                EventVO.EVENT_TYPE_LOGOUT,
        };
        events[0] = (new EventVO())
                .setUuid(UuidUtil.uuid())
                .setType(eventTypes[0])
                .setUserId(253981L)
                .setBody("Bob login the AMAZON - a shopping website at 09:00 am");
        events[1] = (new EventVO())
                .setUuid(UuidUtil.uuid())
                .setType(eventTypes[1])
                .setUserId(253981L)
                .setBody("After browsing some goods pages, Bob add a black `T-shirt` to his cart at 09:26 am");
        events[2] = (new EventVO())
                .setUuid(UuidUtil.uuid())
                .setType(eventTypes[2])
                .setUserId(253981L)
                .setBody("Bob checkout the cart and booking a order at 09:28 am");
        events[3] = (new EventVO())
                .setUuid(UuidUtil.uuid())
                .setType(eventTypes[3])
                .setUserId(253981L)
                .setBody("Bob pay his order online by credit card at 09:30 am");
        events[4] = (new EventVO())
                .setUuid(UuidUtil.uuid())
                .setType(eventTypes[4])
                .setUserId(253981L)
                .setBody("Bob logout the website at 09:31 am");
        IntStream.range(0, 5).forEach(i -> {
            fanoutEventSender.send(events[i]);
            if (events[i].getType() == EventVO.EVENT_TYPE_LOGIN) {
                directEventsSender.sendLoginEvent(events[i]);
            }
            if (events[i].getType() == EventVO.EVENT_TYPE_CHECKOUT) {
                directEventsSender.sendCheckoutEvent(events[i]);
            }
            topicEventSender.send(events[i]);
        });
        return "just for testing rabbitmq in pubsub mode";
    }

}

