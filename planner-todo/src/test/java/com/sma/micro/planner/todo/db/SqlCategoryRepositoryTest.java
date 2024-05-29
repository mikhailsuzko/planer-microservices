package com.sma.micro.planner.todo.db;

import com.sma.micro.planner.todo.db.jpa.CategoryJpaRepository;
import com.sma.micro.planner.todo.db.mapper.CategoryMapper;
import com.sma.micro.planner.todo.repository.CategoryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static com.sma.micro.planner.todo.model.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@SpringBootTest(classes = SqlCategoryRepository.class)
class SqlCategoryRepositoryTest {
    @Autowired
    private CategoryRepository repository;
    @MockBean
    private CategoryJpaRepository jpaRepository;

    @MockBean
    private CategoryMapper mapper;

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(jpaRepository, mapper);
    }


    @Test
    void findByUserIdOrderByTitle() {
        when(jpaRepository.findByUserIdOrderByTitle(USER_ID)).thenReturn(CATEGORIES);
        when(mapper.toDomain(CATEGORY)).thenReturn(CATEGORY_DTO);

        var result = repository.findByUserIdOrderByTitle(USER_ID);

        verify(jpaRepository).findByUserIdOrderByTitle(USER_ID);
        verify(mapper).toDomain(CATEGORY);
        assertThat(result).isEqualTo(CATEGORIES_DTO);
    }

    @Test
    void findByTitle() {
        var sqlTitle = TITLE;
        when(jpaRepository.findByTitle(sqlTitle, USER_ID)).thenReturn(CATEGORIES);
        when(mapper.toDomain(CATEGORY)).thenReturn(CATEGORY_DTO);

        var result = repository.findByTitle(TITLE, USER_ID);

        verify(jpaRepository).findByTitle(sqlTitle, USER_ID);
        verify(mapper).toDomain(CATEGORY);
        assertThat(result).isEqualTo(CATEGORIES_DTO);
    }

    @Test
    void deleteByIdAndUserId() {
        repository.deleteByIdAndUserId(ID, USER_ID);

        verify(jpaRepository).deleteByIdAndUserId(ID, USER_ID);
    }

    @Test
    void findByIdAndUserId() {
        when(jpaRepository.findByIdAndUserId(ID, USER_ID)).thenReturn(Optional.of(CATEGORY));
        when(mapper.toDomain(CATEGORY)).thenReturn(CATEGORY_DTO);

        var result = repository.findByIdAndUserId(ID, USER_ID);

        verify(jpaRepository).findByIdAndUserId(ID, USER_ID);
        verify(mapper).toDomain(CATEGORY);
        assertThat(result).isEqualTo(CATEGORY_DTO);
    }

    @Test
    void save() {
        when(mapper.toEntity(CATEGORY_DTO, USER_ID)).thenReturn(CATEGORY);
        when(jpaRepository.save(CATEGORY)).thenReturn(CATEGORY);
        when(mapper.toDomain(CATEGORY)).thenReturn(CATEGORY_DTO);

        var result = repository.save(CATEGORY_DTO, USER_ID);

        verify(mapper).toEntity(CATEGORY_DTO, USER_ID);
        verify(jpaRepository).save(CATEGORY);
        verify(mapper).toDomain(CATEGORY);
        assertThat(result).isEqualTo(CATEGORY_DTO);
    }

    @Test
    void saveAll() {
        when(mapper.toEntity(CATEGORY_DTO, USER_ID)).thenReturn(CATEGORY);

        repository.saveAll(CATEGORIES_DTO, USER_ID);

        verify(mapper).toEntity(CATEGORY_DTO, USER_ID);
        verify(jpaRepository).saveAll(CATEGORIES);
    }
}