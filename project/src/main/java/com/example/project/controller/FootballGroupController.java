package com.example.project.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.model.group.FootballGroup;
import com.example.project.model.member.FootballMember;
import com.example.project.service.implementation.FootballGroupService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/group/football/")
@AllArgsConstructor
public class FootballGroupController {

    private FootballGroupService footballGroupService;

    @GetMapping("/get/{id}")
    public FootballGroup getFootballGroup(@PathVariable(name = "id") Long groupID) {
        return footballGroupService.findFootballGroup(groupID);
    }

    @PostMapping("/save/{name}/{userid}")
    public FootballGroup createFootballGroup(@PathVariable(name = "name") String name, @PathVariable(name = "userid") Long userID) {
        return footballGroupService.saveFootballGroup(name, userID);
    }

    @PostMapping("/add/member/{groupid}/{name}")
    public FootballMember createFootballMember(@PathVariable(name = "groupid") Long groupID, @PathVariable(name = "name") String memberName) {
        return footballGroupService.createAndAddMemberToGroup(groupID, memberName);
    }

    @PostMapping("/update/member")
    public void updateFootballMemberStats(@RequestBody FootballMember member) {
        footballGroupService.updateFootballMemberStats(member);
    }

}