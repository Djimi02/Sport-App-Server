package com.example.project.repository.group;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.project.model.group.FootballGroup;

@Repository
public interface FootballGroupRepository extends JpaRepository<FootballGroup, Long> {
    
    public FootballGroup findByUuid(UUID uuid); 

}