package com.sma.micro.planner.plannerentity.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "stat")
public class Stat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Builder.Default
    @Column(name = "completed_total", updatable = false, nullable = false)
    private Long completedTotal = 0L;

    @Builder.Default
    @Column(name = "uncompleted_total", updatable = false, nullable = false)
    private Long uncompletedTotal = 0L;

    @Column(name = "user_id", nullable = false)
    @JsonIgnore
    private String userId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stat that = (Stat) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
