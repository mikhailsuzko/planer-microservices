package com.sma.micro.planner.todo.repository;

import com.sma.micro.planner.plannerentity.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("SELECT c " +
            "from Category c " +
            "where c.userId = :userId " +
            "and (:title is null " +
            "or lower(c.title) like :title)" +
            "order by c.title asc ")
    List<Task> findByTitle(String title, Long userId);

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
                            Long userId,
                            Pageable pageable);

    List<Task> findByUserIdOrderByTaskDateDescTitleAsc(Long userId);
}
