package com.douyasi.tutorial.sample.messaging.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitConfig
 *
 * @author raoyc
 */
@Configuration
public class RabbitConfig {

    /**
     * routing key: for `login` type event
     */
    public final static String ROUTING_KEY_LOGIN  = "event.login";

    /**
     * routing key: for `add-to-cart` type event
     */
    public final static String ROUTING_KEY_ADD_TO_CART  = "event.add-to-cart";

    /**
     * routing key: for `checkout` type event
     */
    public final static String ROUTING_KEY_CHECKOUT = "event.checkout";

    /**
     * routing key: for `pay` type event
     */
    public final static String ROUTING_KEY_PAY = "event.pay";

    /**
     * routing key: for `logout` type event
     */
    public final static String ROUTING_KEY_LOGOUT = "event.logout";

    /**
     * using json serial message
     * @return
     */
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * simple mode: a text queue just echo hello world
     */
    public final static String ECHO_STRING_QUEUE = "echo-string.que";

    /**
     * echoStringQueue: queue for simple mode
     *
     * @return
     */
    @Bean
    public Queue echoStringQueue() {
        return QueueBuilder.durable(RabbitConfig.ECHO_STRING_QUEUE)
                // exclusive
                .exclusive()
                // auto-delete
                .autoDelete()
                .build();
    }

    /**
     * worker mode: one producer with multi consumer
     */
    public final static String CALC_JOB_QUEUE = "calc-job.que";

    /**
     * calcJobQueue: queue for worker mode
     *
     * @return
     */
    @Bean
    public Queue calcJobQueue() {
        return QueueBuilder.durable(RabbitConfig.CALC_JOB_QUEUE).build();
    }

    /**
     * pubsub fanout mode: one queue
     */
    public final static String FANOUT_EVENT_A_QUEUE = "fanout.event-a.que";

    /**
     * pubsub fanout mode: another queue
     */
    public final static String FANOUT_EVENT_B_QUEUE = "fanout.event-b.que";

    /**
     * pubsub fanout mode: exchange for fanout
     */
    public final static String FANOUT_EVENTS_EXCHANGE = "fanout-events.ex";

    /**
     * fanoutEventAQueue: a queue for pubsub fanout mode
     *
     * @return
     */
    @Bean
    public Queue fanoutEventAQueue() {
        return QueueBuilder.durable(FANOUT_EVENT_A_QUEUE).build();
    }

    /**
     * fanoutEventBQueue: another queue for pubsub fanout mode
     *
     * @return
     */
    @Bean
    public Queue fanoutEventBQueue() {
        return QueueBuilder.durable(FANOUT_EVENT_B_QUEUE).build();
    }

    /**
     * fanoutEventsExchange: exchange for pubsub fanout mode
     *
     * @return
     */
    @Bean
    FanoutExchange fanoutEventsExchange() {
        return (FanoutExchange) ExchangeBuilder.fanoutExchange(FANOUT_EVENTS_EXCHANGE).build();
    }

    /**
     * bindingFanoutExchangeToEventAQueue
     *
     * @param fanoutEventAQueue
     * @param fanoutEventsExchange
     * @return
     */
    @Bean
    Binding bindingFanoutExchangeToEventAQueue(Queue fanoutEventAQueue, FanoutExchange fanoutEventsExchange) {
        return BindingBuilder.bind(fanoutEventAQueue).to(fanoutEventsExchange);
    }

    /**
     * bindingFanoutExchangeToEventBQueue
     *
     * @param fanoutEventBQueue
     * @param fanoutEventsExchange
     * @return
     */
    @Bean
    Binding bindingFanoutExchangeToEventBQueue(Queue fanoutEventBQueue, FanoutExchange fanoutEventsExchange) {
        return BindingBuilder.bind(fanoutEventBQueue).to(fanoutEventsExchange);
    }

    /**
     * pubsub topic mode: one queue
     */
    public final static String TOPIC_EVENT_A_QUEUE = "topic.event-a.que";

