package com.sma.micro.planner.todo.service.mapper;

import com.sma.micro.planner.todo.db.mapper.CategoryMapper;
import com.sma.micro.planner.todo.db.mapper.CategoryMapperImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.sma.micro.planner.todo.model.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = CategoryMapperImpl.class)
class CategoryMapperTest {
    @Autowired
    private CategoryMapper mapper;

    @Test
    void toEntity() {
        var result = mapper.toEntity(CATEGORY_DTO, USER_ID);

        assertThat(result).isEqualTo(CATEGORY);
    }

    @Test
    void toDomain() {
        var result = mapper.toDomain(CATEGORY);

        assertThat(result).isEqualTo(CATEGORY_DTO);
    }
}