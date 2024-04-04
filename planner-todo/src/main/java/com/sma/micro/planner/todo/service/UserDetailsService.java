package com.sma.micro.planner.todo.service;

import com.sma.micro.planner.todo.exception.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsService {

    public String getUserId() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new AuthenticationException("unauthenticated");
        }
        return authentication.getName();
    }
}
