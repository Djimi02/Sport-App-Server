package com.example.project.service;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.project.model.group.FootballGroup;
import com.example.project.model.stats.FBStats;
import com.example.project.repository.UserRepository;
import com.example.project.repository.member.FootballMemberRepository;
import com.example.project.service.implementation.FootballService;

@SpringBootTest
public class FootballServiceTest {

    @Autowired
    private FootballService footballGroupService;

    @Autowired
    private FootballMemberRepository footballMemberRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void saveGroup() {
        String groupName = "test name";
        Long userID = 2l;
        footballGroupService.saveGroup(groupName, userID);
    }

    @Test
    public void getGroup() {
        Long groupID = 1l;
        FootballGroup footballGroupByID = footballGroupService.findGroupByID(groupID);
    }

    @Test
    public void updateGroupNameTest() {
        Long id = 1l;
        footballGroupService.updateGroupName(id, "mosoma2");
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
        footballGroupService.removeMemberFromGroup(memberID);
    }

    @Test
    public void getGameStatsTest() {
        List<FBStats> stats = footballGroupService.getGameStats(1l);
        for (FBStats stat : stats) {
            System.out.println(stat.getGoals());
        }
    }

    @Test
    public void deleteGroup() {
        footballGroupService.deleteGroup(1);
    }

    @Test
    public void deleteGame() {
        footballGroupService.deleteGame(4L);
    }

    @Test
    public void joinGroupAsExistingMember() {
        long userID = 1l;
        long memberID = 4l;
        footballGroupService.joinGroupAsExistingMember(userID, memberID);
    }

    @Test
    public void joinGroupAsNewMember() {
        long userID = 1l;
        long groupID = 2l;
        footballGroupService.joinGroupAsNewMember(userID, groupID);
    }
}