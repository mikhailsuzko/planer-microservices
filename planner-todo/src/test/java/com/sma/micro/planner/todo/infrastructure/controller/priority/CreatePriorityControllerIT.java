package com.sma.micro.planner.todo.infrastructure.controller.priority;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sma.micro.planner.todo.application.use_case.priority.dto.PriorityRegistrationData;
import com.sma.micro.planner.todo.common.IntegrationTestBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.sma.micro.planner.todo.common.model.Constants.COLOR;
import static com.sma.micro.planner.todo.common.model.Constants.USER_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CreatePriorityControllerIT extends IntegrationTestBase {
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
        var priority = new PriorityRegistrationData("Hobby", COLOR);
        var result = mvc.perform(
                        post("/priority/add")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(priority)))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
        var contentAsString = result.getResponse().getContentAsString();

        var addedPriority = objectMapper.readValue(contentAsString, PriorityRegistrationData.class);
        assertThat(addedPriority).usingRecursiveComparison().ignoringFields("id").isEqualTo(priority);
    }

    @Test
    @WithMockUser(value = USER_ID, roles = {"user"})
    void add_emptyTitle_shouldSucceedWith400() throws Exception {
        var priority = new PriorityRegistrationData("", COLOR);
        mvc.perform(post("/priority/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(priority)))
                .andDo(print())
                .andExpectAll(
                        status().isBadRequest(),
                        content().string("title must not be blank"));
    }
}
