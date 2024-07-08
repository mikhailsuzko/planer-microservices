package com.sma.micro.planner.todo.repository;

import com.sma.micro.planner.todo.domain.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("SELECT t " +
            "from Task t " +
            "where t.userId = :userId " +
            "and (:title is null or lower(t.title) like :title) " +
            "and (:completed is null or t.completed = :completed) " +
            "and (:priorityId is null or t.priority.id = :priorityId) " +
            "and (:categoryId is null or t.category.id = :categoryId) " +
            "and ( " +
            "(cast(:dateFrom as timestamp) is null or t.taskDate>=:dateFrom) " +
            "and (cast(:dateTo as timestamp) is null or t.taskDate<=:dateTo)" +
            ")")
    Page<Task> findByParams(String title,
                            Boolean completed,
                            Long priorityId,
                            Long categoryId,
                            LocalDateTime dateFrom,
                            LocalDateTime dateTo,
                            String userId,
                            Pageable pageable);

    List<Task> findByUserIdOrderByTaskDateDescTitleAsc(String userId);

    void deleteByIdAndUserId(Long id, String userId);

    Optional<Task> findByIdAndUserId(Long id, String userId);
}
