package com.sma.micro.planner.todo.integration.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sma.micro.planner.todo.db.jpa.CategoryJpaRepository;
import com.sma.micro.planner.todo.dto.CategoryDto;
import com.sma.micro.planner.todo.integration.IntegrationTestBase;
import com.sma.micro.planner.todo.model.search.SearchValues;
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

class CategoryControllerIT extends IntegrationTestBase {
    private static final TypeReference<List<CategoryDto>> listType = new TypeReference<>() {
    };
    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CategoryJpaRepository repository;


    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    void findAll_shouldFailWith401() throws Exception {
        mvc.perform(get("/category/all").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(value = USER_ID)
    void findAll_shouldFailWith403() throws Exception {
        mvc.perform(get("/category/all").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = USER_ID, roles = {"user"})
    void findAll_shouldSucceedWith200() throws Exception {
        var result = mvc.perform(get("/category/all").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
        var contentAsString = result.getResponse().getContentAsString();

        var categories = objectMapper.readValue(contentAsString, listType);
        assertThat(categories).hasSize(4)
                .containsExactlyInAnyOrder(CATEGORY_DTO_100, CATEGORY_DTO_101, CATEGORY_DTO_102, CATEGORY_DTO_103);
    }

    @Test
    void add_shouldFailWith401() throws Exception {
        mvc.perform(post("/category/add").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(value = USER_ID)
    void add_shouldFailWith403() throws Exception {
        mvc.perform(post("/category/add").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = USER_ID, roles = {"user"})
    void add_shouldSucceedWith200() throws Exception {
        var category = new CategoryDto(null, "Hobby", 0L, 0L);
        var result = mvc.perform(
                        post("/category/add")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(category)))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
        var contentAsString = result.getResponse().getContentAsString();

        var addedCategory = objectMapper.readValue(contentAsString, CategoryDto.class);
        assertThat(addedCategory).usingRecursiveComparison().ignoringFields("id").isEqualTo(category);
    }

    @Test
    @WithMockUser(value = USER_ID, roles = {"user"})
    void add_notEmptyId_shouldSucceedWith400() throws Exception {
        var category = new CategoryDto(5555L, "Hobby", 0L, 0L);
        mvc.perform(post("/category/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(category)))
                .andDo(print())
                .andExpectAll(
                        status().isBadRequest(),
                        content().string("Category id='5555' must be null"));
    }

    @Test
    @WithMockUser(value = USER_ID, roles = {"user"})
    void add_emptyTitle_shouldSucceedWith400() throws Exception {
        var category = new CategoryDto(null, "", 0L, 0L);
        mvc.perform(post("/category/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(category)))
                .andDo(print())
                .andExpectAll(
                        status().isBadRequest(),
                        content().string("Field 'title' can't be blank"));
    }

    @Test
    void update_shouldFailWith401() throws Exception {
        mvc.perform(put("/category/update").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(value = USER_ID)
    void update_shouldFailWith403() throws Exception {
        mvc.perform(put("/category/update").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = USER_ID, roles = {"user"})
    void update_shouldSucceedWith200() throws Exception {
        var category = new CategoryDto(100L, "Hobby", 0L, 0L);
        mvc.perform(put("/category/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(category)))
                .andDo(print())
                .andExpect(status().isOk());
        assertThat(repository.findById(100L).orElseThrow().getTitle())
                .isEqualTo("Hobby");
    }

    @Test
    @WithMockUser(value = USER_ID, roles = {"user"})
    void update_emptyId_shouldSucceedWith400() throws Exception {
        var category = new CategoryDto(null, "Hobby", 0L, 0L);
        mvc.perform(put("/category/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(category)))
                .andExpectAll(
                        status().isBadRequest(),
                        content().string("Category id can't be empty"));
    }

    @Test
    @WithMockUser(value = USER_ID, roles = {"user"})
    void update_emptyTitle_shouldSucceedWith400() throws Exception {
        var category = new CategoryDto(5555L, "", 0L, 0L);
        mvc.perform(put("/category/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(category)))
                .andDo(print())
                .andExpectAll(
                        status().isBadRequest(),
                        content().string("Field 'title' can't be blank"));
    }

    @Test
    void delete_shouldFailWith401() throws Exception {
        mvc.perform(delete("/category/delete").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(value = USER_ID)
    void delete_shouldFailWith403() throws Exception {
        mvc.perform(delete("/category/delete").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = USER_ID, roles = {"user"})
    void delete_shouldSucceedWith200() throws Exception {
        mvc.perform(delete("/category/delete/100")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        assertThat(repository.findById(100L)).isNotPresent();
    }

    @Test
    void search_shouldFailWith401() throws Exception {
        mvc.perform(post("/category/search").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(value = USER_ID)
    void search_shouldFailWith403() throws Exception {
        mvc.perform(post("/category/search").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = USER_ID, roles = {"user"})
    void search_shouldSucceedWith200() throws Exception {
        var searchValues = new SearchValues("o");
        var result = mvc.perform(post("/category/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(searchValues)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        var contentAsString = result.getResponse().getContentAsString();

        var categories = objectMapper.readValue(contentAsString, listType);
        assertThat(categories).hasSize(3)
                .containsExactlyInAnyOrder(CATEGORY_DTO_100, CATEGORY_DTO_101, CATEGORY_DTO_103);
    }

    @Test
    void findById_shouldFailWith401() throws Exception {
        mvc.perform(get("/category/id").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(value = USER_ID)
    void findById_shouldFailWith403() throws Exception {
        mvc.perform(get("/category/id").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = USER_ID, roles = {"user"})
    void findById_shouldSucceedWith200() throws Exception {
        var result = mvc.perform(get("/category/id/100")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();

        var contentAsString = result.getResponse().getContentAsString();

        var addedCategory = objectMapper.readValue(contentAsString, CategoryDto.class);
        assertThat(addedCategory).usingRecursiveComparison().isEqualTo(CATEGORY_DTO_100);
    }

    @Test
    @WithMockUser(value = USER_ID, roles = {"user"})
    void findById_shouldSucceedWith404() throws Exception {
        mvc.perform(get("/category/id/5555")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpectAll(status().isNotFound(),
                        content().string("No value present"));
    }
}
