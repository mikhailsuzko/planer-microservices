package com.sma.micro.planner.todo.application.use_case.task;

import com.sma.micro.planner.plannerutils.util.Utils;
import com.sma.micro.planner.todo.application.use_case.task.dto.TaskPublicData;
import com.sma.micro.planner.todo.application.use_case.task.dto.TaskSearchValues;
import com.sma.micro.planner.todo.domain.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import static org.apache.logging.log4j.util.Strings.isBlank;

@Service
@RequiredArgsConstructor
public class FindTasksByParamsUseCase {
    private static final String TITLE_COLUMN = "title";
    private final TaskRepository repository;
    private final TaskMapper mapper;

    public Page<TaskPublicData> execute(TaskSearchValues params, String userId) {
        var dateFrom = params.dateFrom() == null ? null : params.dateFrom().atStartOfDay();
        var dateTo = params.dateTo() == null ? null : params.dateTo().atTime(23, 59, 59, 999_999_999);
        var direction = isBlank(params.sortDirection())
                || params.sortDirection().trim().equalsIgnoreCase("asc")
                ? Sort.Direction.ASC : Sort.Direction.DESC;
        var sort = Sort.by(direction, params.sortColumn());
        if (!params.sortColumn().equals(TITLE_COLUMN)) {
            sort = sort.and(Sort.by(Sort.Direction.ASC, TITLE_COLUMN));
        }
        var pageRequest = PageRequest.of(params.pageNumber(), params.pageSize(), sort);

        return repository.findByParams(
                        Utils.prepareParam(params.title()),
                        params.completed(),
                        params.priorityId(),
                        params.categoryId(),
                        dateFrom, dateTo,
                        userId,
                        pageRequest)
                .map(mapper::toTaskPublicData);
    }
}
