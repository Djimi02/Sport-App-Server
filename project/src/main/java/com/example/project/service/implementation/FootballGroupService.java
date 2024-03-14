package com.example.project.service.implementation;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.example.project.model.User;
import com.example.project.model.game.FootballGame;
import com.example.project.model.group.FootballGroup;
import com.example.project.model.member.FootballMember;
import com.example.project.repository.UserRepository;
import com.example.project.repository.game.FootballGameRepository;
import com.example.project.repository.group.FootballGroupRepository;
import com.example.project.repository.member.FootballMemberRepository;
import com.example.project.request.AddNewFootballGameRequest;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FootballGroupService {

    private FootballGroupRepository footballGroupRepository;
    private FootballMemberRepository footballMemberRepository;
    private UserRepository userRepository;
    private FootballGameRepository footballGameRepository;

    public FootballGroup saveFootballGroup(FootballGroup footballGroup) {
        return footballGroupRepository.save(footballGroup);
    }

    public FootballGroup saveFootballGroup(String name, Long userID) {
        FootballGroup group = new FootballGroup(name);
        FootballGroup savedGroup = footballGroupRepository.save(group);

        User user = userRepository.findById(userID)
            .orElseThrow(() -> new IllegalArgumentException("User with id= " + userID + " does not exist!"));

        FootballMember newMember = new FootballMember(user.getUserName(), savedGroup);
        newMember.setUser(user);
        newMember = footballMemberRepository.save(newMember);

        savedGroup.addMember(newMember);

        return savedGroup;
    }

    public FootballGroup findFootballGroup(Long groupID) {
        return  footballGroupRepository.findById(groupID)
            .orElseThrow(() -> new IllegalArgumentException("Group with id = " + groupID + " does not exist!"));
    }

    @Transactional
    public FootballGroup updateFootballGroupName(Long groupID, String newName) {
        FootballGroup footballGroup = footballGroupRepository.findById(groupID)
            .orElseThrow(() -> new IllegalArgumentException("Group with id = " + groupID + " does not exist!"));

        footballGroup.setName(newName);

        return footballGroup;
    }

    
    /* =================================================================================================================== */


    @Transactional
    public FootballMember createAndAddMemberToGroup(Long groupID, String memberNickname) {
        FootballGroup footballGroup = footballGroupRepository.findById(groupID)
            .orElseThrow(() -> new IllegalArgumentException("Group with id = " + groupID + " does not exist!"));
        FootballMember newMember = new FootballMember(memberNickname, footballGroup);
        newMember = footballMemberRepository.save(newMember);
        newMember.setGroup(null);

        return newMember;
    }

    @Transactional
    public FootballGroup removeMemberFromGroup(Long groupID, FootballMember member) {
        FootballGroup footballGroup = footballGroupRepository.findById(groupID)
            .orElseThrow(() -> new IllegalArgumentException("Group with id = " + groupID + " does not exist!"));
        footballGroup.removeMember(member);

        footballMemberRepository.deleteById(member.getId());

        return footballGroup;
    }

    @Transactional
    public FootballGroup removeMemberFromGroup(Long groupID, Long memberID) {
        FootballGroup footballGroup = footballGroupRepository.findById(groupID)
            .orElseThrow(() -> new IllegalArgumentException("Group with id = " + groupID + " does not exist!"));
        footballGroup.removeMember(memberID);
        
        footballMemberRepository.deleteById(memberID);

        return footballGroup;
    }

    @Transactional
    public void addNewGame(AddNewFootballGameRequest request) {

        // Retrieve group
        FootballGroup group = footballGroupRepository.findById(request.getGroupID())
            .orElseThrow(() -> new IllegalArgumentException("Group with id = " + request.getGroupID() + " does not exist!"));

        // Save game member stats
        for (FootballMember member : request.getMembersGameStats()) {
            member.setGroup(null);
            member = footballMemberRepository.save(member);
        }

        // Create new game
        FootballGame newGame = new FootballGame(new Date(), group);
        newGame.setVictory(request.getVictory());
        newGame.setMembers(request.getMembersGameStats());

        footballGameRepository.save(newGame);

        // Update group members with the new stats
        for (FootballMember footballMember : request.getUpdatedMembers()) {
            FootballMember dbMember = footballMemberRepository.findById(footballMember.getId())
            .orElseThrow(() -> new IllegalArgumentException("Member with id = " + footballMember.getId() + " does not exists!"));

            dbMember.setGoals(footballMember.getGoals());
            dbMember.setAssists(footballMember.getAssists());
            dbMember.setSaves(footballMember.getSaves());
            dbMember.setFouls(footballMember.getFouls());
        }
    }
}