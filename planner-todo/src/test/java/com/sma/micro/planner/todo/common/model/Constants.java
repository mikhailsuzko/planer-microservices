package com.sma.micro.planner.todo.common.model;

import com.sma.micro.planner.todo.application.use_case.category.dto.CategoryPublicData;
import com.sma.micro.planner.todo.application.use_case.category.dto.CategoryRegistrationData;
import com.sma.micro.planner.todo.application.use_case.category.dto.CategoryUpdateData;
import com.sma.micro.planner.todo.application.use_case.priority.dto.PriorityPublicData;
import com.sma.micro.planner.todo.application.use_case.priority.dto.PriorityRegistrationData;
import com.sma.micro.planner.todo.application.use_case.priority.dto.PriorityUpdateData;
import com.sma.micro.planner.todo.application.use_case.task.dto.TaskPublicData;
import com.sma.micro.planner.todo.application.use_case.task.dto.TaskRegistrationData;
import com.sma.micro.planner.todo.application.use_case.task.dto.TaskUpdateData;
import com.sma.micro.planner.todo.domain.entity.Category;
import com.sma.micro.planner.todo.domain.entity.Priority;
import com.sma.micro.planner.todo.domain.entity.Task;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

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
    public static final Category CATEGORY_101 = new Category(101L, "Home", 0L, 1L, USER_ID);
    public static final Category CATEGORY_102 = new Category(102L, "Travelling", 0L, 1L, USER_ID);
    public static final Priority PRIORITY = new Priority("High", "#FBBABA", USER_ID);
    public static final Priority PRIORITY_100 = new Priority(100L, "High", "#FBBABA", USER_ID);
    public static final Priority PRIORITY_101 = new Priority(101L, "Medium", "#CFF4CF", USER_ID);
    public static final Priority PRIORITY_102 = new Priority(102L, "Low", "#CCE7FF", USER_ID);

    public static final CategoryRegistrationData CATEGORY_REGISTRATION_DATA_100 = new CategoryRegistrationData("Sport");
    public static final CategoryUpdateData CATEGORY_UPDATE_DATA_100 = new CategoryUpdateData(100L, "Sport");
    public static final CategoryPublicData CATEGORY_PUBLIC_DATA_100 = new CategoryPublicData(100L, "Sport", 1L, 1L);
    public static final CategoryPublicData CATEGORY_PUBLIC_DATA_101 = new CategoryPublicData(101L, "Home", 0L, 1L);
    public static final CategoryPublicData CATEGORY_PUBLIC_DATA_102 = new CategoryPublicData(102L, "Travelling", 0L, 1L);
    public static final CategoryPublicData CATEGORY_PUBLIC_DATA_103 = new CategoryPublicData(103L, "Hobby", 1L, 0L);

    public static final PriorityRegistrationData PRIORITY_REGISTRATION_DATA = new PriorityRegistrationData("High", "#FBBABA");
    public static final PriorityUpdateData PRIORITY_UPDATE_DATA_100 = new PriorityUpdateData(100L, "High", "#FBBABA");
    public static final PriorityPublicData PRIORITY_PUBLIC_DATA_100 = new PriorityPublicData(100L, "High", "#FBBABA");
    public static final PriorityPublicData PRIORITY_PUBLIC_DATA_101 = new PriorityPublicData(101L, "Medium", "#CFF4CF");
    public static final PriorityPublicData PRIORITY_PUBLIC_DATA_102 = new PriorityPublicData(102L, "Low", "#CCE7FF");

    public static final Task TASK = new Task("Read book", false, null, CATEGORY_100, PRIORITY_100, USER_ID);
    public static final Task TASK_100 = new Task(100L, "Read book", false, null, CATEGORY_100, PRIORITY_100, USER_ID);
    public static final Task TASK_101 = new Task(101L, "Walk", false, null, CATEGORY_102, PRIORITY_101, USER_ID);
    public static final TaskRegistrationData TASK_REGISTRATION_DATA = new TaskRegistrationData("Read book", false, null, CATEGORY_PUBLIC_DATA_100, PRIORITY_PUBLIC_DATA_100);
    public static final TaskUpdateData TASK_UPDATE_DATA_100 = new TaskUpdateData(100L, "Read book", false, null, CATEGORY_PUBLIC_DATA_100, PRIORITY_PUBLIC_DATA_100);
    public static final TaskPublicData TASK_PUBLIC_DATA_100 = new TaskPublicData(100L, "Read book", false, null, CATEGORY_PUBLIC_DATA_100, PRIORITY_PUBLIC_DATA_100);
    public static final TaskPublicData TASK_PUBLIC_DATA_101 = new TaskPublicData(101L, "Walk", false, null, CATEGORY_PUBLIC_DATA_102, PRIORITY_PUBLIC_DATA_101);
    public static final TaskPublicData TASK_PUBLIC_DATA_102 = new TaskPublicData(102L, "Swim", true, null, CATEGORY_PUBLIC_DATA_100, PRIORITY_PUBLIC_DATA_102);
    public static final TaskPublicData TASK_PUBLIC_DATA_103 = new TaskPublicData(103L, "Report", false, null, CATEGORY_PUBLIC_DATA_101, PRIORITY_PUBLIC_DATA_100);
    public static final TaskPublicData TASK_PUBLIC_DATA_104 = new TaskPublicData(104L, "Fishing", true, null, CATEGORY_PUBLIC_DATA_103, PRIORITY_PUBLIC_DATA_101);
}
