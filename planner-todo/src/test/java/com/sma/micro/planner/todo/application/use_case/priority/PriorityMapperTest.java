package com.sma.micro.planner.todo.application.use_case.priority;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.sma.micro.planner.todo.common.model.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = PriorityMapper.class)
class PriorityMapperTest {
    @Autowired
    private PriorityMapper mapper;


    @Test
    void toCategoryPublicData() {
        var result = mapper.toPriorityPublicData(PRIORITY_100);

        assertThat(result).isEqualTo(PRIORITY_PUBLIC_DATA_100);
    }

    @Test
    void categoryPublicDataToCategory() {
        var result = mapper.toPriority(PRIORITY_PUBLIC_DATA_100, USER_ID);

        assertThat(result).isEqualTo(PRIORITY_100);
    }

    @Test
    void categoryRegistrationDataToCategory() {
        var result = mapper.toPriority(PRIORITY_REGISTRATION_DATA, USER_ID);

        assertThat(result).isEqualTo(PRIORITY);
    }

    @Test
    void categoryUpdateDataToCategory() {
        var result = mapper.toPriority(PRIORITY_UPDATE_DATA_100, USER_ID);

        assertThat(result).isEqualTo(PRIORITY_100);
    }
}
