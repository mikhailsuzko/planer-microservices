package com.sma.micro.planner.todo.service.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.sma.micro.planner.todo.model.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = PriorityMapperImpl.class)
class PriorityMapperTest {
    @Autowired
    private PriorityMapper mapper;

    @Test
    void dtoToPriority() {
        var result = mapper.dtoToPriority(PRIORITY_DTO, USER_ID);

        assertThat(result).isEqualTo(PRIORITY);
    }

    @Test
    void priorityToDto() {
        var result = mapper.priorityToDto(PRIORITY);

        assertThat(result).isEqualTo(PRIORITY_DTO);
    }
}