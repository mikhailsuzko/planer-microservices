package com.sma.micro.planner.todo.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.type.NumericBooleanConverter;

import java.time.LocalDateTime;
import java.util.Objects;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "completed")
    @Convert(converter = NumericBooleanConverter.class)
    @Builder.Default
    private Boolean completed = false;

    @Column(name = "task_date")
    private LocalDateTime taskDate;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "priority_id", referencedColumnName = "id")
    private Priority priority;

    @Column(name = "user_id", nullable = false)
    @JsonIgnore
    private String userId;

    public Task(String title, Boolean completed, LocalDateTime taskDate, Category category, Priority priority, String userId) {
        this.title = title;
        this.completed = completed;
        this.taskDate = taskDate;
        this.category = category;
        this.priority = priority;
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id) && Objects.equals(title, task.title) && Objects.equals(completed, task.completed) && Objects.equals(taskDate, task.taskDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, completed, taskDate);
    }
}