    /**
     * pubsub topic mode: another queue
     */
    public final static String TOPIC_EVENT_B_QUEUE = "topic.event-b.que";

    /**
     * pubsub topic mode: exchange for topic
     */
    public final static String TOPIC_EVENTS_EXCHANGE = "topic.events.ex";

    /**
     * topicEventAQueue: a queue for pubsub topic mode
     *
     * @return
     */
    @Bean
    public Queue topicEventAQueue() {
        return QueueBuilder.durable(TOPIC_EVENT_A_QUEUE).build();
    }

    /**
     * topicEventAQueue: another queue for pubsub topic mode
     *
     * @return
     */
    @Bean
    public Queue topicEventBQueue() {
        return QueueBuilder.durable(TOPIC_EVENT_B_QUEUE).build();
    }

    /**
     * topicEventsExchange: exchange for pubsub topic mode
     *
     * @return
     */
    @Bean
    TopicExchange topicEventsExchange() {
        return ExchangeBuilder.topicExchange(TOPIC_EVENTS_EXCHANGE).build();
    }

    /**
     * bindingTopicExchangeToEventAQueue
     *
     * @param topicEventAQueue
     * @param topicEventsExchange
     * @return
     */
    @Bean
    Binding bindingTopicExchangeToEventAQueue(Queue topicEventAQueue, TopicExchange topicEventsExchange) {
        return BindingBuilder.bind(topicEventAQueue).to(topicEventsExchange).with(ROUTING_KEY_ADD_TO_CART);
    }

    /**
     * bindingTopicExchangeToEventAQueue
     *
     * @param topicEventAQueue
     * @param topicEventsExchange
     * @return
     */
    @Bean
    Binding bindingTopicExchangeToEventBQueue(Queue topicEventAQueue, TopicExchange topicEventsExchange) {
        return BindingBuilder.bind(topicEventAQueue).to(topicEventsExchange).with(ROUTING_KEY_PAY);
    }


    /**
     * pubsub direct mode: one queue
     */
    public final static String DIRECT_EVENT_A_QUEUE = "direct.event-a.que";

    /**
     * pubsub direct mode: another queue
     */
    public final static String DIRECT_EVENT_B_QUEUE = "direct.event-b.que";

    /**
     * pubsub direct mode: exchange for direct
     */
    public final static String DIRECT_EVENTS_EXCHANGE = "direct.events.ex";

    /**
     * directEventAQueue: a queue for pubsub topic mode
     *
     * @return
     */
    @Bean
    public Queue directEventAQueue() {
        return QueueBuilder.durable(DIRECT_EVENT_A_QUEUE).build();
    }

    /**
     * directEventAQueue: another queue for pubsub topic mode
     *
     * @return
     */
    @Bean
    public Queue directEventBQueue() {
        return QueueBuilder.durable(DIRECT_EVENT_B_QUEUE).build();
    }

    /**
     * directEventsExchange: exchange for pubsub direct mode
     *
     * @return
     */
    @Bean
    DirectExchange directEventsExchange() {
        return ExchangeBuilder.directExchange(DIRECT_EVENTS_EXCHANGE).durable(true).build();
    }

    /**
     * bindingDirectExchangeToEventAQueue
     *
     * @param directEventAQueue
     * @param directEventsExchange
     * @return
     */
    @Bean
    Binding bindingDirectExchangeToEventAQueue(Queue directEventAQueue, DirectExchange directEventsExchange) {
        return BindingBuilder.bind(directEventAQueue).to(directEventsExchange).with(ROUTING_KEY_LOGIN);
    }

    /**
     * bindingDirectExchangeToEventBQueue
     *
     * @param directEventBQueue
     * @param directEventsExchange
     * @return
     */
    @Bean
    Binding bindingDirectExchangeToEventBQueue(Queue directEventBQueue, DirectExchange directEventsExchange) {
        return BindingBuilder.bind(directEventBQueue).to(directEventsExchange).with(ROUTING_KEY_CHECKOUT);
    }
}
