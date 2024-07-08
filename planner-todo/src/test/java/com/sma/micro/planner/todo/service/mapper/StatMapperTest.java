package com.sma.micro.planner.todo.service.mapper;

import com.sma.micro.planner.todo.domain.entity.Stat;
import com.sma.micro.planner.todo.dto.StatDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.sma.micro.planner.todo.model.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = StatMapperImpl.class)
class StatMapperTest {
    @Autowired
    private StatMapper mapper;

    @Test
    void statToDto() {
        var result = mapper.statToDto(new Stat(ID_10, COUNT, COUNT, USER_ID));

        assertThat(result).isEqualTo(new StatDto(ID_10, COUNT, COUNT));
    }
}