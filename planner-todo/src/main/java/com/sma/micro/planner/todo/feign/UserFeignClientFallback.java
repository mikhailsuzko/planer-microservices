package com.sma.micro.planner.todo.feign;

import com.sma.micro.planner.plannerentity.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class UserFeignClientFallback implements UserFeignClient{
    @Override
    public ResponseEntity<User> findUserById(Long id) {
        return ResponseEntity.ok(null);
    }
}
