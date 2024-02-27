package com.sma.micro.planner.plannerentity.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "statistics", schema = "todo")
public class Statistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "completed_total", nullable = false)
    private Long completed;

    @Column(name = "uncompleted_total", nullable = false)
    private Long uncompleted;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Statistics that = (Statistics) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
