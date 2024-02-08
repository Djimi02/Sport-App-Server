package com.example.project.service.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.project.model.User;
import com.example.project.model.game.FootballGame;
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

        Optional<FootballGroup> fgOPT = footballGroupRepository.findById(groupID);
        if (fgOPT.isEmpty()) {
            throw new IllegalArgumentException("Group with id = " + groupID + " does not exist.");
        }
        FootballGroup footballGroup = fgOPT.get();
        FootballMember footballMember = new FootballMember(nickname, footballGroup);

        footballMember = footballMemberRepository.save(footballMember);

        return footballMember;
    }

    @Transactional
    public FootballMember updateNickname(Long memberID, String nickname) {
        Optional<FootballMember> fmOPT = footballMemberRepository.findById(memberID);
        if (fmOPT.isEmpty()) {
            throw new IllegalArgumentException("Member with id = " + memberID + " does not exist.");
        }

        FootballMember footballMember = fmOPT.get();
        footballMember.setNickname(nickname);

        return footballMember;
    }

    @Transactional
    public FootballMember updateUser(Long memberID, Long userID) {
        Optional<FootballMember> fmOPT = footballMemberRepository.findById(memberID);
        if (fmOPT.isEmpty()) {
            throw new IllegalArgumentException("Member with id = " + memberID + " does not exist.");
        }

        Optional<User> uOPT = userRepository.findById(userID);
        if (uOPT.isEmpty()) {
            throw new IllegalArgumentException("User with id = " + userID + " does not exist.");
        }

        FootballMember footballMember = fmOPT.get();
        User user = uOPT.get();

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