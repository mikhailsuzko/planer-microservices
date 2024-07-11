package com.sma.micro.planner.todo.integration.controller.priority;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sma.micro.planner.todo.application.use_case.priority.dto.PriorityPublicData;
import com.sma.micro.planner.todo.integration.IntegrationTestBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.sma.micro.planner.todo.model.Constants.PRIORITY_PUBLIC_DATA_100;
import static com.sma.micro.planner.todo.model.Constants.USER_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class FindByIdPriorityControllerIT extends IntegrationTestBase {
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

        var addedPriority = objectMapper.readValue(contentAsString, PriorityPublicData.class);
        assertThat(addedPriority).usingRecursiveComparison().isEqualTo(PRIORITY_PUBLIC_DATA_100);
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
