package com.sma.micro.planner.todo.infrastructure.controller.secured;

import com.sma.micro.planner.todo.common.IntegrationTestBase;
import com.sma.micro.planner.todo.infrastructure.controller.init.InitDataController;
import com.sma.micro.planner.todo.infrastructure.exception.AuthenticationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;

import static com.sma.micro.planner.todo.common.model.Constants.USER_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.http.HttpStatus.OK;

class SMInitDataControllerIT extends IntegrationTestBase {
    @Autowired
    private InitDataController controller;

    @Test
    void init_givenUnauthenticated_thenThrowsException() {
        var exception = assertThrows(AuthenticationException.class,
                () -> controller.init());
        assertThat(exception).hasMessageContaining("unauthenticated");
    }

    @Test
    @WithMockUser(username = USER_ID)
    void init_givenAuthenticated_thenOk() {
        var response = controller.init();

        assertThat(response.getStatusCode()).isEqualTo(OK);
    }
}