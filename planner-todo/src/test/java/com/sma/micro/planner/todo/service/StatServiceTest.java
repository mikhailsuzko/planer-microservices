package com.sma.micro.planner.todo.service;

import com.sma.micro.planner.plannerentity.entity.Stat;
import com.sma.micro.planner.todo.dto.StatDto;
import com.sma.micro.planner.todo.repository.StatRepository;
import com.sma.micro.planner.todo.service.mapper.StatMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.NoSuchElementException;
import java.util.Optional;

import static com.sma.micro.planner.todo.model.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {StatService.class})
class StatServiceTest {
    @Autowired
    private StatService service;
    @MockBean
    private StatRepository repository;
    @MockBean
    private StatMapper mapper;

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(repository, mapper);
    }

    @Test
    void findStat() {
        var stat = new Stat(ID, COUNT, COUNT, USER_ID);
        var statDto = new StatDto(ID, COUNT, COUNT);
        when(repository.findByUserId(USER_ID)).thenReturn(Optional.of(stat));
        when(mapper.statToDto(stat)).thenReturn(statDto);

        var res = service.findStat(USER_ID);

        assertThat(res).isEqualTo(statDto);
        verify(repository).findByUserId(USER_ID);
        verify(mapper).statToDto(stat);
    }

    @Test
    void findStat_exception() {
        when(repository.findByUserId(USER_ID)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> service.findStat(USER_ID));

        verify(repository).findByUserId(USER_ID);
    }

    @Test
    void add() {
        var stat = new Stat(ID, COUNT, COUNT, USER_ID);

        service.add(stat);

        verify(repository).save(stat);
    }
}