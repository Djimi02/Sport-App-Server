package com.example.project.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.model.game.BasketballGame;
import com.example.project.model.group.BasketballGroup;
import com.example.project.model.member.BasketballMember;
import com.example.project.model.stats.BBStats;
import com.example.project.request.addNewGameRequests.AddNewBBGameRequest;
import com.example.project.service.implementation.BasketballService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/basketball/")
@AllArgsConstructor
public class BasketballController {
 
    private BasketballService service;

    /* GROUP */

    @GetMapping("/group/get/{id}")
    public BasketballGroup getGroupByID(@PathVariable(name = "id") Long groupID) {
        return service.findGroupByID(groupID);
    }

    @GetMapping("/group/get/uuid/{uuid}")
    public BasketballGroup findGroupByUUID(@PathVariable(name = "uuid") String uuidStr) {
        return service.findGroupByUUID(uuidStr);
    }

    @PostMapping("/group/save/{name}/{userid}")
    public BasketballGroup createGroup(@PathVariable(name = "name") String name,
            @PathVariable(name = "userid") Long userID) {
        return service.saveGroup(name, userID);
    }

    @PostMapping("/group/join/notnew/{userid}/{memberid}")
    public void joinGroupAsExistingMember(@PathVariable(name = "userid") long userID, @PathVariable(name = "memberid") long memberID) {
        service.joinGroupAsExistingMember(userID, memberID);
    }

    @PostMapping("/group/join/new/{userid}/{groupid}")
    public BasketballMember joinGroupAsNewMember(@PathVariable(name = "userid") long userID, @PathVariable(name = "groupid") long groupID) {
        BasketballMember member = service.joinGroupAsNewMember(userID, groupID);
        member.setGroup(null);
        return member;
    }

    @DeleteMapping("/group/delete/{id}")
    public void deleteGroup(@PathVariable(name = "id") Long groupID) {
        service.deleteGroup(groupID);
    }

    /* Member */

    @PostMapping("/member/save/{groupid}/{name}")
    public BasketballMember createMember(@PathVariable(name = "groupid") Long groupID,
            @PathVariable(name = "name") String memberName) {
        BasketballMember output = service.createAndAddMemberToGroup(groupID, memberName);
        output.setGroup(null);
        return output;
    }

    @DeleteMapping("/member/delete/{id}")
    public void removeMemberFromGroup(@PathVariable(name = "id") Long memberID) {
        service.removeMemberFromGroup(memberID);
    }

    @PostMapping("/member/role/admin/{id}")
    public void setRoleToAdmin(@PathVariable(name = "id") Long memberID) {
        service.setRoleToAdmin(memberID);
    }

    @PostMapping("/member/role/gamemaker/{id}")
    public void setRoleToGameMaker(@PathVariable(name = "id") Long memberID) {
        service.setRoleToGameMaker(memberID);
    }

    @PostMapping("/member/role/member/{id}")
    public void setRoleToMember(@PathVariable(name = "id") Long memberID) {
        service.setRoleToMember(memberID);
    }

    /* Game */

    @GetMapping("/game/get/gamestats/{id}")
    public List<BBStats> getGameStats(@PathVariable(name = "id") Long gameID) {
        return service.getGameStats(gameID);
    }

    @PostMapping("/game/save")
    public BasketballGame addNewGame(@RequestBody AddNewBBGameRequest request) {
        BasketballGame output = service.addNewGame(request);
        output.setGroup(null);
        return output;
    }

    @DeleteMapping("/game/delete/{id}")
    public void deleteGame(@PathVariable(name = "id") Long gameID) {
        service.deleteGame(gameID);
    }

}