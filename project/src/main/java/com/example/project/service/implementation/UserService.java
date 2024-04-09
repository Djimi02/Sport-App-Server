package com.example.project.service.implementation;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.project.model.User;
import com.example.project.model.UserDetailsImpl;
import com.example.project.model.member.Member;
import com.example.project.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User saveUser(User user) {
        if (userRepository.existsUserByEmail(user.getEmail())) {
            throw new IllegalArgumentException("User with email = " + user.getEmail() + " already exists.");
        }
        String encodedPass = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPass);
        return userRepository.save(user);
    }

    public User getUser(Long userID) {
        User user = userRepository.findById(userID)
            .orElseThrow(() -> new IllegalArgumentException("User with id = " + userID + " does not exist."));
        return user;
    }

    public User findUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new IllegalArgumentException("User with email = " + email + " does not exist."));

        return user;
    }

    @Transactional
    public User updateUserName(Long userID, String newUserName) {
        User user = userRepository.findById(userID)
            .orElseThrow(() -> new IllegalArgumentException("User with id = " + userID + " does not exist."));
        user.setUserName(newUserName);
        return user;
    }

    @Transactional
    public User updateEmail(Long userID, String newEmail) {
        User user = userRepository.findById(userID)
            .orElseThrow(() -> new IllegalArgumentException("User with id = " + userID + " does not exist."));
        user.setEmail(newEmail);
        return user;
    }

    @Transactional
    public User updatePassword(Long userID, String newPass) {
        User user = userRepository.findById(userID)
            .orElseThrow(() -> new IllegalArgumentException("User with id = " + userID + " does not exist."));
        user.setPassword(newPass);
        return user;
    }

    @Transactional
    public void deleteUser(Long userID) {
        User user = userRepository.findById(userID)
            .orElseThrow(() -> new IllegalArgumentException("User with id = " + userID + " does not exist."));

        List<Member<?,?>> members = user.getMembers();
        for (Member<?,?> member : members) { // remove user reference from members
            member.setUser(null);
        }

        userRepository.deleteById(userID);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new UserDetailsImpl(findUserByEmail(username));
    }
}