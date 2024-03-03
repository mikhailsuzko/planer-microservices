package com.sma.micro.planner.todo.search;

import java.time.LocalDate;

public record TaskSearchValues(
        String title,
        Boolean completed,
        Long priorityId,
        Long categoryId,
        LocalDate dateFrom,
        LocalDate dateTo,
        Integer pageNumber,
        Integer pageSize,
        String sortColumn,
        String sortDirection
) {

}
