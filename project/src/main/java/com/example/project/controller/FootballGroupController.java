package com.example.project.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.model.group.FootballGroup;
import com.example.project.service.implementation.FootballGroupService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/group/")
@AllArgsConstructor
public class FootballGroupController {

    private FootballGroupService footballGroupService;

    @GetMapping("/football/get/{id}")
    public FootballGroup getFootballGroup(@PathVariable(name = "id") Long groupID) {
        return footballGroupService.findFootballGroup(groupID);
    }

    @PostMapping("/football/save/{name}/{userid}")
    public FootballGroup createFootballGroup(@PathVariable(name = "name") String name, @PathVariable(name = "userid") Long userID) {
        return footballGroupService.saveFootballGroup(name, userID);
    }

}