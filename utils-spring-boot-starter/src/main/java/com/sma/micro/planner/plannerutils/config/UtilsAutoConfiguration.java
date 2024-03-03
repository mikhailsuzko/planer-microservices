package com.sma.micro.planner.plannerutils.config;

import com.sma.micro.planner.plannerutils.aop.LoggingAspect;
import com.sma.micro.planner.plannerutils.converter.JwtConverter;
import com.sma.micro.planner.plannerutils.rest.rest_template.UserRestBuilder;
import com.sma.micro.planner.plannerutils.rest.webclient.UserWebClientBuilder;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;


@Slf4j
@AutoConfiguration
@ConditionalOnClass(UtilsProperties.class)
@EnableConfigurationProperties(UtilsProperties.class)
public class UtilsAutoConfiguration {

    @PostConstruct
    void init() {
        log.info("UtilsAutoConfiguration initialized");
    }

    @Bean
    @ConditionalOnMissingBean
    public JwtConverter jwtConverter() {
        return new JwtConverter();
    }

    @Bean
    @ConditionalOnMissingBean
    public LoggingAspect loggingAspect() {
        return new LoggingAspect();
    }

    @Bean
    @ConditionalOnMissingBean
    public UserRestBuilder userRestBuilder() {
        return new UserRestBuilder();
    }

    @Bean
    @ConditionalOnMissingBean
    public UserWebClientBuilder userWebClientBuilder() {
        return new UserWebClientBuilder();
    }

}
