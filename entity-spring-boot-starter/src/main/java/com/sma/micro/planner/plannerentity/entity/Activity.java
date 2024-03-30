package com.sma.micro.planner.plannerentity.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(name = "activity", schema = "todo")
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "uuid", nullable = false, length = -1)
    private String uuid;

    @Column(name = "activated", nullable = false)
    @Convert(converter = org.hibernate.type.NumericBooleanConverter.class)
    private Boolean activated;

    @Column(name = "user_id", nullable = false)
    @JsonIgnore
    private String userId;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Activity activity = (Activity) o;
        return Objects.equals(id, activity.id) && Objects.equals(uuid, activity.uuid) && Objects.equals(activated, activity.activated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, uuid, activated);
    }
}
