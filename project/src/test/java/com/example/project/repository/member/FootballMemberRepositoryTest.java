package com.example.project.repository.member;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.project.model.User;
import com.example.project.model.group.FootballGroup;
import com.example.project.model.member.FootballMember;
import com.example.project.repository.UserRepository;
import com.example.project.repository.group.FootballGroupRepository;

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
        FootballMember member = new FootballMember();
        member.setNickname(username);
        member.setGroup(group);
        member.setUser(user);
        footballMemberRepository.save(member);
    }

    @Test
    public void retrieveMemberTest() {
        FootballMember footballMember = footballMemberRepository.findById(1l).get();
        System.out.println(footballMember.getNickname());
    }

    @Test
    public void getAllMembersByGroupID() {
        List<FootballMember> footballMembers = footballMemberRepository.getAllMembersByGroupID(1l);
        System.out.println(footballMembers.size());
    }

    @Test
    public void existsByNameAndGroupTest() {
        assertTrue(footballMemberRepository.existsByNameAndGroup("djimi", 1l));
        assertFalse(footballMemberRepository.existsByNameAndGroup("djimi", 2l));
    }

    @Test
    public void getByNameAndGroup() {
        String name = "djimi";
        long groupID = 9l;
        Optional<FootballMember> memberOpt = footballMemberRepository.getByNameAndGroup(name, groupID);
        // System.out.println("LIST = " + memberOpt);
        if (memberOpt.isEmpty()) {
            assertTrue(false);
        } else {
            if (memberOpt.get().getNickname().equals(name)) {
                assertTrue(true);
            } else {
                assertTrue(false);
            }
        }
    }
}