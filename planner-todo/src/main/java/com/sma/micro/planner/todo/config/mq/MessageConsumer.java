package com.sma.micro.planner.todo.config.mq;

import com.sma.micro.planner.todo.service.InitDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
public class MessageConsumer {
    private final InitDataService initDataService;

    @Bean
    public Consumer<Message<Long>> newUserConsume() {
        return message -> initDataService.init(message.getPayload());
    }

}
