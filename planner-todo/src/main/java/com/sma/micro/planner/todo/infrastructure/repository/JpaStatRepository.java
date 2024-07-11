package com.sma.micro.planner.todo.infrastructure.repository;

import com.sma.micro.planner.todo.domain.entity.Stat;
import com.sma.micro.planner.todo.domain.repository.StatRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaStatRepository extends StatRepository, JpaRepository<Stat, Long> {


    Optional<Stat> findByUserId(String userId);


    @Override
    default Stat add(Stat stat) {
        return this.save(stat);
    }

    @Override
    default Stat find(String userId) {
        return this.findByUserId(userId).orElseThrow();
    }
}
