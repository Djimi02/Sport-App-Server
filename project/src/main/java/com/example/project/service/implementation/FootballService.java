package com.example.project.service.implementation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.project.model.MemberRole;
import com.example.project.model.User;
import com.example.project.model.game.FootballGame;
import com.example.project.model.group.FootballGroup;
import com.example.project.model.member.FootballMember;
import com.example.project.model.stats.FBStats;
import com.example.project.repository.UserRepository;
import com.example.project.repository.game.FootballGameRepository;
import com.example.project.repository.group.FootballGroupRepository;
import com.example.project.repository.member.FootballMemberRepository;
import com.example.project.repository.stats.FBStatsRepository;
import com.example.project.request.addNewGameRequests.AddNewFBGameRequest;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FootballService {

    private FootballGroupRepository footballGroupRepository;
    private FootballMemberRepository footballMemberRepository;
    private UserRepository userRepository;
    private FootballGameRepository footballGameRepository;
    private FBStatsRepository fbStatsRepository;

    /* GROUP */

    @Transactional
    public FootballGroup saveFootballGroup(String name, Long userID) {
        FootballGroup group = new FootballGroup(name);
        FootballGroup savedGroup = footballGroupRepository.save(group);

        User user = userRepository.findById(userID)
                .orElseThrow(() -> new IllegalArgumentException("User with id= " + userID + " does not exist!"));

        FootballMember newMember = new FootballMember(user.getUserName(), savedGroup);
        newMember.setUser(user);
        newMember.setRole(MemberRole.GROUP_ADMIN);
        newMember.getStats().setMember(newMember);
        newMember.getStats().setMemberName(user.getUserName());
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

    @Transactional
    public void deleteFootballGroup(long groupID) {
        FootballGroup groupToBeDeleted = footballGroupRepository.findById(groupID)
                .orElseThrow(() -> new IllegalArgumentException("Group with id = " + groupID + " does not exist!"));

        for (FootballMember member : groupToBeDeleted.getMembers()) {
            // setting reference to stats null so it doesnt complain about constraints
            member.setStats(null);
            // deleting all stats refering to the member
            fbStatsRepository.deleteStatsMappingToMember(member.getId());
        }

        // this will delete the members and games also
        footballGroupRepository.deleteById(groupID);
    }

    /* Member */

    @Transactional
    public FootballMember createAndAddMemberToGroup(Long groupID, String memberNickname) {
        FootballGroup footballGroup = footballGroupRepository.findById(groupID)
                .orElseThrow(() -> new IllegalArgumentException("Group with id = " + groupID + " does not exist!"));

        if (footballMemberRepository.existsByNameAndGroup(memberNickname, groupID)) {
            throw new IllegalArgumentException(
                    "Member with name = " + memberNickname + " already exists in the group!");
        }

        FootballMember newMember = new FootballMember(memberNickname, footballGroup);
        newMember.setRole(MemberRole.MEMBER);
        newMember.getStats().setMember(newMember);
        newMember.getStats().setMemberName(memberNickname);
        newMember = footballMemberRepository.save(newMember);

        return newMember;
    }

    @Transactional
    public void removeMemberFromGroup(Long memberID) {
        footballMemberRepository.findById(memberID)
                .orElseThrow(() -> new IllegalArgumentException("Member with id = " + memberID + " does not exist!"));

        fbStatsRepository.setStatsMemberReferencesToNull(memberID);

        footballMemberRepository.deleteById(memberID);
    }

    @Transactional
    public void joinGroupAsExistingMember(long userID, long memberID) {
        User user = userRepository.findById(userID)
                .orElseThrow(() -> {
                    throw new IllegalArgumentException("User with id = " + userID + " does not exists!");
                });

        FootballMember member = footballMemberRepository.findById(memberID)
                .orElseThrow(() -> {
                    throw new IllegalArgumentException("Member with id = " + memberID + " does not exists!");
                });

        member.setUser(user);
    }

    public FootballMember joinGroupAsNewMember(long userID, long groupID) {
        User user = userRepository.findById(userID)
                .orElseThrow(() -> {
                    throw new IllegalArgumentException("User with id = " + userID + " does not exists!");
                });

        FootballGroup group = footballGroupRepository.findById(groupID)
                .orElseThrow(() -> {
                    throw new IllegalArgumentException("Member with id = " + groupID + " does not exists!");
                });

        FootballMember newMember = new FootballMember();
        newMember.setUser(user);
        newMember.setGroup(group);
        newMember.setNickname(user.getUserName());
        newMember.setRole(MemberRole.MEMBER);
        newMember.getStats().setMember(newMember);
        newMember.getStats().setMemberName(newMember.getNickname());
        newMember = footballMemberRepository.save(newMember);

        return newMember;
    }

    @Transactional
    public void setRoleToAdmin(long memberID) {
        FootballMember member = footballMemberRepository.findById(memberID)
            .orElseThrow(() -> new IllegalArgumentException("Member with id = " + memberID + " does not exist!"));

        member.setRole(MemberRole.GROUP_ADMIN);
    }

    @Transactional
    public void setRoleToGameMaker(long memberID) {
        FootballMember member = footballMemberRepository.findById(memberID)
            .orElseThrow(() -> new IllegalArgumentException("Member with id = " + memberID + " does not exist!"));

        member.setRole(MemberRole.GAME_MAKER);
    }

    @Transactional
    public void setRoleToMember(long memberID) {
        FootballMember member = footballMemberRepository.findById(memberID)
            .orElseThrow(() -> new IllegalArgumentException("Member with id = " + memberID + " does not exist!"));

        member.setRole(MemberRole.MEMBER);
    }

    /* Game */

    @Transactional
    public FootballGame addNewGame(AddNewFBGameRequest request) {

        // Retrieve group
        FootballGroup group = footballGroupRepository.findById(request.getGroupID())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Group with id = " + request.getGroupID() + " does not exist!"));

        // Create new game
        Calendar calendar = Calendar.getInstance();
        FootballGame newGame = new FootballGame(LocalDate.of(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH)), group);
        newGame.setResults(createResults(new ArrayList<>(request.getGameStats().values())));
        newGame = footballGameRepository.save(newGame);

        // Save game member stats and update the actual members with the new stats
        for (Long memberID : request.getGameStats().keySet()) {
            FBStats gameStats = request.getGameStats().get(memberID);

            FootballMember dbMember = footballMemberRepository.findById(memberID)
                    .orElseThrow(() -> new IllegalArgumentException("Such member does not exists!"));
            FBStats dbMemberStats = dbMember.getStats();

            dbMemberStats.setWins(dbMemberStats.getWins() + gameStats.getWins());
            dbMemberStats.setDraws(dbMemberStats.getDraws() + gameStats.getDraws());
            dbMemberStats.setLoses(dbMemberStats.getLoses() + gameStats.getLoses());
            dbMemberStats.setGoals(dbMemberStats.getGoals() + gameStats.getGoals());
            dbMemberStats.setAssists(dbMemberStats.getAssists() + gameStats.getAssists());
            dbMemberStats.setSaves(dbMemberStats.getSaves() + gameStats.getSaves());
            dbMemberStats.setFouls(dbMemberStats.getFouls() + gameStats.getFouls());

            gameStats.setMember(dbMember);
            gameStats.setGame(newGame);
            fbStatsRepository.save(gameStats);
        }

        return newGame;
    }

    private String createResults(List<FBStats> gameStats) {
        StringBuilder builder = new StringBuilder();
        int team1Score = 0;
        int team2Score = 0;
        for (FBStats stats : gameStats) {
            if (stats.getIsPartOfTeam1()) {
                team1Score += stats.getGoals();
            } else {
                team2Score += stats.getGoals();
            }
        }

        builder.append(team1Score);
        builder.append(":");
        builder.append(team2Score);
        return builder.toString();
    }

    public List<FBStats> getGameStats(Long gameID) {
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

        List<FBStats> gameStats = getGameStats(gameID);
        decreaseMemberStatsAfterGameDeleted(gameStats);

        // delete stats referencing to that game
        for (FBStats gameStat : gameStats) {
            fbStatsRepository.delete(gameStat);
        }

        footballGameRepository.delete(game);
    }

    private void decreaseMemberStatsAfterGameDeleted(List<FBStats> gameStats) {
        for (FBStats gameStat : gameStats) {
            if (gameStat.getMember() == null) {
                continue;
            }
            FBStats associatedGMemberStats = gameStat.getMember().getStats();

            associatedGMemberStats.setWins(associatedGMemberStats.getWins() - gameStat.getWins());
            associatedGMemberStats.setDraws(associatedGMemberStats.getDraws() - gameStat.getDraws());
            associatedGMemberStats.setLoses(associatedGMemberStats.getLoses() - gameStat.getLoses());
            associatedGMemberStats.setGoals(associatedGMemberStats.getGoals() - gameStat.getGoals());
            associatedGMemberStats.setAssists(associatedGMemberStats.getAssists() - gameStat.getAssists());
            associatedGMemberStats.setSaves(associatedGMemberStats.getSaves() - gameStat.getSaves());
            associatedGMemberStats.setFouls(associatedGMemberStats.getFouls() - gameStat.getFouls());
        }
    }
}