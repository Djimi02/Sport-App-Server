package com.example.project.repository.game;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.project.model.game.FootballGame;
import com.example.project.model.group.FootballGroup;
import com.example.project.repository.group.FootballGroupRepository;

@SpringBootTest
public class FootballGameRepositoryTest {

    @Autowired
    private FootballGameRepository footballGameRepository;

    @Autowired 
    private FootballGroupRepository footballGroupRepository;

    @Test
    public void saveFootballGameTest() {
        String name = "football game";
        FootballGroup footballGroup = footballGroupRepository.findById(1l).get();
        FootballGame footballGame = new FootballGame(new Date(), footballGroup);
        footballGameRepository.save(footballGame);
    }
}
