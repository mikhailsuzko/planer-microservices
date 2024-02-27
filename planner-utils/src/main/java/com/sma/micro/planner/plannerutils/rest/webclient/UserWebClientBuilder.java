package com.sma.micro.planner.plannerutils.rest.webclient;

import com.sma.micro.planner.plannerentity.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
@Slf4j
public class UserWebClientBuilder {
    private static final String BASE_URL = "http://localhost:8765/planner-users/user/";

    public boolean userExist(Long id) {
        try {
            return getUser(id).blockFirst() != null;
        } catch (Exception e) {
            log.error("An error has occurred: {}", e.getMessage());
        }
        return false;
    }

    public Flux<User> getUser(Long id) {
        return WebClient.create(BASE_URL)
                .post()
                .uri("id")
                .bodyValue(id)
                .retrieve()
                .bodyToFlux(User.class);
    }
}
