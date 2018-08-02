package com.dao;

import com.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Long> {
    User findByPreferredUsername(String preferredUsername);

    User findTopByOrderByIdDesc();
}