package com.sma.micro.planner.todo.integration.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sma.micro.planner.todo.dto.TaskDto;
import com.sma.micro.planner.todo.integration.IntegrationTestBase;
import com.sma.micro.planner.todo.model.Page;
import com.sma.micro.planner.todo.model.search.TaskSearchValues;
import com.sma.micro.planner.todo.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static com.sma.micro.planner.todo.model.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TaskControllerIT extends IntegrationTestBase {
    private static final TypeReference<List<TaskDto>> listType = new TypeReference<>() {
    };
    private static final TypeReference<Page<TaskDto>> pageType = new TypeReference<>() {
    };

    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private TaskRepository repository;


    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    void findAll_shouldFailWith401() throws Exception {
        mvc.perform(get("/task/all").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(value = USER_ID)
    void findAll_shouldFailWith403() throws Exception {
        mvc.perform(get("/task/all").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = USER_ID, roles = {"user"})
    void findAll_shouldSucceedWith200() throws Exception {
        var result = mvc.perform(get("/task/all").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
        var contentAsString = result.getResponse().getContentAsString();

        var tasks = objectMapper.readValue(contentAsString, listType);
        assertThat(tasks).hasSize(5).containsExactlyInAnyOrder(
                TASK_DTO_100, TASK_DTO_101, TASK_DTO_102,
                TASK_DTO_103, TASK_DTO_104);
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
        var task = new TaskDto(null, "New", false, null, null, null);
        var result = mvc.perform(
                        post("/task/add")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(task)))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
        var contentAsString = result.getResponse().getContentAsString();

        var addedTask = objectMapper.readValue(contentAsString, TaskDto.class);
        assertThat(addedTask).usingRecursiveComparison().ignoringFields("id").isEqualTo(task);
    }

    @Test
    @WithMockUser(value = USER_ID, roles = {"user"})
    void add_notEmptyId_shouldSucceedWith400() throws Exception {
        var task = new TaskDto(5555L, "New", false, null, null, null);
        mvc.perform(post("/task/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task)))
                .andDo(print())
                .andExpectAll(
                        status().isBadRequest(),
                        content().string("Task id='5555' must be null"));
    }

    @Test
    @WithMockUser(value = USER_ID, roles = {"user"})
    void add_emptyTitle_shouldSucceedWith400() throws Exception {
        var task = new TaskDto(null, "", false, null, null, null);
        mvc.perform(post("/task/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task)))
                .andDo(print())
                .andExpectAll(
                        status().isBadRequest(),
                        content().string("Field 'title' can't be blank"));
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
        var task = new TaskDto(100L, "New", false, null, CATEGORY_DTO_100, PRIORITY_DTO_100);
        mvc.perform(put("/task/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task)))
                .andDo(print())
                .andExpect(status().isOk());
        assertThat(repository.findById(100L).orElseThrow().getTitle())
                .isEqualTo("New");
    }

    @Test
    @WithMockUser(value = USER_ID, roles = {"user"})
    void update_emptyId_shouldSucceedWith400() throws Exception {
        var task = new TaskDto(null, "New", false, null, CATEGORY_DTO_100, PRIORITY_DTO_100);
        mvc.perform(put("/task/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task)))
                .andExpectAll(
                        status().isBadRequest(),
                        content().string("Task id can't be empty"));
    }

    @Test
    @WithMockUser(value = USER_ID, roles = {"user"})
    void update_emptyTitle_shouldSucceedWith400() throws Exception {
        var task = new TaskDto(100L, null, false, null, CATEGORY_DTO_100, PRIORITY_DTO_100);
        mvc.perform(put("/task/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task)))
                .andDo(print())
                .andExpectAll(
                        status().isBadRequest(),
                        content().string("Field 'title' can't be blank"));
    }

    @Test
    void delete_shouldFailWith401() throws Exception {
        mvc.perform(delete("/task/delete").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(value = USER_ID)
    void delete_shouldFailWith403() throws Exception {
        mvc.perform(delete("/task/delete").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = USER_ID, roles = {"user"})
    void delete_shouldSucceedWith200() throws Exception {
        mvc.perform(delete("/task/delete/100")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        assertThat(repository.findById(100L)).isNotPresent();
    }

    @Test
    void search_shouldFailWith401() throws Exception {
        mvc.perform(post("/task/search").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(value = USER_ID)
    void search_shouldFailWith403() throws Exception {
        mvc.perform(post("/task/search").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = USER_ID, roles = {"user"})
    void search_shouldSucceedWith200() throws Exception {
        var searchValues = new TaskSearchValues("i", null, null, null, null, null, 0, 5, "id", "asc");
        var result = mvc.perform(post("/task/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(searchValues)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        var contentAsString = result.getResponse().getContentAsString();

        var tasks = objectMapper.readValue(contentAsString, pageType);

        assertThat(tasks.getContent()).hasSize(2)
                .containsExactlyInAnyOrder(TASK_DTO_102, TASK_DTO_104);
    }

    @Test
    void findById_shouldFailWith401() throws Exception {
        mvc.perform(get("/task/id").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(value = USER_ID)
    void findById_shouldFailWith403() throws Exception {
        mvc.perform(get("/task/id").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = USER_ID, roles = {"user"})
    void findById_shouldSucceedWith200() throws Exception {
        var result = mvc.perform(get("/task/id/100")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();

        var contentAsString = result.getResponse().getContentAsString();

        var addedTask = objectMapper.readValue(contentAsString, TaskDto.class);
        assertThat(addedTask).usingRecursiveComparison().isEqualTo(TASK_DTO_100);
    }

    @Test
    @WithMockUser(value = USER_ID, roles = {"user"})
    void findById_shouldSucceedWith404() throws Exception {
        mvc.perform(get("/task/id/5555")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpectAll(status().isNotFound(),
                        content().string("No value present"));
    }
}
