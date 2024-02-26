package com.example.project.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.project.service.implementation.FootballMemberService;

@SpringBootTest
public class FootballMemberServiceTest {

    @Autowired
    private FootballMemberService footballMemberService;

    @Test
    public void saveFootballMemberTest() {
        String nickname = "jimi2";
        Long groupID = 1l;
        footballMemberService.saveFootballMember(nickname, groupID);
    }

    @Test
    public void updateNicknameTest() {
        String nickname = "jimi2";
        Long id = 18l;
        footballMemberService.updateNickname(id, nickname);
    }

    @Test
    public void updateUserTest() {
        Long userID = 1l;
        Long id = 1l;
        footballMemberService.updateUser(id, userID);
    }

    // @Test
    // public void deleteMemberTest() {
    //     Long id = 18l;
    //     footballMemberService.deleteMember(id);
    // }
}
