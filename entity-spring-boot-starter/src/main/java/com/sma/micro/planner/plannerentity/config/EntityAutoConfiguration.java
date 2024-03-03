package com.sma.micro.planner.plannerentity.config;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Slf4j
@Configuration
@EnableConfigurationProperties(EntityProperties.class)
@ConditionalOnClass(EntityProperties.class)
public class EntityAutoConfiguration {

    @PostConstruct
    void init() {
        log.info("EntityAutoConfiguration initialized");
    }

    @Configuration
    @EntityScan("com.sma.micro.planner.plannerentity.entity")
    public static class EntityConfig {
    }

}
