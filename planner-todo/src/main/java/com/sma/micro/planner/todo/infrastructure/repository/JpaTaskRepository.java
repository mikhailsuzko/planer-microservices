package com.sma.micro.planner.todo.infrastructure.repository;

import com.sma.micro.planner.todo.domain.entity.Task;
import com.sma.micro.planner.todo.domain.repository.TaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface JpaTaskRepository extends TaskRepository, JpaRepository<Task, Long> {

    void deleteByIdAndUserId(Long id, String userId);

    Optional<Task> findByIdAndUserId(Long id, String userId);

    List<Task> findByUserIdOrderByTitle(String userId);

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

    @Override
    default Task add(Task task) {
        return this.save(task);
    }

    @Override
    default void update(Task task) {
        this.save(task);
    }

    @Override
    default void delete(Long id, String userId) {
        this.deleteByIdAndUserId(id, userId);
    }

    @Override
    default Task findById(Long id, String userId) {
        return this.findByIdAndUserId(id, userId).orElseThrow();
    }

    @Override
    default List<Task> findAll(String userId) {
        return this.findByUserIdOrderByTitle(userId);
    }

}
