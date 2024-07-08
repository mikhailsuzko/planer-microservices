package com.sma.micro.planner.todo.application.use_case.category;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.sma.micro.planner.todo.model.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = CategoryMapper.class)
class CategoryMapperTest {
    @Autowired
    private CategoryMapper mapper;


    @Test
    void toCategoryPublicData() {
        var result = mapper.toCategoryPublicData(CATEGORY_100);

        assertThat(result).isEqualTo(CATEGORY_PUBLIC_DATA_100);
    }

    @Test
    void categoryPublicDataToCategory() {
        var result = mapper.toCategory(CATEGORY_PUBLIC_DATA_100, USER_ID);

        assertThat(result).isEqualTo(CATEGORY_100);
    }

    @Test
    void categoryRegistrationDataToCategory() {
        var result = mapper.toCategory(CATEGORY_REGISTRATION_DATA_100, USER_ID);

        assertThat(result).isEqualTo(CATEGORY);
    }

    @Test
    void categoryUpdateDataToCategory() {
        var result = mapper.toCategory(CATEGORY_UPDATE_DATA_100, USER_ID);

        assertThat(result).isEqualTo(CATEGORY_100);
    }
}
