package com.sma.micro.planner.todo.integration.controller.secured_method;

import com.sma.micro.planner.todo.controller.TaskController;
import com.sma.micro.planner.todo.dto.TaskDto;
import com.sma.micro.planner.todo.exception.AuthenticationException;
import com.sma.micro.planner.todo.integration.IntegrationTestBase;
import com.sma.micro.planner.todo.model.search.TaskSearchValues;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;

import static com.sma.micro.planner.todo.model.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.http.HttpStatus.OK;

class SmTaskControllerIT extends IntegrationTestBase {
    @Autowired
    private TaskController controller;

    @Test
    void all_givenUnauthenticated_thenThrowsException() {
        var exception = assertThrows(AuthenticationException.class,
                () -> controller.findAll());
        assertThat(exception).hasMessageContaining("unauthenticated");
    }

    @Test
    @WithMockUser(username = USER_ID)
    void all_givenAuthenticated_thenOk() {
        var response = controller.findAll();

        assertThat(response.getStatusCode()).isEqualTo(OK);
    }

    @Test
    void add_givenUnauthenticated_thenThrowsException() {
        var exception = assertThrows(AuthenticationException.class,
                () -> controller.add(TASK_DTO));
        assertThat(exception).hasMessageContaining("unauthenticated");
    }

    @Test
    @WithMockUser(username = USER_ID)
    void add_givenAuthenticated_thenOk() {
        var taskDto = new TaskDto(null, TITLE, false, null, null, null);
        var response = controller.add(taskDto);

        assertThat(response.getStatusCode()).isEqualTo(OK);
    }

    @Test
    void update_givenUnauthenticated_thenThrowsException() {
        var exception = assertThrows(AuthenticationException.class,
                () -> controller.update(TASK_DTO));
        assertThat(exception).hasMessageContaining("unauthenticated");
    }

    @Test
    @WithMockUser(username = USER_ID)
    void update_givenAuthenticated_thenOk() {
        var task = new TaskDto(102L, "Read", true, null, null, null);
        var response = controller.update(task);

        assertThat(response.getStatusCode()).isEqualTo(OK);
    }

    @Test
    void delete_givenUnauthenticated_thenThrowsException() {
        var exception = assertThrows(AuthenticationException.class,
                () -> controller.delete(ID));
        assertThat(exception).hasMessageContaining("unauthenticated");
    }

    @Test
    @WithMockUser(username = USER_ID)
    void delete_givenAuthenticated_thenOk() {
        var response = controller.delete(102L);

        assertThat(response.getStatusCode()).isEqualTo(OK);
    }

    @Test
    void search_givenUnauthenticated_thenThrowsException() {
        var params = new TaskSearchValues("ea", null, null, null, null, null, 0, 5, "title", "asc");
        var exception = assertThrows(AuthenticationException.class,
                () -> controller.search(params));
        assertThat(exception).hasMessageContaining("unauthenticated");
    }

    @Test
    @WithMockUser(username = USER_ID)
    void search_givenAuthenticated_thenOk() {
        var params = new TaskSearchValues("ea", null, null, null, null, null, 0, 5, "title", "asc");
        var response = controller.search(params);

        assertThat(response.getStatusCode()).isEqualTo(OK);
    }

    @Test
    void findById_givenUnauthenticated_thenThrowsException() {
        var exception = assertThrows(AuthenticationException.class,
                () -> controller.findById(ID));
        assertThat(exception).hasMessageContaining("unauthenticated");
    }

    @Test
    @WithMockUser(username = USER_ID)
    void findById_givenAuthenticated_thenOk() {
        var response = controller.findById(100L);

        assertThat(response.getStatusCode()).isEqualTo(OK);
    }
}