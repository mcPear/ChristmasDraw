package com.dao;

import com.domain.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupDao extends JpaRepository<Group, Long> {
    Group findByName(String name);
}
