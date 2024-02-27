package com.sma.micro.planner.todo.repository;

import com.sma.micro.planner.plannerentity.entity.Priority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriorityRepository extends JpaRepository<Priority, Long> {

    List<Priority> findByUserIdOrderByIdAsc(Long userId);

    @Query("SELECT p " +
            "from Priority p " +
            "where p.userId = :userId " +
            "and (:title is null " +
            "or lower(p.title) like :title)" +
            "order by p.title asc ")
    List<Priority> findByTitle(String title, Long userId);
}