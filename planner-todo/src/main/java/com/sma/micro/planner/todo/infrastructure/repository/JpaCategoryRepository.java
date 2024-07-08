package com.sma.micro.planner.todo.infrastructure.repository;

import com.sma.micro.planner.todo.domain.entity.Category;
import com.sma.micro.planner.todo.domain.repository.CategoryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaCategoryRepository extends CategoryRepository, JpaRepository<Category, Long> {

    void deleteByIdAndUserId(Long id, String userId);

    Optional<Category> findByIdAndUserId(Long id, String userId);

    List<Category> findByUserIdOrderByTitle(String userId);

    @Query("SELECT c " +
            "from Category c " +
            "where c.userId = :userId " +
            "and (:title is null " +
            "or lower(c.title) like :title)" +
            "order by c.title asc ")
    List<Category> findByTitle(String title, String userId);

    @Override
    default Category add(Category category) {
        return this.save(category);
    }

    @Override
    default void update(Category category) {
        this.save(category);
    }

    @Override
    default void delete(Long id, String userId) {
        this.deleteByIdAndUserId(id, userId);
    }

    @Override
    default Category findById(Long id, String userId) {
        return this.findByIdAndUserId(id, userId).orElseThrow();
    }

    @Override
    default List<Category> findAll(String userId) {
        return this.findByUserIdOrderByTitle(userId);
    }
}
