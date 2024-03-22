package com.example.project.service.implementation;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

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
        newMember.setIsAdmin(true);
        newMember = footballMemberRepository.save(newMember);

        savedGroup.addMember(newMember);

        return savedGroup;
    }

    public FootballGroup findFootballGroup(Long groupID) {
        return footballGroupRepository.findById(groupID)
                .orElseThrow(() -> new IllegalArgumentException("Group with id = " + groupID + " does not exist!"));
    }

    @Transactional
    public FootballGroup updateFootballGroupName(Long groupID, String newName) {
        FootballGroup footballGroup = footballGroupRepository.findById(groupID)
                .orElseThrow(() -> new IllegalArgumentException("Group with id = " + groupID + " does not exist!"));

        footballGroup.setName(newName);

        return footballGroup;
    }

    /*
     * =============================================================================
     * ======================================
     */

    @Transactional
    public FootballMember createAndAddMemberToGroup(Long groupID, String memberNickname) {
        FootballGroup footballGroup = footballGroupRepository.findById(groupID)
                .orElseThrow(() -> new IllegalArgumentException("Group with id = " + groupID + " does not exist!"));

        if (footballMemberRepository.existsByNameAndGroup(memberNickname, groupID)) {
            throw new IllegalArgumentException("Member with name = " + memberNickname + " already exists in the group!");
        }

        FootballMember newMember = new FootballMember(memberNickname, footballGroup);
        newMember = footballMemberRepository.save(newMember);

        return newMember;
    }

    public void removeMemberFromGroup(Long groupID, Long memberID) {
        footballGroupRepository.findById(groupID)
                .orElseThrow(() -> new IllegalArgumentException("Group with id = " + groupID + " does not exist!"));

        footballMemberRepository.findById(memberID)
            .orElseThrow(() -> new IllegalArgumentException("Member with id = " + memberID + " does not exist!"));

        footballMemberRepository.deleteById(memberID);
    }

    @Transactional
    public FootballGame addNewGame(AddNewFootballGameRequest request) {

        // Retrieve group
        FootballGroup group = footballGroupRepository.findById(request.getGroupID())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Group with id = " + request.getGroupID() + " does not exist!"));

        // Save game member stats
        for (FootballMember member : request.getMembersGameStats()) {
            member.setGroup(null);
            member = footballMemberRepository.save(member);
        }

        // Create new game
        Calendar calendar = Calendar.getInstance();
        FootballGame newGame =
        new FootballGame(LocalDate.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.DAY_OF_MONTH)), group);
        newGame.setVictory(request.getVictory());
        newGame.setMembers(request.getMembersGameStats());
        newGame.setResults(createResults(request.getMembersGameStats()));

        newGame = footballGameRepository.save(newGame);

        // Update group members with the new stats
        for (FootballMember footballMember : request.getUpdatedMembers()) {
            FootballMember dbMember = footballMemberRepository.findById(footballMember.getId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Member with id = " + footballMember.getId() + " does not exists!"));

            dbMember.setGoals(footballMember.getGoals());
            dbMember.setAssists(footballMember.getAssists());
            dbMember.setSaves(footballMember.getSaves());
            dbMember.setFouls(footballMember.getFouls());
            dbMember.setWins(footballMember.getWins());
            dbMember.setLoses(footballMember.getLoses());
            dbMember.setDraws(footballMember.getDraws());
        }

        return newGame;
    }

    private String createResults(List<FootballMember> membersGameStats) {
        StringBuilder builder = new StringBuilder();
        int team1Score = 0;
        int team2Score = 0;
        for (FootballMember member : membersGameStats) {
            if (member.getIsPartOfTeam1()) {
                team1Score += member.getGoals();
            } else {
                team2Score += member.getGoals();
            }
        }

        builder.append(team1Score);
        builder.append(":");
        builder.append(team2Score);
        return builder.toString();
    }

    public List<FootballMember> getGameStats(Long gameID) {
        Optional<FootballGame> game = footballGameRepository.findById(gameID);
        if (game.isEmpty()) {
            throw new IllegalAccessError("Game with id = " + gameID + " does not exists!");
        }
        return footballGameRepository.getGameStats(gameID);
    }
}