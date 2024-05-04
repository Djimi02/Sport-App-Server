package com.example.project.repository.game;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.project.model.game.BasketballGame;
import com.example.project.model.stats.BBStats;

public interface BasketballGameRepository extends JpaRepository<BasketballGame, Long> {
    
    @Query("SELECT s FROM BBStats s JOIN s.game g WHERE g.id = :gameId")
    List<BBStats> getGameStats(@Param("gameId") Long gameId);

}