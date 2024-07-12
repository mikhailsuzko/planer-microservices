package com.sma.micro.planner.todo.infrastructure.controller.category;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sma.micro.planner.todo.application.use_case.category.dto.CategoryUpdateData;
import com.sma.micro.planner.todo.common.IntegrationTestBase;
import com.sma.micro.planner.todo.infrastructure.repository.JpaCategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.sma.micro.planner.todo.common.model.Constants.USER_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UpdateCategoryControllerIT extends IntegrationTestBase {
    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private JpaCategoryRepository repository;


    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
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
        var category = new CategoryUpdateData(100L, "Hobby");
        mvc.perform(put("/category/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(category)))
                .andDo(print())
                .andExpect(status().isOk());
        assertThat(repository.findById(100L, USER_ID).getTitle())
                .isEqualTo("Hobby");
    }

    @Test
    @WithMockUser(value = USER_ID, roles = {"user"})
    void update_emptyId_shouldSucceedWith400() throws Exception {
        var category = new CategoryUpdateData(null, "Hobby");
        mvc.perform(put("/category/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(category)))
                .andExpectAll(
                        status().isBadRequest(),
                        content().string("id must not be null"));
    }

    @Test
    @WithMockUser(value = USER_ID, roles = {"user"})
    void update_emptyTitle_shouldSucceedWith400() throws Exception {
        var category = new CategoryUpdateData(5555L, "");
        mvc.perform(put("/category/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(category)))
                .andDo(print())
                .andExpectAll(
                        status().isBadRequest(),
                        content().string("title must not be blank"));
    }
}
