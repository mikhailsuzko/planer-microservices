package com.sma.micro.planner.plannerusers.dto;

import lombok.Value;

@Value
public class UserDto {
    String id;
    String email;
    String username;
    String password;
}
