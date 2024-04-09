package com.example.project.service.implementation;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
public class FootballService {

    private FootballGroupRepository footballGroupRepository;
    private FootballMemberRepository footballMemberRepository;
    private UserRepository userRepository;
    private FootballGameRepository footballGameRepository;

    /* GROUP */

    @Transactional
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

    public FootballGroup findFootballGroupByID(Long groupID) {
        return footballGroupRepository.findById(groupID)
                .orElseThrow(() -> new IllegalArgumentException("Group with id = " + groupID + " does not exist!"));
    }

    public FootballGroup findGroupByUUID(String uuidStr) {
        UUID uuid = UUID.fromString(uuidStr);
        return footballGroupRepository.findByUuid(uuid);
    }

    @Transactional
    public FootballGroup updateFootballGroupName(Long groupID, String newName) {
        FootballGroup footballGroup = footballGroupRepository.findById(groupID)
                .orElseThrow(() -> new IllegalArgumentException("Group with id = " + groupID + " does not exist!"));

        footballGroup.setName(newName);

        return footballGroup;
    }

    public void deleteFootballGroup(long groupID) {
        footballGroupRepository.findById(groupID)
            	.orElseThrow(() -> new IllegalArgumentException("Group with id = " + groupID + " does not exist!"));      
        
        footballGroupRepository.deleteById(groupID);
    }

    /* Member */

    @Transactional
    public FootballMember createAndAddMemberToGroup(Long groupID, String memberNickname) {
        FootballGroup footballGroup = footballGroupRepository.findById(groupID)
                .orElseThrow(() -> new IllegalArgumentException("Group with id = " + groupID + " does not exist!"));

        if (footballMemberRepository.existsByNameAndGroup(memberNickname, groupID)) {
            throw new IllegalArgumentException("Member with name = " + memberNickname + " already exists in the group!");
        }

        FootballMember newMember = new FootballMember(memberNickname, footballGroup);
        newMember.setIsAdmin(false);
        newMember = footballMemberRepository.save(newMember);

        return newMember;
    }

    public void removeMemberFromGroup(Long memberID) {
        footballMemberRepository.findById(memberID)
            .orElseThrow(() -> new IllegalArgumentException("Member with id = " + memberID + " does not exist!"));

        footballMemberRepository.deleteById(memberID);
    }

    @Transactional
    public void joinGroupAsExistingMember(long userID, long memberID) {
        User user = userRepository.findById(userID)
            .orElseThrow(() -> {throw new IllegalArgumentException("User with id = " + userID + " does not exists!");});

        FootballMember member = footballMemberRepository.findById(memberID)
            .orElseThrow(() -> {throw new IllegalArgumentException("Member with id = " + memberID + " does not exists!");});

        member.setUser(user);
    }

    public FootballMember joinGroupAsNewMember(long userID, long groupID) {
        User user = userRepository.findById(userID)
            .orElseThrow(() -> {throw new IllegalArgumentException("User with id = " + userID + " does not exists!");});

        FootballGroup group = footballGroupRepository.findById(groupID)
            .orElseThrow(() -> {throw new IllegalArgumentException("Member with id = " + groupID + " does not exists!");});

        FootballMember newMember = new FootballMember();
        newMember.setUser(user);
        newMember.setGroup(group);
        newMember.setNickname(user.getUserName());
        newMember = footballMemberRepository.save(newMember);

        return newMember;
    }

    /* Game */

    @Transactional
    public FootballGame addNewGame(AddNewFootballGameRequest request) {

        // Retrieve group
        FootballGroup group = footballGroupRepository.findById(request.getGroupID())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Group with id = " + request.getGroupID() + " does not exist!"));

        // Create new game
        Calendar calendar = Calendar.getInstance();
        FootballGame newGame =
        new FootballGame(LocalDate.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.DAY_OF_MONTH)), group);
        newGame.setVictory(request.getVictory());
        newGame.setResults(createResults(request.getMembersGameStats()));

        // Save game member stats and update the actual members with the new stats
        for (FootballMember stats : request.getMembersGameStats()) {
            stats.setGroup(null);
            stats.setGame(newGame);
            stats = footballMemberRepository.save(stats); // save

            FootballMember dbMember = footballMemberRepository.getByNameAndGroup(stats.getNickname(), request.getGroupID())
                .orElseThrow(() -> new IllegalArgumentException("Such member does not exists!"));
            dbMember.setGoals(dbMember.getGoals() + stats.getGoals());
            dbMember.setAssists(dbMember.getAssists() + stats.getAssists());
            dbMember.setSaves(dbMember.getSaves() + stats.getSaves());
            dbMember.setFouls(dbMember.getFouls() + stats.getFouls());
            if (request.getVictory() == 0) { // draw
                dbMember.setDraws(dbMember.getDraws() + 1);
            } else if ((stats.getIsPartOfTeam1() && request.getVictory() == -1) || (!stats.getIsPartOfTeam1() && request.getVictory() == 1)) { // player won
                dbMember.setWins(dbMember.getWins() + 1);
            } else if ((stats.getIsPartOfTeam1() && request.getVictory() == 1) || (!stats.getIsPartOfTeam1() && request.getVictory() == -1)) { // player lost
                dbMember.setLoses(dbMember.getLoses() + 1);
            }
        }

        return footballGameRepository.save(newGame);
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

    @Transactional
    public void deleteFootballGame(Long gameID) {
        FootballGame game = footballGameRepository.findById(gameID)
            .orElseThrow(() -> new IllegalAccessError("Game with id = " + gameID + " does not exists!"));

        List<FootballMember> gameStats = getGameStats(gameID);
        decreaseMemberStatsAfterGameDeleted(game, gameStats);

        for (FootballMember gameStat : gameStats) {
            footballMemberRepository.delete(gameStat);
        }

        footballGameRepository.delete(game);
    }

    private void decreaseMemberStatsAfterGameDeleted(FootballGame game, List<FootballMember> members) {
        for (int i = 0; i < members.size(); i++) {
            FootballMember associatedGMember = getGroupMemberByNickname(game.getGroup(), members.get(i).getNickname());
            if (associatedGMember == null) {
                return;
            }
            associatedGMember.setGoals(associatedGMember.getGoals() - members.get(i).getGoals());
            associatedGMember.setAssists(associatedGMember.getAssists() - members.get(i).getAssists());
            associatedGMember.setSaves(associatedGMember.getSaves() - members.get(i).getSaves());
            associatedGMember.setFouls(associatedGMember.getFouls() - members.get(i).getFouls());
            if (game.getVictory() == 0) { // draw
                associatedGMember.setDraws(associatedGMember.getDraws()-1);
            } else if ((game.getVictory() == -1 && members.get(i).getIsPartOfTeam1() ||
                    (game.getVictory() == 1 && !members.get(i).getIsPartOfTeam1()))) { // player had won
                associatedGMember.setWins(associatedGMember.getWins()-1);
            } else if ((game.getVictory() == 1 && members.get(i).getIsPartOfTeam1() ||
                    (game.getVictory() == -1 && !members.get(i).getIsPartOfTeam1()))) { // player had lost
                associatedGMember.setLoses(associatedGMember.getLoses()-1);
            }
        }
    }

    private FootballMember getGroupMemberByNickname(FootballGroup group, String nickname) {
        for (FootballMember member : group.getMembers()) {
            if (member.getNickname().equals(nickname)) {
                return member;
            }
        }
        return null;
    }
}