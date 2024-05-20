package com.example.project.service.interfaces;

import java.util.List;

import com.example.project.model.game.Game;
import com.example.project.model.group.Group;
import com.example.project.model.member.Member;
import com.example.project.model.stats.Stats;
import com.example.project.request.addNewGameRequests.AddNewGameRequest;

public interface SportService<GroupT extends Group, MemberT extends Member, GameT extends Game, StatsT extends Stats, AddNewGameRequestsT extends AddNewGameRequest<?>> {
 
    /* Group */

    public GroupT saveGroup(String name, Long userID);

    public GroupT findGroupByID(Long groupID);

    public GroupT findGroupByUUID(String uuidStr);

    public void updateGroupName(Long groupID, String newName);

    public void deleteGroup(long groupID);

    /* Member */

    public MemberT createAndAddMemberToGroup(Long groupID, String memberNickname);

    public void removeMemberFromGroup(Long memberID);

    public void joinGroupAsExistingMember(long userID, long memberID);

    public MemberT joinGroupAsNewMember(long userID, long groupID);

    public void setRoleToAdmin(long memberID);

    public void setRoleToGameMaker(long memberID);

    public void setRoleToMember(long memberID);

    /* Game */

    public GameT addNewGame(AddNewGameRequestsT request);

    public List<StatsT> getGameStats(Long gameID);

    public void deleteGame(Long gameID);

}