package com.sma.micro.planner.todo.integration.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sma.micro.planner.todo.application.use_case.category.find_by_title.dto.FindByTitleCategoriesRq;
import com.sma.micro.planner.todo.dto.PriorityDto;
import com.sma.micro.planner.todo.integration.IntegrationTestBase;
import com.sma.micro.planner.todo.repository.PriorityRepository;
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

class PriorityControllerIT extends IntegrationTestBase {
    private static final TypeReference<List<PriorityDto>> listType = new TypeReference<>() {
    };
    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PriorityRepository repository;


    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    void findAll_shouldFailWith401() throws Exception {
        mvc.perform(get("/priority/all").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(value = USER_ID)
    void findAll_shouldFailWith403() throws Exception {
        mvc.perform(get("/priority/all").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = USER_ID, roles = {"user"})
    void findAll_shouldSucceedWith200() throws Exception {
        var result = mvc.perform(get("/priority/all").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
        var contentAsString = result.getResponse().getContentAsString();

        var tasks = objectMapper.readValue(contentAsString, listType);
        assertThat(tasks).hasSize(3)
                .containsExactlyInAnyOrder(PRIORITY_DTO_100, PRIORITY_DTO_101, PRIORITY_DTO_102);
    }

    @Test
    void add_shouldFailWith401() throws Exception {
        mvc.perform(post("/priority/add").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(value = USER_ID)
    void add_shouldFailWith403() throws Exception {
        mvc.perform(post("/priority/add").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = USER_ID, roles = {"user"})
    void add_shouldSucceedWith200() throws Exception {
        var priority = new PriorityDto(null, "New", COLOR);
        var result = mvc.perform(
                        post("/priority/add")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(priority)))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
        var contentAsString = result.getResponse().getContentAsString();

        var addedPriority = objectMapper.readValue(contentAsString, PriorityDto.class);
        assertThat(addedPriority).usingRecursiveComparison().ignoringFields("id").isEqualTo(priority);
    }

    @Test
    @WithMockUser(value = USER_ID, roles = {"user"})
    void add_notEmptyId_shouldSucceedWith400() throws Exception {
        var priority = new PriorityDto(5555L, "New", COLOR);
        mvc.perform(post("/priority/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(priority)))
                .andDo(print())
                .andExpectAll(
                        status().isBadRequest(),
                        content().string("Priority id='5555' must be null"));
    }

    @Test
    @WithMockUser(value = USER_ID, roles = {"user"})
    void add_emptyTitle_shouldSucceedWith400() throws Exception {
        var priority = new PriorityDto(null, "", COLOR);
        mvc.perform(post("/priority/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(priority)))
                .andDo(print())
                .andExpectAll(
                        status().isBadRequest(),
                        content().string("Field 'title' can't be blank"));
    }

    @Test
    void update_shouldFailWith401() throws Exception {
        mvc.perform(put("/priority/update").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(value = USER_ID)
    void update_shouldFailWith403() throws Exception {
        mvc.perform(put("/priority/update").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = USER_ID, roles = {"user"})
    void update_shouldSucceedWith200() throws Exception {
        var priority = new PriorityDto(100L, "New", "#FBBABA");
        mvc.perform(put("/priority/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(priority)))
                .andDo(print())
                .andExpect(status().isOk());
        assertThat(repository.findById(100L).orElseThrow().getTitle())
                .isEqualTo("New");
    }

    @Test
    @WithMockUser(value = USER_ID, roles = {"user"})
    void update_emptyId_shouldSucceedWith400() throws Exception {
        var priority = new PriorityDto(null, "New", "#FBBABA");
        mvc.perform(put("/priority/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(priority)))
                .andExpectAll(
                        status().isBadRequest(),
                        content().string("Priority id can't be empty"));
    }

    @Test
    @WithMockUser(value = USER_ID, roles = {"user"})
    void update_emptyTitle_shouldSucceedWith400() throws Exception {
        var priority = new PriorityDto(100L, null, "#FBBABA");
        mvc.perform(put("/priority/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(priority)))
                .andDo(print())
                .andExpectAll(
                        status().isBadRequest(),
                        content().string("Field 'title' can't be blank"));
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
        assertThat(repository.findById(100L)).isNotPresent();
    }

    @Test
    void search_shouldFailWith401() throws Exception {
        mvc.perform(post("/priority/search").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(value = USER_ID)
    void search_shouldFailWith403() throws Exception {
        mvc.perform(post("/priority/search").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = USER_ID, roles = {"user"})
    void search_shouldSucceedWith200() throws Exception {
        var searchValues = new FindByTitleCategoriesRq("i");
        var result = mvc.perform(post("/priority/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(searchValues)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        var contentAsString = result.getResponse().getContentAsString();

        var priorities = objectMapper.readValue(contentAsString, listType);
        assertThat(priorities).hasSize(2)
                .containsExactlyInAnyOrder(
                        PRIORITY_DTO_100,
                        PRIORITY_DTO_101);
    }

    @Test
    void findById_shouldFailWith401() throws Exception {
        mvc.perform(get("/priority/id").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(value = USER_ID)
    void findById_shouldFailWith403() throws Exception {
        mvc.perform(get("/priority/id").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = USER_ID, roles = {"user"})
    void findById_shouldSucceedWith200() throws Exception {
        var result = mvc.perform(get("/priority/id/100")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();

        var contentAsString = result.getResponse().getContentAsString();

        var addedPriority = objectMapper.readValue(contentAsString, PriorityDto.class);
        assertThat(addedPriority).usingRecursiveComparison().isEqualTo(PRIORITY_DTO_100);
    }

    @Test
    @WithMockUser(value = USER_ID, roles = {"user"})
    void findById_shouldSucceedWith404() throws Exception {
        mvc.perform(get("/priority/id/5555")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpectAll(status().isNotFound(),
                        content().string("No value present"));
    }

}
