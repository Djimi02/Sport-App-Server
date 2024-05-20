package com.example.project.controller;

import java.util.List;

import com.example.project.model.game.Game;
import com.example.project.model.group.Group;
import com.example.project.model.member.Member;
import com.example.project.model.stats.Stats;
import com.example.project.request.addNewGameRequests.AddNewGameRequest;

public interface SportController<GroupT extends Group, MemberT extends Member, GameT extends Game, StatsT extends Stats, AddNewGameRequestsT extends AddNewGameRequest<?>> {
 
    /* Group */

    public GroupT getGroupByID(Long groupID);

    public GroupT findGroupByUUID(String uuidStr);

    public GroupT createGroup(String name, Long userID);

    public void joinGroupAsExistingMember(long userID, long memberID);

    public MemberT joinGroupAsNewMember(long userID, long groupID);

    public void deleteGroup(Long groupID);

    /* Member */

    public MemberT createMember(Long groupID, String memberName);

    public void removeMemberFromGroup(Long memberID);

    public void setRoleToAdmin(Long memberID);

    public void setRoleToGameMaker(Long memberID);

    public void setRoleToMember(Long memberID);

    /* Game */

    public List<StatsT> getGameStats(Long gameID);

    public GameT addNewGame(AddNewGameRequestsT request);

    public void deleteGame(Long gameID);

}