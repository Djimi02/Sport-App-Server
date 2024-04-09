package com.example.project.repository.game;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.project.model.game.FootballGame;
import com.example.project.model.member.FootballMember;

@Repository
public interface FootballGameRepository extends JpaRepository<FootballGame, Long> {

    @Query("SELECT fm FROM FootballMember fm JOIN fm.game fg WHERE fg IS NOT NULL AND fg.id = :gameId")
    List<FootballMember> getGameStats(@Param("gameId") Long gameId);
    
}