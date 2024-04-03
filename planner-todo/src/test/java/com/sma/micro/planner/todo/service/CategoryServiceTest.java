package com.sma.micro.planner.todo.service;

import com.sma.micro.planner.todo.repository.CategoryRepository;
import com.sma.micro.planner.todo.service.mapper.CategoryMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.NoSuchElementException;
import java.util.Optional;

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
    @MockBean
    private CategoryMapper mapper;

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(repository, mapper);
    }

    @Test
    void findById() {
        when(repository.findById(ID)).thenReturn(Optional.of(CATEGORY));
        when(mapper.categoryToDto(CATEGORY)).thenReturn(CATEGORY_DTO);

        var result = service.findById(ID);

        verify(repository).findById(ID);
        verify(mapper).categoryToDto(CATEGORY);
        assertThat(result).isEqualTo(CATEGORY_DTO);
    }

    @Test
    void findById_exception() {
        when(repository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> service.findById(ID));

        verify(repository).findById(ID);
    }

    @Test
    void findAll() {
        when(repository.findByUserIdOrderByTitle(USER_ID)).thenReturn(CATEGORIES);
        when(mapper.categoryToDto(CATEGORY)).thenReturn(CATEGORY_DTO);

        var result = service.findAll(USER_ID);

        verify(repository).findByUserIdOrderByTitle(USER_ID);
        verify(mapper).categoryToDto(CATEGORY);
        assertThat(result).isEqualTo(CATEGORIES_DTO);
    }

    @Test
    void add() {
        when(mapper.dtoToCategory(CATEGORY_DTO, USER_ID)).thenReturn(CATEGORY);
        when(repository.save(CATEGORY)).thenReturn(CATEGORY);
        when(mapper.categoryToDto(CATEGORY)).thenReturn(CATEGORY_DTO);

        var result = service.add(CATEGORY_DTO, USER_ID);

        verify(mapper).dtoToCategory(CATEGORY_DTO, USER_ID);
        verify(repository).save(CATEGORY);
        verify(mapper).categoryToDto(CATEGORY);
        assertThat(result).isEqualTo(CATEGORY_DTO);
    }

    @Test
    void update() {
        when(mapper.dtoToCategory(CATEGORY_DTO, USER_ID)).thenReturn(CATEGORY);
        when(repository.save(CATEGORY)).thenReturn(CATEGORY);

        service.update(CATEGORY_DTO, USER_ID);

        verify(mapper).dtoToCategory(CATEGORY_DTO, USER_ID);
        verify(repository).save(CATEGORY);
    }

    @Test
    void deleteById() {

        service.deleteById(ID);

        verify(repository).deleteById(ID);
    }

    @Test
    void findByTitle() {
        var sqlTitle = "%" + TITLE + "%";
        when(repository.findByTitle(sqlTitle, USER_ID)).thenReturn(CATEGORIES);
        when(mapper.categoryToDto(CATEGORY)).thenReturn(CATEGORY_DTO);

        var result = service.findByTitle(TITLE, USER_ID);

        verify(repository).findByTitle(sqlTitle, USER_ID);
        verify(mapper).categoryToDto(CATEGORY);
        assertThat(result).isEqualTo(CATEGORIES_DTO);
    }

    @Test
    void addAll() {
        service.addAll(CATEGORIES);

        verify(repository).saveAll(CATEGORIES);
    }
}