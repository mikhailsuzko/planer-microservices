package com.sma.micro.planner.todo.integration.controller.secured_method;

import com.sma.micro.planner.todo.controller.StatController;
import com.sma.micro.planner.todo.exception.AuthenticationException;
import com.sma.micro.planner.todo.integration.IntegrationTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;

import static com.sma.micro.planner.todo.model.Constants.USER_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.http.HttpStatus.OK;

class SMStatControllerIT extends IntegrationTestBase {
    @Autowired
    private StatController controller;

    @Test
    void stat_givenUnauthenticated_thenThrowsException() {
        var exception = assertThrows(AuthenticationException.class,
                () -> controller.stat());
        assertThat(exception).hasMessageContaining("unauthenticated");
    }

    @Test
    @WithMockUser(username = USER_ID)
    void stat_givenAuthenticated_thenOk() {
        var response = controller.stat();

        assertThat(response.getStatusCode()).isEqualTo(OK);
    }
}