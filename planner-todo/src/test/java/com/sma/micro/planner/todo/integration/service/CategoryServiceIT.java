package com.sma.micro.planner.todo.integration.service;

import com.sma.micro.planner.plannerentity.entity.Category;
import com.sma.micro.planner.todo.dto.CategoryDto;
import com.sma.micro.planner.todo.integration.IntegrationTestBase;
import com.sma.micro.planner.todo.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static com.sma.micro.planner.todo.model.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


class CategoryServiceIT extends IntegrationTestBase {
    @Autowired
    private CategoryService service;

    @Test
    void findById() {
        var result = service.findById(100L, USER_ID);

        assertThat(result).isEqualTo(CATEGORY_DTO_100);
    }

    @Test
    void findById_exception() {
        assertThrows(NoSuchElementException.class, () -> service.findById(ID, USER_ID));
    }

    @Test
    void findAll() {
        var result = service.findAll(USER_ID);

        assertThat(result).hasSize(4)
                .containsExactlyInAnyOrder(CATEGORY_DTO_100, CATEGORY_DTO_101, CATEGORY_DTO_102, CATEGORY_DTO_103);
    }

    @Test
    void add() {
        var userId = UUID.randomUUID().toString();

        var result = service.add(CATEGORY_DTO, userId);

        assertThat(result).usingRecursiveComparison()
                .ignoringFields("id").isEqualTo(CATEGORY_DTO);
    }

    @Test
    void update() {
        var id = 102L;
        var category = new CategoryDto(id, "Travel", COUNT, COUNT);

        service.update(category, USER_ID);

        var result = service.findById(id, USER_ID);
        assertThat(result).isEqualTo(category);
    }

    @Test
    void deleteById() {
        var id = 102L;

        service.deleteById(id, USER_ID);

        assertThrows(NoSuchElementException.class, () -> service.findById(id, USER_ID));
    }

    @Test
    void findByTitle() {

        var result = service.findByTitle("om", USER_ID);

        assertThat(result).hasSize(1)
                .containsExactly(CATEGORY_DTO_101);
    }

    @Test
    void addAll() {
        var userId = UUID.randomUUID().toString();
        var cat1 = Category.builder().title("title1").userId(userId).build();
        var cat2 = Category.builder().title("title2").userId(userId).build();
        var cat3 = Category.builder().title("title3").userId(userId).build();

        service.addAll(List.of(cat1, cat2, cat3));

        var result = service.findAll(userId);
        assertThat(result).hasSize(3)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .containsExactlyInAnyOrder(
                        new CategoryDto(0L, "title1", COUNT, COUNT),
                        new CategoryDto(0L, "title2", COUNT, COUNT),
                        new CategoryDto(0L, "title3", COUNT, COUNT)
                );
    }
}