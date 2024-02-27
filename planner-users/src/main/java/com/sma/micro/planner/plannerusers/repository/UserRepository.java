package com.sma.micro.planner.plannerusers.repository;

import com.sma.micro.planner.plannerentity.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    void deleteByEmail(String email);

    @Query("FROM User " +
            "where (:email is null or lower(email) like :email) " +
            "and (:username is null or lower(username) like :username)"
    )
    Page<User> findByParams(String username, String email, Pageable pageable);
}
