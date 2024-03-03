package com.sma.micro.planner.todo.repository;

import com.sma.micro.planner.plannerentity.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByUserIdOrderByTitle(String userId);

    @Query("SELECT c " +
            "from Category c " +
            "where c.userId = :userId " +
            "and (:title is null " +
            "or lower(c.title) like :title)" +
            "order by c.title asc ")
    List<Category> findByTitle(String title, String userId);
}
