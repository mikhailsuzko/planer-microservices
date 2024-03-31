package com.sma.micro.planner.plannerutils.rest.webclient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Slf4j
public class UserWebClientBuilder {
    private static final String BASE_URL_DATA = "https://localhost:8765/planner-todo/data/";

    public boolean initUser(MultiValueMap<String, String> headers) {
        try {
            return initUserData(headers).blockFirst() != null;
        } catch (Exception e) {
            log.error("An error has occurred: {}", e.getMessage());
        }
        return false;
    }

    private Flux<Boolean> initUserData(MultiValueMap<String, String> headers) {
        return WebClient.create(BASE_URL_DATA)
                .post()
                .uri("init")
                .headers(h -> h.addAll(headers))
                .retrieve()
                .bodyToFlux(Boolean.class);
    }
}
