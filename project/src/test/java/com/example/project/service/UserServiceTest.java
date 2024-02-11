package com.example.project.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.project.model.User;
import com.example.project.service.implementation.UserService;

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
    void testSaveUserExceptionForRepeatingEmail() {
        String username = "djimi";
        String email = "masha3@gmail.com";
        String password = "pass";
        User user = new User(username, email, password);
        try {
            userService.saveUser(user);
        } catch (IllegalArgumentException e) {
            assertTrue(true);
            return;
        } catch (Exception e) {
            assertTrue(false);
            return;
        }
        assertTrue(false);
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
