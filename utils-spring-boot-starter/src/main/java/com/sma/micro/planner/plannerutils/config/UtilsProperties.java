package com.sma.micro.planner.plannerutils.config;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Data
@ConfigurationProperties(prefix = "planner.utils")
public class UtilsProperties {
    /**
     * to enable utils on service layer
     */
    private boolean enabled;

    @PostConstruct
    void init() {
        System.out.println("Utils properties initialized: " + this);
    }
}
