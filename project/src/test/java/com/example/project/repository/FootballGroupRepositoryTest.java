package com.example.project.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.project.model.group.FootballGroup;
import com.example.project.repository.group.FootballGroupRepository;

@SpringBootTest
public class FootballGroupRepositoryTest {

    @Autowired
    private FootballGroupRepository footballGroupRepository;

    @Test
    public void saveFootballGroupTest() {
        String name = "mosoma";
        FootballGroup footballGroup = new FootballGroup(name);
        footballGroupRepository.save(footballGroup);
    }

    @Test
    public void retrieveFootballGroupTest() {
        FootballGroup footballGroup = footballGroupRepository.findById(1l).get();
        assertEquals(1, footballGroup.getMembers().size());
    }

}