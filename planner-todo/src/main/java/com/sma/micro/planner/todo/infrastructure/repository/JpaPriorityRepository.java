package com.sma.micro.planner.todo.infrastructure.repository;

import com.sma.micro.planner.todo.domain.entity.Priority;
import com.sma.micro.planner.todo.domain.repository.PriorityRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaPriorityRepository extends PriorityRepository, JpaRepository<Priority, Long> {

    void deleteByIdAndUserId(Long id, String userId);

    Optional<Priority> findByIdAndUserId(Long id, String userId);

    List<Priority> findByUserIdOrderByTitle(String userId);

    @Query("SELECT p " +
            "from Priority p " +
            "where p.userId = :userId " +
            "and (:title is null " +
            "or lower(p.title) like :title)" +
            "order by p.title asc ")
    List<Priority> findByTitle(String title, String userId);

    @Override
    default Priority add(Priority priority) {
        return this.save(priority);
    }

    @Override
    default void update(Priority priority) {
        this.save(priority);
    }

    @Override
    default void delete(Long id, String userId) {
        this.deleteByIdAndUserId(id, userId);
    }

    @Override
    default Priority findById(Long id, String userId) {
        return this.findByIdAndUserId(id, userId).orElseThrow();
    }

    @Override
    default List<Priority> findAll(String userId) {
        return this.findByUserIdOrderByTitle(userId);
    }
}
