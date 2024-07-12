package com.sma.micro.planner.todo.application.use_case.priority;

import com.sma.micro.planner.plannerutils.util.Utils;
import com.sma.micro.planner.todo.application.use_case.priority.dto.PriorityPublicData;
import com.sma.micro.planner.todo.domain.entity.Priority;
import com.sma.micro.planner.todo.domain.repository.PriorityRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static com.sma.micro.planner.todo.common.model.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {FindByTitlePrioritiesUseCase.class, PriorityMapper.class})
class FindByTitlePrioritiesUseCaseTest {
    @Autowired
    private FindByTitlePrioritiesUseCase findByTitlePrioritiesUseCase;
    @MockBean
    private PriorityRepository priorityRepository;

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(priorityRepository);
    }

    @Test
    void execute() {
        var priority10Expected = new Priority(ID_10, TITLE, COLOR, USER_ID);
        var priority20Expected = new Priority(ID_20, TITLE, COLOR, USER_ID);

        when(priorityRepository.findByTitle(Utils.prepareParam(TITLE), USER_ID)).thenReturn(List.of(priority10Expected, priority20Expected));

        var result = findByTitlePrioritiesUseCase.execute(TITLE, USER_ID);

        assertThat(result).containsExactly(
                new PriorityPublicData(ID_10, TITLE, COLOR),
                new PriorityPublicData(ID_20, TITLE, COLOR)
        );
        verify(priorityRepository).findByTitle(Utils.prepareParam(TITLE), USER_ID);
    }
}