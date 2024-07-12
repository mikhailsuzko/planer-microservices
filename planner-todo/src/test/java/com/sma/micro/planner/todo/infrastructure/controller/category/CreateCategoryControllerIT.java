package com.sma.micro.planner.todo.infrastructure.controller.category;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sma.micro.planner.todo.application.use_case.category.dto.CategoryPublicData;
import com.sma.micro.planner.todo.application.use_case.category.dto.CategoryRegistrationData;
import com.sma.micro.planner.todo.common.IntegrationTestBase;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CreateCategoryControllerIT extends IntegrationTestBase {
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
        var category = new CategoryRegistrationData("Hobby");
        var result = mvc.perform(
                        post("/category/add")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(category)))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
        var contentAsString = result.getResponse().getContentAsString();

        var addedCategory = objectMapper.readValue(contentAsString, CategoryPublicData.class);
        var expectedCategory = new CategoryPublicData(ID_10, category.title(), COUNT, COUNT);
        assertThat(addedCategory).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expectedCategory);
    }

    @Test
    @WithMockUser(value = USER_ID, roles = {"user"})
    void add_emptyTitle_shouldSucceedWith400() throws Exception {
        var category = new CategoryRegistrationData("");
        mvc.perform(post("/category/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(category)))
                .andDo(print())
                .andExpectAll(
                        status().isBadRequest(),
                        content().string("title must not be blank"));
    }
}
