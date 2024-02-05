package com.example.project.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.project.model.User;
import com.example.project.model.group.FootballGroup;
import com.example.project.model.member.FootballMember;
import com.example.project.repository.group.FootballGroupRepository;
import com.example.project.repository.member.FootballMemberRepository;

import jakarta.transaction.Transactional;

@Service
public class FootballGroupService {

    private FootballGroupRepository footballGroupRepository;
    private FootballMemberRepository footballMemberRepository;

    public FootballGroupService(FootballGroupRepository footballGroupRepository, FootballMemberRepository footballMemberRepository) {
        this.footballGroupRepository = footballGroupRepository;
        this.footballMemberRepository = footballMemberRepository;
    }

    public FootballGroup saveFootballGroup(FootballGroup footballGroup) {
        return footballGroupRepository.save(footballGroup);
    }

    public FootballGroup saveFootballGroup(FootballGroup footballGroup, User user) {
        FootballGroup savedGroup = footballGroupRepository.save(footballGroup);
        FootballMember newMember = new FootballMember(user.getUserName(), savedGroup);
        newMember.setUser(user);
        newMember = footballMemberRepository.save(newMember);
        savedGroup.addMember(newMember);
        return savedGroup;
    }

    public FootballGroup findFootballGroup(Long groupID) {
        Optional<FootballGroup> fgOPT = footballGroupRepository.findById(groupID);
        if (fgOPT.isEmpty()) {
            throw new IllegalArgumentException("Group with id = " + groupID + " does not exist!");
        }
        return fgOPT.get();
    }

    @Transactional
    public FootballGroup updateFootballGroupName(Long groupID, String newName) {
        Optional<FootballGroup> fgOPT = footballGroupRepository.findById(groupID);
        if (fgOPT.isEmpty()) {
            throw new IllegalArgumentException("Group with id = " + groupID + " does not exist!");
        }

        FootballGroup footballGroup = fgOPT.get();
        footballGroup.setName(newName);

        return footballGroup;
    }

    @Transactional
    public FootballGroup createAndAddMemberToGroup(Long groupID, String memberNickname) {
        Optional<FootballGroup> fmOPT = footballGroupRepository.findById(groupID);
        if (fmOPT.isEmpty()) {
            throw new IllegalArgumentException("Group with id = " + groupID + " does not exist!");
        }
        FootballGroup group = fmOPT.get();
        FootballMember newMember = new FootballMember(memberNickname, group);
        newMember = footballMemberRepository.save(newMember);
        group.addMember(newMember);

        return group;
    }


    /* =================================================================================================================== */

    @Transactional
    public FootballGroup removeMemberFromGroup(Long groupID, FootballMember member) {
        Optional<FootballGroup> fmOPT = footballGroupRepository.findById(groupID);
        if (fmOPT.isEmpty()) {
            throw new IllegalArgumentException("Group with id = " + groupID + " does not exist!");
        }
        FootballGroup footballGroup = fmOPT.get();
        footballGroup.removeMember(member);

        footballMemberRepository.deleteById(member.getId());

        return footballGroup;
    }

    @Transactional
    public FootballGroup removeMemberFromGroup(Long groupID, Long memberID) {
        Optional<FootballGroup> fmOPT = footballGroupRepository.findById(groupID);
        if (fmOPT.isEmpty()) {
            throw new IllegalArgumentException("Group with id = " + groupID + " does not exist!");
        }
        FootballGroup footballGroup = fmOPT.get();
        footballGroup.removeMember(memberID);
        
        footballMemberRepository.deleteById(memberID);

        return footballGroup;
    }

}