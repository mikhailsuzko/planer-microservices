package com.sma.micro.planner.todo.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.Objects;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "completed_count", updatable = false)
    @Builder.Default
    private Long completedCount = 0L;

    @Column(name = "uncompleted_count", updatable = false)
    @Builder.Default
    private Long uncompletedCount = 0L;

    @Column(name = "user_id", nullable = false)
    @JsonIgnore
    private String userId;

    public Category(String title, String userId) {
        this.title = title;
        this.userId = userId;
        this.completedCount = 0L;
        this.uncompletedCount = 0L;
    }

    public Category(Long id, String title, String userId) {
        this(title, userId);
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(id, category.id) && Objects.equals(title, category.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }
}