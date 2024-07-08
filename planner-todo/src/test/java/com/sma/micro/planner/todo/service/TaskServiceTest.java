package com.sma.micro.planner.todo.service;

import com.sma.micro.planner.todo.repository.TaskRepository;
import com.sma.micro.planner.todo.service.mapper.TaskMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.NoSuchElementException;
import java.util.Optional;

import static com.sma.micro.planner.todo.model.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = TaskService.class)
class TaskServiceTest {
    @Autowired
    private TaskService service;
    @MockBean
    private TaskRepository repository;
    @MockBean
    private TaskMapper mapper;

    @Test
    void findById() {
        when(repository.findByIdAndUserId(ID_10, USER_ID)).thenReturn(Optional.of(TASK));
        when(mapper.taskToDto(TASK)).thenReturn(TASK_DTO);

        var result = service.findById(ID_10, USER_ID);

        verify(repository).findByIdAndUserId(ID_10, USER_ID);
        verify(mapper).taskToDto(TASK);
        assertThat(result).isEqualTo(TASK_DTO);
    }

    @Test
    void findById_exception() {
        when(repository.findByIdAndUserId(ID_10, USER_ID)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> service.findById(ID_10, USER_ID));

        verify(repository).findByIdAndUserId(ID_10, USER_ID);
    }

    @Test
    void findAll() {
        when(repository.findByUserIdOrderByTaskDateDescTitleAsc(USER_ID)).thenReturn(TASKS);
        when(mapper.taskToDto(TASK)).thenReturn(TASK_DTO);

        var result = service.findAll(USER_ID);

        verify(repository).findByUserIdOrderByTaskDateDescTitleAsc(USER_ID);
        verify(mapper).taskToDto(TASK);
        assertThat(result).isEqualTo(TASKS_DTO);
    }

    @Test
    void add() {
        when(mapper.dtoToTask(TASK_DTO, USER_ID)).thenReturn(TASK);
        when(repository.save(TASK)).thenReturn(TASK);
        when(mapper.taskToDto(TASK)).thenReturn(TASK_DTO);

        var result = service.add(TASK_DTO, USER_ID);

        verify(mapper).dtoToTask(TASK_DTO, USER_ID);
        verify(repository).save(TASK);
        verify(mapper).taskToDto(TASK);
        assertThat(result).isEqualTo(TASK_DTO);
    }

    @Test
    void update() {
        when(mapper.dtoToTask(TASK_DTO, USER_ID)).thenReturn(TASK);
        when(repository.save(TASK)).thenReturn(TASK);

        service.update(TASK_DTO, USER_ID);

        verify(mapper).dtoToTask(TASK_DTO, USER_ID);
        verify(repository).save(TASK);
    }

    @Test
    void deleteById() {

        service.deleteById(ID_10, USER_ID);

        verify(repository).deleteByIdAndUserId(ID_10, USER_ID);
    }

    @Test
    void findByParams() {
        var sqlTitle = "%" + TITLE + "%";
        var sort = Sort.by("ASC", "TITLE");
        var request = PageRequest.of(0, 5, sort);
        var pages = new PageImpl<>(TASKS, request, 10);
        var pagesExpected = new PageImpl<>(TASKS_DTO, request, 10);
        when(mapper.taskToDto(TASK)).thenReturn(TASK_DTO);
        when(repository.findByParams(
                sqlTitle, false, ID_10, ID_10, null, null, USER_ID, request))
                .thenReturn(pages);

        var result = service.findByParams(TITLE, false, ID_10, ID_10,
                null, null, USER_ID, request);

        verify(mapper).taskToDto(TASK);
        verify(repository).findByParams(
                sqlTitle, false, ID_10, ID_10, null, null, USER_ID, request);
        assertThat(result).isEqualTo(pagesExpected);
    }

    @Test
    void addAll() {
        service.addAll(TASKS);

        verify(repository).saveAll(TASKS);
    }
}