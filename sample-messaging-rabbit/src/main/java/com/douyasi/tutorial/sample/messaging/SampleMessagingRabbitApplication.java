package com.douyasi.tutorial.sample.messaging;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author raoyc
 */
@SpringBootApplication
@EnableWebMvc
public class SampleMessagingRabbitApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(SampleMessagingRabbitApplication.class, args);
    }

}
