package com.example.project.service.implementation;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.project.model.User;
import com.example.project.model.group.FootballGroup;
import com.example.project.model.member.FootballMember;
import com.example.project.repository.UserRepository;
import com.example.project.repository.group.FootballGroupRepository;
import com.example.project.repository.member.FootballMemberRepository;

import jakarta.transaction.Transactional;

@Service
public class FootballMemberService {

    private FootballMemberRepository footballMemberRepository;
    private FootballGroupRepository footballGroupRepository;
    private UserRepository userRepository;

    public FootballMemberService(FootballMemberRepository footballMemberRepository, FootballGroupRepository footballGroupRepository,
        UserRepository userRepository) {
        this.footballMemberRepository = footballMemberRepository;
        this.footballGroupRepository = footballGroupRepository;
        this.userRepository = userRepository;
    }

    public FootballMember saveFootballMember(FootballMember footballMember) {
        return footballMemberRepository.save(footballMember);
    }

    public FootballMember saveFootballMember(String nickname, Long groupID) {
        FootballGroup footballGroup = footballGroupRepository.findById(groupID)
            .orElseThrow(() -> new IllegalArgumentException("Group with id = " + groupID + " does not exist."));

        FootballMember footballMember = new FootballMember(nickname, footballGroup);

        return footballMemberRepository.save(footballMember);
    }

    @Transactional
    public FootballMember updateNickname(Long memberID, String nickname) {
        FootballMember footballMember = footballMemberRepository.findById(memberID)
            .orElseThrow(() -> new IllegalArgumentException("Member with id = " + memberID + " does not exist."));

        footballMember.setNickname(nickname);

        return footballMember;
    }

    @Transactional
    public FootballMember updateUser(Long memberID, Long userID) {
        FootballMember footballMember = footballMemberRepository.findById(memberID)
            .orElseThrow(() -> new IllegalArgumentException("Member with id = " + memberID + " does not exist."));

        User user = userRepository.findById(userID).orElseThrow(() -> new IllegalArgumentException("User with id = " + userID + " does not exist."));

        footballMember.setUser(user);

        return footballMember;
    }

    // @Transactional
    // public void deleteMember(Long memberID) {
    //     Optional<FootballMember> fmOPT = footballMemberRepository.findById(memberID);
    //     if (fmOPT.isEmpty()) {
    //         throw new IllegalArgumentException("Member with id = " + memberID + " does not exist.");
    //     }

    //     List<FootballGame> games = fmOPT.get().getGames();
    //     for (FootballGame footballGame : games) { // remove member reference from games
    //         footballGame.removeMember(memberID);
    //     }

    //     footballMemberRepository.deleteById(memberID);
    // }
    
}