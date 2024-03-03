package com.sma.micro.planner.plannerentity.config;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Slf4j
@Data
@NoArgsConstructor
@ConfigurationProperties(prefix = "planner.entity")
public class EntityProperties {
    /**
     * to enable entities on service layer
     */
    private boolean enabled;

    @PostConstruct
    void init() {
        log.info("Entity properties initialized: {}", this);
    }
}
