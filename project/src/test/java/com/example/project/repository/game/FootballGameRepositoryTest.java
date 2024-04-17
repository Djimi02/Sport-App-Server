package com.example.project.repository.game;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.project.model.stats.FBStats;

@SpringBootTest
public class FootballGameRepositoryTest {

    @Autowired
    private FootballGameRepository footballGameRepository;


    @Test
    public void getGameStatsTest() {
        List<FBStats> stats = footballGameRepository.getGameStats(7l);
        for (FBStats stat : stats) {
            System.out.println(stat.getGoals());
        }
    }
}