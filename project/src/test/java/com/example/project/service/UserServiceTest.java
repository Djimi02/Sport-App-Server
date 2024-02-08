package com.example.project.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.project.model.User;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void testSaveUser() {
        String username = "djimi";
        String email = "@abv.com";
        String password = "pass";
        User user = new User(username, email, password);
        userService.saveUser(user);
    }

    @Test
    void testDeleteUser() {
        Long userID = 1l;
        userService.deleteUser(userID);
    }

    @Test
    void testFindUserByEmail() {
        String email = "@abv.com";
        User userByEmail = userService.findUserByEmail(email);
        System.out.println("USER EMAIL = " + userByEmail.getEmail() + " USER ID = " + userByEmail.getId());
    }
}
