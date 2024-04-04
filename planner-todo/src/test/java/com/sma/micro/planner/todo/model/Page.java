package com.sma.micro.planner.todo.model;

import lombok.Data;

import java.util.List;

@Data
public class Page<T> {
    private List<T> content;
}
