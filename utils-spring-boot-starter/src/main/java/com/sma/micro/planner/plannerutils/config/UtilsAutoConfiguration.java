package com.sma.micro.planner.plannerutils.config;

import com.sma.micro.planner.plannerutils.aop.LoggingAspect;
import com.sma.micro.planner.plannerutils.converter.JwtConverter;
import com.sma.micro.planner.plannerutils.handler.OAuth2ExceptionHandler;
import com.sma.micro.planner.plannerutils.rest.rest_template.UserRestBuilder;
import com.sma.micro.planner.plannerutils.rest.webclient.UserWebClientBuilder;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;


@Slf4j
@AutoConfiguration
@ConditionalOnClass(UtilsProperties.class)
@EnableConfigurationProperties(UtilsProperties.class)
public class UtilsAutoConfiguration {
    @Value("${client.url}")
    public String clientUrl;

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
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new OAuth2ExceptionHandler();
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

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        var configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(clientUrl));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowedMethods(List.of("*"));
        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public StrictHttpFirewall httpFirewall() {
        var firewall = new StrictHttpFirewall();
        firewall.setAllowedHeaderNames(header -> true);
        firewall.setAllowedHeaderValues(header -> true);
        firewall.setAllowedParameterNames(header -> true);
        return firewall;
    }
}
