package com.sma.micro.planner.todo.infrastructure.controller.priority;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sma.micro.planner.todo.application.use_case.priority.dto.PriorityPublicData;
import com.sma.micro.planner.todo.common.IntegrationTestBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static com.sma.micro.planner.todo.common.model.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class FindAllPriorityControllerIT extends IntegrationTestBase {
    private static final TypeReference<List<PriorityPublicData>> listType = new TypeReference<>() {
    };
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

        var priorities = objectMapper.readValue(contentAsString, listType);
        assertThat(priorities).hasSize(3)
                .containsExactlyInAnyOrder(PRIORITY_PUBLIC_DATA_100, PRIORITY_PUBLIC_DATA_101, PRIORITY_PUBLIC_DATA_102);
    }
}
