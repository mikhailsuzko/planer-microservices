package com.sma.micro.planner.plannerutils.rest.webclient;

import com.sma.micro.planner.plannerentity.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Slf4j
public class UserWebClientBuilder {
    private static final String BASE_URL_USER = "http://localhost:8765/planner-users/user/";
    private static final String BASE_URL_DATA = "http://localhost:8765/planner-todo/data/";

    public boolean userExist(Long id) {
        try {
            return getUser(id).blockFirst() != null;
        } catch (Exception e) {
            log.error("An error has occurred: {}", e.getMessage());
        }
        return false;
    }

    public Flux<User> getUser(Long id) {
        return WebClient.create(BASE_URL_USER)
                .post()
                .uri("id")
                .bodyValue(id)
                .retrieve()
                .bodyToFlux(User.class);
    }

    public Flux<Boolean> initUserData(Long id) {
        return WebClient.create(BASE_URL_DATA)
                .post()
                .uri("init")
                .bodyValue(id)
                .retrieve()
                .bodyToFlux(Boolean.class);

    }
}
