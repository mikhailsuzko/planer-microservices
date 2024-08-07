package com.sma.micro.planner.todo.infrastructure.controller;

import com.sma.micro.planner.todo.common.IntegrationTestBase;
import com.sma.micro.planner.todo.domain.repository.CategoryRepository;
import com.sma.micro.planner.todo.domain.repository.PriorityRepository;
import com.sma.micro.planner.todo.domain.repository.StatRepository;
import com.sma.micro.planner.todo.infrastructure.repository.JpaTaskRepository;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class InitControllerIT extends IntegrationTestBase {
    public static final String NEW_USER = "NewUser";
    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private PriorityRepository priorityRepository;
    @Autowired
    private JpaTaskRepository taskRepository;
    @Autowired
    private StatRepository statRepository;


    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    void stat_shouldFailWith401() throws Exception {
        mvc.perform(post("/data/init").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(value = USER_ID)
    void stat_shouldFailWith403() throws Exception {
        mvc.perform(post("/data/init").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = USER_ID, roles = {"user"})
    void stat_shouldSucceedWith200() throws Exception {
        mvc.perform(post("/data/init").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpectAll(status().isOk(), content().string("false"));
    }

    @Test
    @WithMockUser(value = NEW_USER, roles = {"user"})
    void stat_newUser_shouldSucceedWith200() throws Exception {
        mvc.perform(post("/data/init").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpectAll(status().isOk(), content().string("true"));

        assertThat(statRepository.find(NEW_USER)).isNotNull();
        assertThat(categoryRepository.findAll(NEW_USER)).hasSize(4);
        assertThat(priorityRepository.findAll(NEW_USER)).hasSize(3);
        assertThat(taskRepository.findAll(NEW_USER)).hasSize(2);
    }
}
