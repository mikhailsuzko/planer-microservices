package com.sma.micro.planner.plannerusers.mq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageProducer {
    private final Sinks.Many<Message<Long>> innerBus;

    public void sendNewUserMessage(Long userId) {
        innerBus.emitNext(MessageBuilder.withPayload(userId).build(), Sinks.EmitFailureHandler.FAIL_FAST);
        log.info("Message send: {}", userId);
    }
}
