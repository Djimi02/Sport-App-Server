package com.example.project.service.implementation;

import java.util.ArrayList;
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

    private UserRepository userRepository;
    private FootballGroupRepository groupRepository;
    private FootballMemberRepository memberRepository;
    private FootballGameRepository gameRepository;
    private FBStatsRepository statsRepository;

    /* GROUP */

    @Transactional
    public FootballGroup saveGroup(String name, Long userID) {
        FootballGroup group = new FootballGroup();
        group.setName(name);
        FootballGroup savedGroup = groupRepository.save(group);

        User user = userRepository.findById(userID)
                .orElseThrow(() -> new IllegalArgumentException("User with id= " + userID + " does not exist!"));

        FootballMember newMember = new FootballMember();
        newMember.setNickname(user.getUserName());
        newMember.setGroup(savedGroup);
        newMember.setUser(user);
        newMember.setRole(MemberRole.GROUP_ADMIN);
        newMember.getStats().setMember(newMember);
        newMember.getStats().setMemberName(user.getUserName());
        newMember = memberRepository.save(newMember);

        savedGroup.addMember(newMember);

        return savedGroup;
    }

    public FootballGroup findGroupByID(Long groupID) {
        return groupRepository.findById(groupID)
                .orElseThrow(() -> new IllegalArgumentException("Group with id = " + groupID + " does not exist!"));
    }

    public FootballGroup findGroupByUUID(String uuidStr) {
        UUID uuid = UUID.fromString(uuidStr);
        return groupRepository.findByUuid(uuid);
    }

    @Transactional
    public void updateGroupName(Long groupID, String newName) {
        FootballGroup footballGroup = groupRepository.findById(groupID)
                .orElseThrow(() -> new IllegalArgumentException("Group with id = " + groupID + " does not exist!"));

        footballGroup.setName(newName);
    }

    @Transactional
    public void deleteGroup(long groupID) {
        FootballGroup groupToBeDeleted = groupRepository.findById(groupID)
                .orElseThrow(() -> new IllegalArgumentException("Group with id = " + groupID + " does not exist!"));

        for (FootballMember member : groupToBeDeleted.getMembers()) {
            // setting reference to stats null so it doesnt complain about constraints
            member.setStats(null);
            // deleting all stats refering to the member
            statsRepository.deleteStatsMappingToMember(member.getId());
        }

        // this will delete the members and games also
        groupRepository.deleteById(groupID);
    }

    /* Member */

    @Transactional
    public FootballMember createAndAddMemberToGroup(Long groupID, String memberNickname) {
        FootballGroup group = groupRepository.findById(groupID)
                .orElseThrow(() -> new IllegalArgumentException("Group with id = " + groupID + " does not exist!"));

        if (memberRepository.existsByNameAndGroup(memberNickname, groupID)) {
            throw new IllegalArgumentException(
                    "Member with name = " + memberNickname + " already exists in the group!");
        }

        FootballMember newMember = new FootballMember();
        newMember.setNickname(memberNickname);
        newMember.setGroup(group);
        newMember.setRole(MemberRole.MEMBER);
        newMember.getStats().setMember(newMember);
        newMember.getStats().setMemberName(memberNickname);
        newMember = memberRepository.save(newMember);

        return newMember;
    }

    @Transactional
    public void removeMemberFromGroup(Long memberID) {
        memberRepository.findById(memberID)
                .orElseThrow(() -> new IllegalArgumentException("Member with id = " + memberID + " does not exist!"));

        statsRepository.setStatsMemberReferencesToNull(memberID);

        memberRepository.deleteById(memberID);
    }

    @Transactional
    public void joinGroupAsExistingMember(long userID, long memberID) {
        User user = userRepository.findById(userID)
                .orElseThrow(() -> {
                    throw new IllegalArgumentException("User with id = " + userID + " does not exists!");
                });

        FootballMember member = memberRepository.findById(memberID)
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

        FootballGroup group = groupRepository.findById(groupID)
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
        newMember = memberRepository.save(newMember);

        return newMember;
    }

    @Transactional
    public void setRoleToAdmin(long memberID) {
        FootballMember member = memberRepository.findById(memberID)
            .orElseThrow(() -> new IllegalArgumentException("Member with id = " + memberID + " does not exist!"));

        member.setRole(MemberRole.GROUP_ADMIN);
    }

    @Transactional
    public void setRoleToGameMaker(long memberID) {
        FootballMember member = memberRepository.findById(memberID)
            .orElseThrow(() -> new IllegalArgumentException("Member with id = " + memberID + " does not exist!"));

        member.setRole(MemberRole.GAME_MAKER);
    }

    @Transactional
    public void setRoleToMember(long memberID) {
        FootballMember member = memberRepository.findById(memberID)
            .orElseThrow(() -> new IllegalArgumentException("Member with id = " + memberID + " does not exist!"));

        member.setRole(MemberRole.MEMBER);
    }

    /* Game */

    @Transactional
    public FootballGame addNewGame(AddNewFBGameRequest request) {

        // Retrieve group
        FootballGroup group = groupRepository.findById(request.getGroupID())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Group with id = " + request.getGroupID() + " does not exist!"));

        // Create new game
        FootballGame newGame = new FootballGame();
        newGame.setGroup(group);
        newGame.setResults(createResults(new ArrayList<>(request.getGameStats().values())));
        newGame = gameRepository.save(newGame);

        // Save game member stats and update the actual members with the new stats
        for (Long memberID : request.getGameStats().keySet()) {
            FBStats gameStats = request.getGameStats().get(memberID);

            FootballMember dbMember = memberRepository.findById(memberID)
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
            statsRepository.save(gameStats);
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
        Optional<FootballGame> game = gameRepository.findById(gameID);
        if (game.isEmpty()) {
            throw new IllegalAccessError("Game with id = " + gameID + " does not exists!");
        }
        return gameRepository.getGameStats(gameID);
    }

    @Transactional
    public void deleteGame(Long gameID) {
        FootballGame game = gameRepository.findById(gameID)
                .orElseThrow(() -> new IllegalAccessError("Game with id = " + gameID + " does not exists!"));

        List<FBStats> gameStats = getGameStats(gameID);
        decreaseMemberStatsAfterGameDeleted(gameStats);

        // delete stats referencing to that game
        for (FBStats gameStat : gameStats) {
            statsRepository.delete(gameStat);
        }

        gameRepository.delete(game);
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