package com.sma.micro.planner.todo.common.model;

import lombok.Data;

import java.util.List;

@Data
public class Page<T> {
    private List<T> content;
}
