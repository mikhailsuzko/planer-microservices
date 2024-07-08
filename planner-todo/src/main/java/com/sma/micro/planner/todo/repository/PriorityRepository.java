package com.sma.micro.planner.todo.repository;

import com.sma.micro.planner.todo.domain.entity.Priority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PriorityRepository extends JpaRepository<Priority, Long> {

    List<Priority> findByUserIdOrderByIdAsc(String userId);

    @Query("SELECT p " +
            "from Priority p " +
            "where p.userId = :userId " +
            "and (:title is null " +
            "or lower(p.title) like :title)" +
            "order by p.title asc ")
    List<Priority> findByTitle(String title, String userId);

    void deleteByIdAndUserId(Long id, String userId);

    Optional<Priority> findByIdAndUserId(Long id, String userId);
}
