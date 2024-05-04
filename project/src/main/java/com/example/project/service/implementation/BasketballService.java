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
import com.example.project.model.game.BasketballGame;
import com.example.project.model.group.BasketballGroup;
import com.example.project.model.member.BasketballMember;
import com.example.project.model.stats.BBStats;
import com.example.project.repository.UserRepository;
import com.example.project.repository.game.BasketballGameRepository;
import com.example.project.repository.stats.BBStatsRepository;
import com.example.project.request.addNewGameRequests.AddNewBBGameRequest;

import jakarta.transaction.Transactional;

import com.example.project.repository.group.BasketballGroupRepository;
import com.example.project.repository.member.BasketballMemberRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BasketballService {
 
    private UserRepository userRepository;
    private BasketballGroupRepository groupRepository;
    private BasketballMemberRepository memberRepository;
    private BasketballGameRepository gameRepository;
    private BBStatsRepository statsRepository;

    /* GROUP */
    
    @Transactional
    public BasketballGroup saveGroup(String name, Long userID) {
        BasketballGroup group = new BasketballGroup(name);
        BasketballGroup savedGroup = groupRepository.save(group);

        User user = userRepository.findById(userID)
                .orElseThrow(() -> new IllegalArgumentException("User with id= " + userID + " does not exist!"));

        BasketballMember newMember = new BasketballMember(user.getUserName(), savedGroup);
        newMember.setUser(user);
        newMember.setRole(MemberRole.GROUP_ADMIN);
        newMember.getStats().setMember(newMember);
        newMember.getStats().setMemberName(user.getUserName());
        newMember = memberRepository.save(newMember);

        savedGroup.addMember(newMember);

        return savedGroup;
    }

    public BasketballGroup findGroupByID(Long groupID) {
        return groupRepository.findById(groupID)
                .orElseThrow(() -> new IllegalArgumentException("Group with id = " + groupID + " does not exist!"));
    }

    public BasketballGroup findGroupByUUID(String uuidStr) {
        UUID uuid = UUID.fromString(uuidStr);
        return groupRepository.findByUuid(uuid);
    }

    @Transactional
    public BasketballGroup updateGroupName(Long groupID, String newName) {
        BasketballGroup group = groupRepository.findById(groupID)
                .orElseThrow(() -> new IllegalArgumentException("Group with id = " + groupID + " does not exist!"));

        group.setName(newName);

        return group;
    }

    @Transactional
    public void deleteGroup(long groupID) {
        BasketballGroup groupToBeDeleted = groupRepository.findById(groupID)
                .orElseThrow(() -> new IllegalArgumentException("Group with id = " + groupID + " does not exist!"));

        for (BasketballMember member : groupToBeDeleted.getMembers()) {
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
    public BasketballMember createAndAddMemberToGroup(Long groupID, String memberNickname) {
        BasketballGroup group = groupRepository.findById(groupID)
                .orElseThrow(() -> new IllegalArgumentException("Group with id = " + groupID + " does not exist!"));

        if (memberRepository.existsByNameAndGroup(memberNickname, groupID)) {
            throw new IllegalArgumentException(
                    "Member with name = " + memberNickname + " already exists in the group!");
        }

        BasketballMember newMember = new BasketballMember(memberNickname, group);
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

        BasketballMember member = memberRepository.findById(memberID)
            .orElseThrow(() -> {
                throw new IllegalArgumentException("Member with id = " + memberID + " does not exists!");
            });

        member.setUser(user);
    }

    public BasketballMember joinGroupAsNewMember(long userID, long groupID) {
        User user = userRepository.findById(userID)
                .orElseThrow(() -> {
                    throw new IllegalArgumentException("User with id = " + userID + " does not exists!");
                });

        BasketballGroup group = groupRepository.findById(groupID)
                .orElseThrow(() -> {
                    throw new IllegalArgumentException("Member with id = " + groupID + " does not exists!");
                });

        BasketballMember newMember = new BasketballMember();
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
        BasketballMember member = memberRepository.findById(memberID)
            .orElseThrow(() -> new IllegalArgumentException("Member with id = " + memberID + " does not exist!"));

        member.setRole(MemberRole.GROUP_ADMIN);
    }

    @Transactional
    public void setRoleToGameMaker(long memberID) {
        BasketballMember member = memberRepository.findById(memberID)
            .orElseThrow(() -> new IllegalArgumentException("Member with id = " + memberID + " does not exist!"));

        member.setRole(MemberRole.GAME_MAKER);
    }

    @Transactional
    public void setRoleToMember(long memberID) {
        BasketballMember member = memberRepository.findById(memberID)
            .orElseThrow(() -> new IllegalArgumentException("Member with id = " + memberID + " does not exist!"));

        member.setRole(MemberRole.MEMBER);
    }

    /* Game */

    @Transactional
    public BasketballGame addNewGame(AddNewBBGameRequest request) {

        // Retrieve group
        BasketballGroup group = groupRepository.findById(request.getGroupID())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Group with id = " + request.getGroupID() + " does not exist!"));

        // Create new game
        Calendar calendar = Calendar.getInstance();
        BasketballGame newGame = new BasketballGame(LocalDate.of(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH)), group);
        newGame.setResults(createResults(new ArrayList<>(request.getGameStats().values())));
        newGame = gameRepository.save(newGame);

        // Save game member stats and update the actual members with the new stats
        for (Long memberID : request.getGameStats().keySet()) {
            BBStats gameStats = request.getGameStats().get(memberID);

            BasketballMember dbMember = memberRepository.findById(memberID)
                    .orElseThrow(() -> new IllegalArgumentException("Such member does not exists!"));
            BBStats dbMemberStats = dbMember.getStats();

            dbMemberStats.setWins(dbMemberStats.getWins() + gameStats.getWins());
            dbMemberStats.setDraws(dbMemberStats.getDraws() + gameStats.getDraws());
            dbMemberStats.setLoses(dbMemberStats.getLoses() + gameStats.getLoses());

            dbMemberStats.setPoints(dbMemberStats.getPoints() + gameStats.getPoints());
            dbMemberStats.setBlocks(dbMemberStats.getBlocks() + gameStats.getBlocks());
            dbMemberStats.setNumberOfThreePoints(dbMemberStats.getNumberOfThreePoints() + gameStats.getNumberOfThreePoints());
            dbMemberStats.setFouls(dbMemberStats.getFouls() + gameStats.getFouls());

            gameStats.setMember(dbMember);
            gameStats.setGame(newGame);
            statsRepository.save(gameStats);
        }

        return newGame;
    }

    private String createResults(List<BBStats> gameStats) {
        StringBuilder builder = new StringBuilder();
        int team1Score = 0;
        int team2Score = 0;
        for (BBStats stats : gameStats) {
            if (stats.getIsPartOfTeam1()) {
                team1Score += stats.getPoints();
            } else {
                team2Score += stats.getPoints();
            }
        }

        builder.append(team1Score);
        builder.append(":");
        builder.append(team2Score);
        return builder.toString();
    }

    public List<BBStats> getGameStats(Long gameID) {
        Optional<BasketballGame> game = gameRepository.findById(gameID);
        if (game.isEmpty()) {
            throw new IllegalAccessError("Game with id = " + gameID + " does not exists!");
        }
        return gameRepository.getGameStats(gameID);
    }

    @Transactional
    public void deleteGame(Long gameID) {
        BasketballGame game = gameRepository.findById(gameID)
                .orElseThrow(() -> new IllegalAccessError("Game with id = " + gameID + " does not exists!"));

        List<BBStats> gameStats = getGameStats(gameID);
        decreaseMemberStatsAfterGameDeleted(gameStats);

        // delete stats referencing to that game
        for (BBStats gameStat : gameStats) {
            statsRepository.delete(gameStat);
        }

        gameRepository.delete(game);
    }

    private void decreaseMemberStatsAfterGameDeleted(List<BBStats> gameStats) {
        for (BBStats gameStat : gameStats) {
            if (gameStat.getMember() == null) {
                continue;
            }
            BBStats associatedGMemberStats = gameStat.getMember().getStats();

            associatedGMemberStats.setWins(associatedGMemberStats.getWins() - gameStat.getWins());
            associatedGMemberStats.setDraws(associatedGMemberStats.getDraws() - gameStat.getDraws());
            associatedGMemberStats.setLoses(associatedGMemberStats.getLoses() - gameStat.getLoses());
            
            associatedGMemberStats.setPoints(associatedGMemberStats.getPoints() - associatedGMemberStats.getPoints());
            associatedGMemberStats.setBlocks(associatedGMemberStats.getBlocks() - associatedGMemberStats.getBlocks());
            associatedGMemberStats.setNumberOfThreePoints(associatedGMemberStats.getNumberOfThreePoints() - associatedGMemberStats.getNumberOfThreePoints());
            associatedGMemberStats.setFouls(associatedGMemberStats.getFouls() - associatedGMemberStats.getFouls());
        }
    }

}