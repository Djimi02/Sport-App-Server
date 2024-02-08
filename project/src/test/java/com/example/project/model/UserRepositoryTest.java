package com.example.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.project.repository.UserRepository;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void saveUserTest() {
        String username = "username";
        String password = "pass";
        String email = "@gmail.com";
        User user = new User(username, email, password);
        userRepository.save(user);
    }

    @Test
    public void retrieveUserTest() {
        User user = userRepository.findById(1l).get();
        assertEquals(2, user.getMembers().size());
        System.out.println("SIZE = " + user.getMembers().size());
    }
}