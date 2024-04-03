package com.sma.micro.planner.todo.service.mapper;

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
    void dtoToCategory() {
        var result = mapper.dtoToCategory(CATEGORY_DTO, USER_ID);

        assertThat(result).isEqualTo(CATEGORY);
    }

    @Test
    void categoryToDto() {
        var result = mapper.categoryToDto(CATEGORY);

        assertThat(result).isEqualTo(CATEGORY_DTO);
    }
}