package com.sma.micro.planner.todo.model;

import com.sma.micro.planner.todo.application.use_case.category.dto.CategoryPublicData;
import com.sma.micro.planner.todo.application.use_case.category.dto.CategoryRegistrationData;
import com.sma.micro.planner.todo.application.use_case.category.dto.CategoryUpdateData;
import com.sma.micro.planner.todo.application.use_case.priority.dto.PriorityPublicData;
import com.sma.micro.planner.todo.application.use_case.priority.dto.PriorityRegistrationData;
import com.sma.micro.planner.todo.application.use_case.priority.dto.PriorityUpdateData;
import com.sma.micro.planner.todo.domain.entity.Category;
import com.sma.micro.planner.todo.domain.entity.Priority;
import com.sma.micro.planner.todo.domain.entity.Task;
import com.sma.micro.planner.todo.dto.CategoryDto;
import com.sma.micro.planner.todo.dto.PriorityDto;
import com.sma.micro.planner.todo.dto.TaskDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {
    public static final long ID_10 = 10L;
    public static final long ID_20 = 20L;
    public static final Long COUNT = 0L;
    public static final Long COMPLETED_COUNT = 10L;
    public static final Long UNCOMPLETED_COUNT = 2L;
    public static final String USER_ID = "userId";
    public static final String TITLE = "title";
    public static final String COLOR = "color";

    public static final Category CATEGORY = new Category("Sport", USER_ID);
    public static final Category CATEGORY_100 = new Category(100L, "Sport", 1L, 1L, USER_ID);
    public static final CategoryDto CATEGORY_DTO = new CategoryDto(ID_10, TITLE, COUNT, COUNT);
    public static final Priority PRIORITY = new Priority("High", "#FBBABA", USER_ID);
    public static final Priority PRIORITY_100 = new Priority(100L, "High", "#FBBABA", USER_ID);
    public static final PriorityDto PRIORITY_DTO = new PriorityDto(ID_10, TITLE, COLOR);
    public static final Task TASK = new Task(ID_10, TITLE, false, null, CATEGORY, PRIORITY, USER_ID);
    public static final TaskDto TASK_DTO = new TaskDto(ID_10, TITLE, false, null, CATEGORY_DTO, PRIORITY_DTO);
    public static final List<Task> TASKS = List.of(TASK);
    public static final List<TaskDto> TASKS_DTO = List.of(TASK_DTO);

    public static final CategoryDto CATEGORY_DTO_100 = new CategoryDto(100L, "Sport", 1L, 1L);
    public static final CategoryDto CATEGORY_DTO_101 = new CategoryDto(101L, "Home", 0L, 1L);
    public static final CategoryDto CATEGORY_DTO_102 = new CategoryDto(102L, "Travelling", 0L, 1L);
    public static final CategoryDto CATEGORY_DTO_103 = new CategoryDto(103L, "Hobby", 1L, 0L);

    public static final CategoryRegistrationData CATEGORY_REGISTRATION_DATA_100 = new CategoryRegistrationData("Sport");
    public static final CategoryUpdateData CATEGORY_UPDATE_DATA_100 = new CategoryUpdateData(100L, "Sport");
    public static final CategoryPublicData CATEGORY_PUBLIC_DATA_100 = new CategoryPublicData(100L, "Sport", 1L, 1L);
    public static final CategoryPublicData CATEGORY_PUBLIC_DATA_101 = new CategoryPublicData(101L, "Home", 0L, 1L);
    public static final CategoryPublicData CATEGORY_PUBLIC_DATA_102 = new CategoryPublicData(102L, "Travelling", 0L, 1L);
    public static final CategoryPublicData CATEGORY_PUBLIC_DATA_103 = new CategoryPublicData(103L, "Hobby", 1L, 0L);

    public static final PriorityDto PRIORITY_DTO_100 = new PriorityDto(100L, "High", "#FBBABA");
    public static final PriorityDto PRIORITY_DTO_101 = new PriorityDto(101L, "Medium", "#CFF4CF");
    public static final PriorityDto PRIORITY_DTO_102 = new PriorityDto(102L, "Low", "#CCE7FF");
    public static final PriorityRegistrationData PRIORITY_REGISTRATION_DATA = new PriorityRegistrationData("High", "#FBBABA");
    public static final PriorityUpdateData PRIORITY_UPDATE_DATA_100 = new PriorityUpdateData(100L, "High", "#FBBABA");
    public static final PriorityPublicData PRIORITY_PUBLIC_DATA_100 = new PriorityPublicData(100L, "High", "#FBBABA");
    public static final PriorityPublicData PRIORITY_PUBLIC_DATA_101 = new PriorityPublicData(101L, "Medium", "#CFF4CF");
    public static final PriorityPublicData PRIORITY_PUBLIC_DATA_102 = new PriorityPublicData(102L, "Low", "#CCE7FF");

    public static final TaskDto TASK_DTO_100 = new TaskDto(100L, "Read book", false, null, CATEGORY_DTO_100, PRIORITY_DTO_100);
    public static final TaskDto TASK_DTO_101 = new TaskDto(101L, "Walk", false, null, CATEGORY_DTO_102, PRIORITY_DTO_101);
    public static final TaskDto TASK_DTO_102 = new TaskDto(102L, "Swim", true, null, CATEGORY_DTO_100, PRIORITY_DTO_102);
    public static final TaskDto TASK_DTO_103 = new TaskDto(103L, "Report", false, null, CATEGORY_DTO_101, PRIORITY_DTO_100);
    public static final TaskDto TASK_DTO_104 = new TaskDto(104L, "Fishing", true, null, CATEGORY_DTO_103, PRIORITY_DTO_101);
}
