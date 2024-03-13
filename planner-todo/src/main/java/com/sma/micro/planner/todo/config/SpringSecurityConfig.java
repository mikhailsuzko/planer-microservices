package com.sma.micro.planner.todo.config;

import com.sma.micro.planner.plannerutils.converter.JwtConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SpringSecurityConfig {
    @Value("${client.url}")
    public String clientUrl;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtConverter jwtConverter,
                                           AuthenticationEntryPoint authEntryPoint) throws Exception {
        var converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwtConverter);

        http.authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/category/*", "/priority/*", "/task/*").hasRole("user")
                        .anyRequest().authenticated())

                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())

                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(converter))
                        .authenticationEntryPoint(authEntryPoint)
                )
                //.requiresChannel(rc -> rc.anyRequest().requiresSecure())
                .sessionManagement(sm -> sm.sessionCreationPolicy(STATELESS));

        return http.build();
    }

}
