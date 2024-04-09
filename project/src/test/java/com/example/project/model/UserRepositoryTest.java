package com.example.project.model;

import static org.junit.jupiter.api.Assertions.assertTrue;

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
        User user = userRepository.findById(3l).get();
        // assertEquals(2, user.getMembers().size());
        // System.out.println("User = " + user);
        // System.out.println("Member name = " + user.getMembers().get(0).numberOfGroups());
        System.out.println("SIZE = " + user.getMembers().size());
    }

    @Test
    public void existsUserByEmailTest() {
        String email = "masha3@gmail.com";
        assertTrue(userRepository.existsUserByEmail(email));
    }
}