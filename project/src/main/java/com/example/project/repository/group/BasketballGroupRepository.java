package com.example.project.repository.group;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.project.model.group.BasketballGroup;

@Repository
public interface BasketballGroupRepository extends JpaRepository<BasketballGroup, Long> {
    
    public BasketballGroup findByUuid(UUID uuid); 

}