package com.sma.micro.planner.todo.model;

import com.sma.micro.planner.plannerentity.entity.Category;
import com.sma.micro.planner.plannerentity.entity.Priority;
import com.sma.micro.planner.plannerentity.entity.Task;
import com.sma.micro.planner.todo.dto.CategoryDto;
import com.sma.micro.planner.todo.dto.PriorityDto;
import com.sma.micro.planner.todo.dto.TaskDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {
    public static final long ID = 10L;
    public static final Long COUNT = 0L;
    public static final String USER_ID = "userId";
    public static final String TITLE = "title";
    public static final String COLOR = "color";

    public static final Category CATEGORY = new Category(ID, TITLE, COUNT, COUNT, USER_ID);
    public static final CategoryDto CATEGORY_DTO = new CategoryDto(ID, TITLE, COUNT, COUNT);
    public static final List<Category> CATEGORIES = List.of(CATEGORY);
    public static final List<CategoryDto> CATEGORIES_DTO = List.of(CATEGORY_DTO);
    public static final Priority PRIORITY = new Priority(ID, TITLE, COLOR, USER_ID);
    public static final PriorityDto PRIORITY_DTO = new PriorityDto(ID, TITLE, COLOR);
    public static final List<Priority> PRIORITIES = List.of(PRIORITY);
    public static final List<PriorityDto> PRIORITIES_DTO = List.of(PRIORITY_DTO);
    public static final Task TASK = new Task(ID, TITLE, false, null, CATEGORY, PRIORITY, USER_ID);
    public static final TaskDto TASK_DTO = new TaskDto(ID, TITLE, false, null, CATEGORY_DTO, PRIORITY_DTO);
    public static final List<Task> TASKS = List.of(TASK);
    public static final List<TaskDto> TASKS_DTO = List.of(TASK_DTO);
}
