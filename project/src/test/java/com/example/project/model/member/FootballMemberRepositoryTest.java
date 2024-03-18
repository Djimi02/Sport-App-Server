package com.example.project.model.member;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.project.model.User;
import com.example.project.model.group.FootballGroup;
import com.example.project.repository.UserRepository;
import com.example.project.repository.group.FootballGroupRepository;
import com.example.project.repository.member.FootballMemberRepository;

@SpringBootTest
public class FootballMemberRepositoryTest {
    @Autowired
    private FootballMemberRepository footballMemberRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FootballGroupRepository groupRepository;

    @Test
    public void saveMemberTest() {
        User user = userRepository.findById(1l).get();
        FootballGroup group = groupRepository.findById(1l).get();
        String username = "mishel";
        FootballMember member = new FootballMember(username, group);
        member.setUser(user);
        footballMemberRepository.save(member);
    }

    @Test
    public void retrieveMemberTest() {
        FootballMember footballMember = footballMemberRepository.findById(3l).get();
        assertTrue(footballMember.getGroup() instanceof FootballGroup);
    }

    @Test
    public void existsByNameAndGroupTest() {
        assertTrue(footballMemberRepository.existsByNameAndGroup("djimi", 1l));
        assertFalse(footballMemberRepository.existsByNameAndGroup("djimi", 2l));
    }
}