package com.sma.micro.planner.todo.service;

import com.sma.micro.planner.todo.repository.PriorityRepository;
import com.sma.micro.planner.todo.service.mapper.PriorityMapper;
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

@SpringBootTest(classes = PriorityService.class)
class PriorityServiceTest {
    @Autowired
    private PriorityService service;
    @MockBean
    private PriorityRepository repository;
    @MockBean
    private PriorityMapper mapper;


    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(repository, mapper);
    }

    @Test
    void findById() {
        when(repository.findByIdAndUserId(ID_10, USER_ID)).thenReturn(Optional.of(PRIORITY));
        when(mapper.priorityToDto(PRIORITY)).thenReturn(PRIORITY_DTO);

        var result = service.findById(ID_10, USER_ID);

        verify(repository).findByIdAndUserId(ID_10, USER_ID);
        verify(mapper).priorityToDto(PRIORITY);
        assertThat(result).isEqualTo(PRIORITY_DTO);
    }

    @Test
    void findById_exception() {
        when(repository.findByIdAndUserId(ID_10, USER_ID)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> service.findById(ID_10, USER_ID));

        verify(repository).findByIdAndUserId(ID_10, USER_ID);
    }

    @Test
    void findAll() {
        when(repository.findByUserIdOrderByIdAsc(USER_ID)).thenReturn(PRIORITIES);
        when(mapper.priorityToDto(PRIORITY)).thenReturn(PRIORITY_DTO);

        var result = service.findAll(USER_ID);

        verify(repository).findByUserIdOrderByIdAsc(USER_ID);
        verify(mapper).priorityToDto(PRIORITY);
        assertThat(result).isEqualTo(PRIORITIES_DTO);
    }

    @Test
    void add() {
        when(mapper.dtoToPriority(PRIORITY_DTO, USER_ID)).thenReturn(PRIORITY);
        when(repository.save(PRIORITY)).thenReturn(PRIORITY);
        when(mapper.priorityToDto(PRIORITY)).thenReturn(PRIORITY_DTO);

        var result = service.add(PRIORITY_DTO, USER_ID);

        verify(mapper).dtoToPriority(PRIORITY_DTO, USER_ID);
        verify(repository).save(PRIORITY);
        verify(mapper).priorityToDto(PRIORITY);
        assertThat(result).isEqualTo(PRIORITY_DTO);
    }

    @Test
    void update() {
        when(mapper.dtoToPriority(PRIORITY_DTO, USER_ID)).thenReturn(PRIORITY);
        when(repository.save(PRIORITY)).thenReturn(PRIORITY);

        service.update(PRIORITY_DTO, USER_ID);

        verify(mapper).dtoToPriority(PRIORITY_DTO, USER_ID);
        verify(repository).save(PRIORITY);
    }

    @Test
    void deleteById() {

        service.deleteById(ID_10, USER_ID);

        verify(repository).deleteByIdAndUserId(ID_10, USER_ID);
    }

    @Test
    void findByTitle() {
        var sqlTitle = "%" + TITLE + "%";
        when(repository.findByTitle(sqlTitle, USER_ID)).thenReturn(PRIORITIES);
        when(mapper.priorityToDto(PRIORITY)).thenReturn(PRIORITY_DTO);

        var result = service.findByTitle(TITLE, USER_ID);

        verify(repository).findByTitle(sqlTitle, USER_ID);
        verify(mapper).priorityToDto(PRIORITY);
        assertThat(result).isEqualTo(PRIORITIES_DTO);
    }

    @Test
    void addAll() {

        service.addAll(PRIORITIES);

        verify(repository).saveAll(PRIORITIES);
    }
}