package com.example.project.repository.game;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.project.model.game.FootballGame;

@Repository
public interface FootballGameRepository extends JpaRepository<FootballGame, Long> {
    
}
