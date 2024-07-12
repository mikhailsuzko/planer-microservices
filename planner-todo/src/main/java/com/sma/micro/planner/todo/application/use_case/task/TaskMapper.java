package com.sma.micro.planner.todo.application.use_case.task;

import com.sma.micro.planner.todo.application.use_case.category.CategoryMapper;
import com.sma.micro.planner.todo.application.use_case.priority.PriorityMapper;
import com.sma.micro.planner.todo.application.use_case.task.dto.TaskPublicData;
import com.sma.micro.planner.todo.application.use_case.task.dto.TaskRegistrationData;
import com.sma.micro.planner.todo.application.use_case.task.dto.TaskUpdateData;
import com.sma.micro.planner.todo.domain.entity.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskMapper {
    private final CategoryMapper categoryMapper;
    private final PriorityMapper priorityMapper;

    public TaskPublicData toTaskPublicData(Task task) {
        return new TaskPublicData(
                task.getId(),
                task.getTitle(),
                task.getCompleted(),
                task.getTaskDate(),
                categoryMapper.toCategoryPublicData(task.getCategory()),
                priorityMapper.toPriorityPublicData(task.getPriority()));
    }

    public Task toTask(TaskPublicData data, String userId) {
        return new Task(
                data.id(),
                data.title(),
                data.completed(),
                data.taskDate(),
                categoryMapper.toCategory(data.category(), userId),
                priorityMapper.toPriority(data.priority(), userId),
                userId);
    }

    public Task toTask(TaskRegistrationData data, String userId) {
        return new Task(
                data.title(),
                data.completed(),
                data.taskDate(),
                categoryMapper.toCategory(data.category(), userId),
                priorityMapper.toPriority(data.priority(), userId),
                userId);
    }

    public Task toTask(TaskUpdateData data, String userId) {
        return new Task(
                data.id(),
                data.title(),
                data.completed(),
                data.taskDate(),
                categoryMapper.toCategory(data.category(), userId),
                priorityMapper.toPriority(data.priority(), userId),
                userId);
    }

}
