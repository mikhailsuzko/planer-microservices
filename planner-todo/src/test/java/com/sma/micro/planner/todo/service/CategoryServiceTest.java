package com.sma.micro.planner.todo.service;

import com.sma.micro.planner.todo.repository.CategoryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.NoSuchElementException;

import static com.sma.micro.planner.todo.model.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = CategoryService.class)
class CategoryServiceTest {
    @Autowired
    private CategoryService service;
    @MockBean
    private CategoryRepository repository;

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(repository);
    }

    @Test
    void findById() {
        when(repository.findByIdAndUserId(ID, USER_ID)).thenReturn(CATEGORY_DTO);

        var result = service.findById(ID, USER_ID);

        verify(repository).findByIdAndUserId(ID, USER_ID);
        assertThat(result).isEqualTo(CATEGORY_DTO);
    }

    @Test
    void findById_exception() {
        when(repository.findByIdAndUserId(ID, USER_ID)).thenThrow(NoSuchElementException.class);

        assertThrows(NoSuchElementException.class, () -> service.findById(ID, USER_ID));

        verify(repository).findByIdAndUserId(ID, USER_ID);
    }

    @Test
    void findAll() {
        when(repository.findByUserIdOrderByTitle(USER_ID)).thenReturn(CATEGORIES_DTO);

        var result = service.findAll(USER_ID);

        verify(repository).findByUserIdOrderByTitle(USER_ID);
        assertThat(result).isEqualTo(CATEGORIES_DTO);
    }

    @Test
    void add() {
        when(repository.save(CATEGORY_DTO, USER_ID)).thenReturn(CATEGORY_DTO);

        var result = service.add(CATEGORY_DTO, USER_ID);

        verify(repository).save(CATEGORY_DTO, USER_ID);
        assertThat(result).isEqualTo(CATEGORY_DTO);
    }

    @Test
    void update() {
        when(repository.save(CATEGORY_DTO, USER_ID)).thenReturn(CATEGORY_DTO);

        service.update(CATEGORY_DTO, USER_ID);

        verify(repository).save(CATEGORY_DTO, USER_ID);
    }

    @Test
    void deleteById() {

        service.deleteById(ID, USER_ID);

        verify(repository).deleteByIdAndUserId(ID, USER_ID);
    }

    @Test
    void findByTitle() {
        var sqlTitle = "%" + TITLE + "%";
        when(repository.findByTitle(sqlTitle, USER_ID)).thenReturn(CATEGORIES_DTO);

        var result = service.findByTitle(TITLE, USER_ID);

        verify(repository).findByTitle(sqlTitle, USER_ID);
        assertThat(result).isEqualTo(CATEGORIES_DTO);
    }

    @Test
    void addAll() {
        service.addAll(CATEGORIES_DTO, USER_ID);

        verify(repository).saveAll(CATEGORIES_DTO, USER_ID);
    }
}