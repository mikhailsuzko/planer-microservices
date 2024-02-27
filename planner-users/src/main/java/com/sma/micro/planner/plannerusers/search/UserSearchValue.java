package com.sma.micro.planner.plannerusers.search;

public record UserSearchValue(
        String username,
        String email,
        Integer pageNumber,
        Integer pageSize,
        String sortColumn,
        String sortDirection

) {
}
