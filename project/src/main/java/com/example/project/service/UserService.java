package com.example.project.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.project.model.User;
import com.example.project.repository.UserRepository;

@Service
public class UserService {
    
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User getUser(Long userID) {
        Optional<User> uOPT = userRepository.findById(userID);
        if (uOPT.isEmpty()) {
            throw new IllegalArgumentException("User with id = " + userID + " does not exist.");
        }
        return uOPT.get();
    }
}