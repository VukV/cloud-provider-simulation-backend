package com.raf.cloudproviderbackend.configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class QueueConfig {

    @Bean
    public Queue machineTaskQueue(){
        return new Queue("machineTaskQueue", false);
    }

    //systemctl status rabbitmq-server.service
    //systemctl start rabbitmq-server
    //          (stop)
    //sudo systemctl enable  rabbitmq-server
    //               (disable)

    //sudo rabbitmq-server
}
