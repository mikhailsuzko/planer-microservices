package com.sma.micro.planner.plannerusers.config.mq;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.util.concurrent.Queues;

import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
public class MessageConfig {
    @Bean
    public Supplier<Flux<Message<Long>>> newUserProduce(Sinks.Many<Message<Long>> innerBus) {
        return innerBus::asFlux;
    }

    @Bean Sinks.Many<Message<Long>> innerBus() {
        return Sinks.many().multicast().onBackpressureBuffer(Queues.SMALL_BUFFER_SIZE, false);
    }
}
