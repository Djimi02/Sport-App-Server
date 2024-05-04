package com.example.project.repository.group;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.project.model.group.BasketballGroup;

public interface BasketballGroupRepository extends JpaRepository<BasketballGroup, Long> {
    
    public BasketballGroup findByUuid(UUID uuid); 

}