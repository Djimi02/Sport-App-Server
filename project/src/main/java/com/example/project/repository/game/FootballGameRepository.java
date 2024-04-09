package com.example.project.repository.game;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.project.model.game.FootballGame;
import com.example.project.model.stats.FBStats;

@Repository
public interface FootballGameRepository extends JpaRepository<FootballGame, Long> {

    @Query("SELECT s FROM FBStats s JOIN s.game g WHERE g.id = :gameId")
    List<FBStats> getGameStats(@Param("gameId") Long gameId);
    
}