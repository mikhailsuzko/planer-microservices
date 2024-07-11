package com.sma.micro.planner.todo.application.use_case.priority;

import com.sma.micro.planner.todo.application.use_case.priority.dto.PriorityPublicData;
import com.sma.micro.planner.todo.application.use_case.priority.dto.PriorityRegistrationData;
import com.sma.micro.planner.todo.application.use_case.priority.dto.PriorityUpdateData;
import com.sma.micro.planner.todo.domain.entity.Priority;
import org.springframework.stereotype.Service;

@Service
public class PriorityMapper {

    public PriorityPublicData toPriorityPublicData(Priority priority) {
        return new PriorityPublicData(
                priority.getId(),
                priority.getTitle(),
                priority.getColor());
    }

    public Priority toPriority(PriorityPublicData data, String userId) {
        return new Priority(
                data.id(),
                data.title(),
                data.color(),
                userId);
    }

    public Priority toPriority(PriorityRegistrationData data, String userId) {
        return new Priority(
                data.title(),
                data.color(),
                userId);
    }

    public Priority toPriority(PriorityUpdateData data, String userId) {
        return new Priority(
                data.id(),
                data.title(),
                data.color(),
                userId);
    }
}
