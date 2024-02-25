package com.example.project.repository.group;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.project.model.group.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    
}