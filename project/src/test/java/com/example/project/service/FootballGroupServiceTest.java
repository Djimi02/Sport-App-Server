package com.example.project.service;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.project.model.group.FootballGroup;
import com.example.project.model.member.FootballMember;
import com.example.project.repository.UserRepository;
import com.example.project.repository.member.FootballMemberRepository;
import com.example.project.service.implementation.FootballGroupService;

@SpringBootTest
public class FootballGroupServiceTest {

    @Autowired
    private FootballGroupService footballGroupService;

    @Autowired
    private FootballMemberRepository footballMemberRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void saveGroupTest1() {
        String name = "Mosoma";
        FootballGroup footballGroup = new FootballGroup(name);
        footballGroupService.saveFootballGroup(footballGroup);
    }

    @Test
    public void updateGroupNameTest() {
        Long id = 1l;
        footballGroupService.updateFootballGroupName(id, "mosoma2");
    }

    @Test
    public void createAndAddMemberToGroupTest() {
        Long groupID = 1l;
        String memberNickname = "jimi";
        footballGroupService.createAndAddMemberToGroup(groupID, memberNickname);

        // FootballGroup footballGroup =
        // footballGroupService.findFootballGroup(groupID);
        // assertTrue(footballGroup.getMembers().size() == 1);
    }

    @Test
    public void removeMemberFromGroupTest2() {
        Long memberID = 2l;
        Long groupID = 1l;
        footballGroupService.removeMemberFromGroup(groupID, memberID);
    }

    @Test
    public void getGameStatsTest() {
        List<FootballMember> stats = footballGroupService.getGameStats(1l);
        for (FootballMember footballMember : stats) {
            System.out.println(footballMember.toString());
        }
    }

    @Test
    public void deleteGroup() {
        footballGroupService.deleteFootballGroup(6);
    }
}