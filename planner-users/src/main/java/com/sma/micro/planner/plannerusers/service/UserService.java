package com.sma.micro.planner.plannerusers.service;

import com.sma.micro.planner.plannerentity.entity.User;
import com.sma.micro.planner.plannerusers.repository.UserRepository;
import com.sma.micro.planner.plannerutils.util.Utils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public User findByEmail(String email) {
        return repository.findByEmail(email).orElseThrow();
    }

    public User add(User user) {
        return repository.save(user);
    }

    public User update(User user) {
        return repository.save(user);
    }

    public void delete(Long userId) {
        repository.deleteById(userId);
    }

    public void delete(String email) {
        repository.deleteByEmail(email);
    }

    public User findById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    public Page<User> findByParams(String username, String email, PageRequest pageRequest) {
        return repository.findByParams(Utils.prepareParam(username), Utils.prepareParam(email), pageRequest);
    }

}
