package com.example.project.repository.game;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.project.model.game.FootballGame;
import com.example.project.model.group.FootballGroup;
import com.example.project.model.member.FootballMember;
import com.example.project.repository.group.FootballGroupRepository;
import com.example.project.repository.member.FootballMemberRepository;

@SpringBootTest
public class FootballGameRepositoryTest {

    @Autowired
    private FootballGameRepository footballGameRepository;

    @Autowired 
    private FootballGroupRepository footballGroupRepository;

    @Autowired
    private FootballMemberRepository footballMemberRepository;

    @Test
    public void saveFootballGameTest() {
        FootballGroup footballGroup = footballGroupRepository.findById(1l).get();
        FootballGame footballGame = new FootballGame(new Date(), footballGroup);
        Long memberID = 18l;
        FootballMember footballMember = footballMemberRepository.findById(memberID).get();
        footballGame.addMember(footballMember);
        footballGameRepository.save(footballGame);
    }

    @Test
    public void retrieveFootballGameTest() {
        FootballGame footballGame = footballGameRepository.findById(1l).get();
        System.out.println("NUM OF MEMBERS = " + footballGame.getMembers().size());
    }
}