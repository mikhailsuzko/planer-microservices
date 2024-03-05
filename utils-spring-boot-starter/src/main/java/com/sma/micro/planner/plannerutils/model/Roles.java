package com.sma.micro.planner.plannerutils.model;

public enum Roles {
    USER, ADMIN;

    public String getName() {
        return name().toLowerCase();
    }
}
