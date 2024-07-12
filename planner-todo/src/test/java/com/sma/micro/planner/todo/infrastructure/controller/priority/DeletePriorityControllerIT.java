package com.sma.micro.planner.todo.infrastructure.controller.priority;

import com.sma.micro.planner.todo.common.IntegrationTestBase;
import com.sma.micro.planner.todo.infrastructure.repository.JpaPriorityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.NoSuchElementException;

import static com.sma.micro.planner.todo.common.model.Constants.USER_ID;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DeletePriorityControllerIT extends IntegrationTestBase {
    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;
    @Autowired
    private JpaPriorityRepository repository;


    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    void delete_shouldFailWith401() throws Exception {
        mvc.perform(delete("/priority/delete").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(value = USER_ID)
    void delete_shouldFailWith403() throws Exception {
        mvc.perform(delete("/priority/delete").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = USER_ID, roles = {"user"})
    void delete_shouldSucceedWith200() throws Exception {
        mvc.perform(delete("/priority/delete/100")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        assertThrows(NoSuchElementException.class, () -> repository.findById(100L, USER_ID));
    }
}
