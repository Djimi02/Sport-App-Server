package com.example.project.repository.stats;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.project.model.stats.FBStats;

@Repository
public interface FBStatsRepository extends JpaRepository<FBStats, Long> {
    
}
