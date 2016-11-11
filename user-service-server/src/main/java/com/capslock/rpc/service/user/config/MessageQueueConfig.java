package com.capslock.rpc.service.user.config;

import com.capslock.commons.config.MqConfig;
import com.capslock.commons.mq.MessageQueuePublisher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by alvin.
 */
@Configuration
public class MessageQueueConfig {
    @Value("${mq.host}")
    private String host;
    @Value("${mq.username}")
    private String username;
    @Value("${mq.password}")
    private String password;

    @Bean
    private MessageQueuePublisher sessMessageQueuePublisher() throws IOException, TimeoutException {
        return new MessageQueuePublisher(host, username, password, MqConfig.SESSION_CLUSTER_QUEUE_NAME);
    }
}
