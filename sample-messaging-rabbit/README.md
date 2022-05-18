# sample-messaging-rabbit


### Config

See [RabbitConfig](./src/main/java/com/douyasi/tutorial/sample/messaging/config/RabbitConfig.java) .

### Simple Mode

>   Using a raw text queue just echo such as `hello world` string.

`Sender` code:

```java
package com.douyasi.tutorial.sample.messaging.rabbit.simple;

import com.douyasi.tutorial.sample.messaging.config.RabbitConfig;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * HelloSender
 *
 * see Controller/TestController
 *
 * @author raoyc
 */
@Component
public class HelloSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    /**
     * send - send string message
     *
     * @param message
     * @return boolean
     */
    public boolean send(String message) {
        if (message == null) {
            // if empty message
            // return false
            return false;
        }

        rabbitTemplate.convertAndSend(RabbitConfig.ECHO_STRING_QUEUE, message);
        
        return true;
    }
}
```

`Receiver` code:

 ```java
 package com.douyasi.tutorial.sample.messaging.rabbit.simple;

import com.douyasi.tutorial.sample.messaging.config.RabbitConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * HelloReceiver
 *
 * @author raoyc
 */
@Component
@Slf4j
public class HelloReceiver {

    @RabbitListener(queues = RabbitConfig.ECHO_STRING_QUEUE)
    @RabbitHandler
    public void process(String message) {
        try {
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("<HelloReceiver> message => " + message);
    }
}
 ```

### Worker Mode

>   One producer with multiple consumer workers.

Just see code in [worker](./src/main/java/com/douyasi/tutorial/sample/messaging/rabbit/worker) directory.

### `Publish/Subscribe` Mode

>   It has three implements according to exchange type: `direct`,`fanout` and `topic` .

Just see code in [pubsub](./src/main/java/com/douyasi/tutorial/sample/messaging/rabbit/pubsub) directory.


### How to test?

I put message-pushing code in [controller/TestController](./src/main/java/com/douyasi/tutorial/sample/messaging/controller/TestController.java). You can visit `http://localhost:16603/test` and `http://localhost:16603/test-pub-sub` link after running the app. Then you will get some log in console stdout. Also, you can visit your RabbitMQ management dashboard, normal its url is `http://127.0.0.1:15672/#/queues` when installed on your local development machine.

