package com.sma.micro.planner.todo.integration.controller.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sma.micro.planner.todo.application.use_case.task.dto.TaskPublicData;
import com.sma.micro.planner.todo.application.use_case.task.dto.TaskRegistrationData;
import com.sma.micro.planner.todo.integration.IntegrationTestBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.sma.micro.planner.todo.model.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CreateTaskControllerIT extends IntegrationTestBase {
    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    void add_shouldFailWith401() throws Exception {
        mvc.perform(post("/task/add").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(value = USER_ID)
    void add_shouldFailWith403() throws Exception {
        mvc.perform(post("/task/add").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = USER_ID, roles = {"user"})
    void add_shouldSucceedWith200() throws Exception {
        var result = mvc.perform(
                        post("/task/add")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(TASK_REGISTRATION_DATA)))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
        var contentAsString = result.getResponse().getContentAsString();

        var addedTask = objectMapper.readValue(contentAsString, TaskPublicData.class);
        assertThat(addedTask).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(TASK_PUBLIC_DATA_100);
    }

    @Test
    @WithMockUser(value = USER_ID, roles = {"user"})
    void add_emptyTitle_shouldSucceedWith400() throws Exception {
        var task = new TaskRegistrationData("", false, null, null, null);
        mvc.perform(post("/task/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task)))
                .andDo(print())
                .andExpectAll(
                        status().isBadRequest(),
                        content().string("title must not be blank"));
    }
}
