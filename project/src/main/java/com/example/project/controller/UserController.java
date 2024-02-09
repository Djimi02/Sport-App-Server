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
import com.example.project.request.UpdateUserEmailRequest;
import com.example.project.request.UpdateUserNameRequest;
import com.example.project.request.UpdateUserPasswordRequest;
import com.example.project.service.implementation.UserService;

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
    public User updateUserName(@RequestBody UpdateUserNameRequest request) {
        return userService.updateUserName(request.getUserID(), request.getUserName());
    }

    @PutMapping("/update/email")
    public User updateEmail(@RequestBody UpdateUserEmailRequest request) {
        return userService.updateEmail(request.getUserID(), request.getEmail());
    }

    @PutMapping("/update/password")
    public User updatePassword(@RequestBody UpdateUserPasswordRequest request) {
        return userService.updatePassword(request.getUserID(), request.getPassword());
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable(name = "id") Long userID) {
        userService.deleteUser(userID);
    }

}