package com.example.project.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.model.group.FootballGroup;
import com.example.project.repository.group.GroupRepository;
import com.example.project.service.implementation.FootballGroupService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/group/")
@AllArgsConstructor
public class GroupController {

    private GroupRepository groupRepository;
    private FootballGroupService footballGroupService;

    @GetMapping("/football/get/{id}")
    public FootballGroup getFootballGroup(@PathVariable(name = "id") Long groupID) {
        return footballGroupService.findFootballGroup(groupID);
    }

}