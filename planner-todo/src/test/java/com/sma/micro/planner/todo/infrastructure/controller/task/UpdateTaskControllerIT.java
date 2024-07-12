package com.sma.micro.planner.todo.infrastructure.controller.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sma.micro.planner.todo.application.use_case.task.dto.TaskUpdateData;
import com.sma.micro.planner.todo.common.IntegrationTestBase;
import com.sma.micro.planner.todo.domain.entity.Task;
import com.sma.micro.planner.todo.infrastructure.repository.JpaTaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.sma.micro.planner.todo.common.model.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UpdateTaskControllerIT extends IntegrationTestBase {
    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private JpaTaskRepository repository;


    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    void update_shouldFailWith401() throws Exception {
        mvc.perform(put("/task/update").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(value = USER_ID)
    void update_shouldFailWith403() throws Exception {
        mvc.perform(put("/task/update").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = USER_ID, roles = {"user"})
    void update_shouldSucceedWith200() throws Exception {
        var task = new TaskUpdateData(100L, "Hobby", false, null, CATEGORY_PUBLIC_DATA_101, PRIORITY_PUBLIC_DATA_102);
        mvc.perform(put("/task/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task)))
                .andDo(print())
                .andExpect(status().isOk());
        assertThat(repository.findById(100L, USER_ID))
                .isEqualTo(new Task(100L, "Hobby", false, null, CATEGORY_101, PRIORITY_102, USER_ID));
    }

    @Test
    @WithMockUser(value = USER_ID, roles = {"user"})
    void update_emptyId_shouldSucceedWith400() throws Exception {
        var task = new TaskUpdateData(null, "Hobby", false, null, CATEGORY_PUBLIC_DATA_101, PRIORITY_PUBLIC_DATA_102);
        mvc.perform(put("/task/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task)))
                .andExpectAll(
                        status().isBadRequest(),
                        content().string("id must not be null"));
    }

    @Test
    @WithMockUser(value = USER_ID, roles = {"user"})
    void update_emptyTitle_shouldSucceedWith400() throws Exception {
        var task = new TaskUpdateData(100L, "", false, null, CATEGORY_PUBLIC_DATA_101, PRIORITY_PUBLIC_DATA_102);
        mvc.perform(put("/task/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task)))
                .andDo(print())
                .andExpectAll(
                        status().isBadRequest(),
                        content().string("title must not be blank"));
    }
}
