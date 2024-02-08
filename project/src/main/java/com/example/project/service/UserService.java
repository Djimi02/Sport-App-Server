package com.example.project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.project.model.User;
import com.example.project.model.member.Member;
import com.example.project.repository.UserRepository;

import jakarta.transaction.Transactional;

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

    public User findUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new IllegalArgumentException("User with email = " + email + " does not exist."));

        return user;
    }

    @Transactional
    public User updateUserName(Long userID, String newUserName) {
        Optional<User> uOPT = userRepository.findById(userID);
        if (uOPT.isEmpty()) {
            throw new IllegalArgumentException("User with id = " + userID + " does not exist.");
        }
        User user = uOPT.get();
        user.setUserName(newUserName);
        return user;
    }

    @Transactional
    public User updateEmail(Long userID, String newEmail) {
        Optional<User> uOPT = userRepository.findById(userID);
        if (uOPT.isEmpty()) {
            throw new IllegalArgumentException("User with id = " + userID + " does not exist.");
        }
        User user = uOPT.get();
        user.setEmail(newEmail);
        return user;
    }

    @Transactional
    public User updatePassword(Long userID, String newPass) {
        Optional<User> uOPT = userRepository.findById(userID);
        if (uOPT.isEmpty()) {
            throw new IllegalArgumentException("User with id = " + userID + " does not exist.");
        }
        User user = uOPT.get();
        user.setPassword(newPass);
        return user;
    }

    @Transactional
    public void deleteUser(Long userID) {
        Optional<User> uOPT = userRepository.findById(userID);
        if (uOPT.isEmpty()) {
            throw new IllegalArgumentException("User with id = " + userID + " does not exist.");
        }

        List<Member> members = uOPT.get().getMembers();
        for (Member member : members) { // remove user reference from members
            member.setUser(null);
        }

        userRepository.deleteById(userID);
    }
}