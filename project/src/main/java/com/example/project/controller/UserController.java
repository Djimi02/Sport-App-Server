package com.example.project.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.model.User;
import com.example.project.service.implementation.UserService;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
@RequestMapping("/user/")
public class UserController {
    
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/get/{id}")
    public User getUser(@PathVariable(name = "id") Long userID) {
        return userService.getUser(userID);
    }

    @PostMapping("/save")
    public User saveUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @PutMapping("/update/name")
    public User updateUserName(@RequestBody ObjectNode objectNode) {
        Long userID = objectNode.get("userID").asLong();
        String userName = objectNode.get("userName").asText();
        return userService.updateUserName(userID, userName);
    }

    @PutMapping("/update/email")
    public User updateEmail(@RequestBody ObjectNode objectNode) {
        Long userID = objectNode.get("userID").asLong();
        String email = objectNode.get("email").asText();
        return userService.updateEmail(userID, email);
    }

    @PutMapping("/update/password")
    public User updatePassword(@RequestBody ObjectNode objectNode) {
        Long userID = objectNode.get("userID").asLong();
        String password = objectNode.get("password").asText();
        return userService.updatePassword(userID, password);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable(name = "id") Long userID) {
        userService.deleteUser(userID);
    }

}