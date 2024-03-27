package com.example.project.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.model.game.FootballGame;
import com.example.project.model.group.FootballGroup;
import com.example.project.model.member.FootballMember;
import com.example.project.request.AddNewFootballGameRequest;
import com.example.project.service.implementation.FootballGroupService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/football/")
@AllArgsConstructor
public class FootballGroupController {

    private FootballGroupService footballGroupService;

    /* GROUP */

    @GetMapping("/group/get/{id}")
    public FootballGroup getFootballGroup(@PathVariable(name = "id") Long groupID) {
        return footballGroupService.findFootballGroup(groupID);
    }

    @PostMapping("/group/save/{name}/{userid}")
    public FootballGroup createFootballGroup(@PathVariable(name = "name") String name,
            @PathVariable(name = "userid") Long userID) {
        return footballGroupService.saveFootballGroup(name, userID);
    }

    @DeleteMapping("/group/delete/{id}")
    public void deleteGroup(@PathVariable(name = "id") Long groupID) {
        footballGroupService.deleteFootballGroup(groupID);
    }

    /* Member */

    @PostMapping("/member/save/{groupid}/{name}")
    public FootballMember createFootballMember(@PathVariable(name = "groupid") Long groupID,
            @PathVariable(name = "name") String memberName) {
        FootballMember output = footballGroupService.createAndAddMemberToGroup(groupID, memberName);
        output.setGroup(null);
        return output;
    }

    @DeleteMapping("/member/delete/{id}")
    public void removeMemberFromGroup(@PathVariable(name = "id") Long memberID) {
        footballGroupService.removeMemberFromGroup(memberID);
    }

    /* Game */

    @GetMapping("/game/get/gamestats/{id}")
    public List<FootballMember> getGameStats(@PathVariable(name = "id") Long gameID) {
        return footballGroupService.getGameStats(gameID);
    }

    @PostMapping("/game/save")
    public FootballGame addNewGame(@RequestBody AddNewFootballGameRequest request) {
        FootballGame output = footballGroupService.addNewGame(request);
        output.setGroup(null);
        output.setMembers(null);
        return output;
    }

    @DeleteMapping("/game/delete/{id}")
    public void deleteGame(@PathVariable(name = "id") Long gameID) {
        footballGroupService.deleteFootballGame(gameID);
    }
}