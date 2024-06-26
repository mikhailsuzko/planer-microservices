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

    public static final CategoryDto CATEGORY_DTO_100 = new CategoryDto(100L, "Sport", 1L, 1L);
    public static final CategoryDto CATEGORY_DTO_101 = new CategoryDto(101L, "Home", 0L, 1L);
    public static final CategoryDto CATEGORY_DTO_102 = new CategoryDto(102L, "Travelling", 0L, 1L);
    public static final CategoryDto CATEGORY_DTO_103 = new CategoryDto(103L, "Hobby", 1L, 0L);

    public static final PriorityDto PRIORITY_DTO_100 = new PriorityDto(100L, "High", "#FBBABA");
    public static final PriorityDto PRIORITY_DTO_101 = new PriorityDto(101L, "Medium", "#CFF4CF");
    public static final PriorityDto PRIORITY_DTO_102 = new PriorityDto(102L, "Low", "#CCE7FF");

    public static final TaskDto TASK_DTO_100 = new TaskDto(100L, "Read book", false, null, CATEGORY_DTO_100, PRIORITY_DTO_100);
    public static final TaskDto TASK_DTO_101 = new TaskDto(101L, "Walk", false, null, CATEGORY_DTO_102, PRIORITY_DTO_101);
    public static final TaskDto TASK_DTO_102 = new TaskDto(102L, "Swim", true, null, CATEGORY_DTO_100, PRIORITY_DTO_102);
    public static final TaskDto TASK_DTO_103 = new TaskDto(103L, "Report", false, null, CATEGORY_DTO_101, PRIORITY_DTO_100);
    public static final TaskDto TASK_DTO_104 = new TaskDto(104L, "Fishing", true, null, CATEGORY_DTO_103, PRIORITY_DTO_101);
}
