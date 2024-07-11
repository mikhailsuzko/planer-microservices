package com.sma.micro.planner.todo.application.use_case.stat.dto;

import com.sma.micro.planner.todo.domain.entity.Stat;

public record StatPublicData(Long completedTotal,
                             Long uncompletedTotal) {
    public StatPublicData(Stat stat) {
        this(
                stat.getCompletedTotal(),
                stat.getUncompletedTotal());
    }
}
