package com.example.project.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.project.model.User;
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
    public void saveGroupTest2() {
        String name = "Mosoma";
        Long userID = 1l;
        User user = userRepository.findById(userID).get();
        FootballGroup footballGroup = new FootballGroup(name);
        footballGroupService.saveFootballGroup(footballGroup, user);
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
    public void removeMemberFromGroupTest1() {
        Long memberID = 16l;
        Long groupID = 1l;
        FootballMember member = footballMemberRepository.findById(memberID).get();
        footballGroupService.removeMemberFromGroup(groupID, member);
    }

    @Test
    public void removeMemberFromGroupTest2() {
        Long memberID = 17l;
        Long groupID = 1l;
        footballGroupService.removeMemberFromGroup(groupID, memberID);
    }
}