package com.sma.micro.planner.todo.integration.controller.secured;

import com.sma.micro.planner.todo.application.use_case.category.find_by_title.dto.FindByTitleCategoriesRq;
import com.sma.micro.planner.todo.controller.PriorityController;
import com.sma.micro.planner.todo.dto.PriorityDto;
import com.sma.micro.planner.todo.exception.AuthenticationException;
import com.sma.micro.planner.todo.integration.IntegrationTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;

import static com.sma.micro.planner.todo.model.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.http.HttpStatus.OK;

class SMPriorityControllerIT extends IntegrationTestBase {
    @Autowired
    private PriorityController controller;

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
                () -> controller.add(PRIORITY_DTO));
        assertThat(exception).hasMessageContaining("unauthenticated");
    }

    @Test
    @WithMockUser(username = USER_ID)
    void add_givenAuthenticated_thenOk() {
        var priority = new PriorityDto(null, TITLE, COLOR);
        var response = controller.add(priority);

        assertThat(response.getStatusCode()).isEqualTo(OK);
    }

    @Test
    void update_givenUnauthenticated_thenThrowsException() {
        var exception = assertThrows(AuthenticationException.class,
                () -> controller.update(PRIORITY_DTO));
        assertThat(exception).hasMessageContaining("unauthenticated");
    }

    @Test
    @WithMockUser(username = USER_ID)
    void update_givenAuthenticated_thenOk() {
        var priority = new PriorityDto(102L, "Low", COLOR);
        var response = controller.update(priority);

        assertThat(response.getStatusCode()).isEqualTo(OK);
    }

    @Test
    void delete_givenUnauthenticated_thenThrowsException() {
        var exception = assertThrows(AuthenticationException.class,
                () -> controller.delete(ID_10));
        assertThat(exception).hasMessageContaining("unauthenticated");
    }

    @Test
    @WithMockUser(username = USER_ID)
    void delete_givenAuthenticated_thenOk() {
        var response = controller.delete(101L);

        assertThat(response.getStatusCode()).isEqualTo(OK);
    }

    @Test
    void search_givenUnauthenticated_thenThrowsException() {
        var params = new FindByTitleCategoriesRq(TITLE);
        var exception = assertThrows(AuthenticationException.class,
                () -> controller.search(params));
        assertThat(exception).hasMessageContaining("unauthenticated");
    }

    @Test
    @WithMockUser(username = USER_ID)
    void search_givenAuthenticated_thenOk() {
        var params = new FindByTitleCategoriesRq("o");
        var response = controller.search(params);

        assertThat(response.getStatusCode()).isEqualTo(OK);
    }

    @Test
    void findById_givenUnauthenticated_thenThrowsException() {
        var exception = assertThrows(AuthenticationException.class,
                () -> controller.findById(ID_10));
        assertThat(exception).hasMessageContaining("unauthenticated");
    }

    @Test
    @WithMockUser(username = USER_ID)
    void findById_givenAuthenticated_thenOk() {
        var response = controller.findById(100L);

        assertThat(response.getStatusCode()).isEqualTo(OK);
    }
}