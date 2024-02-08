package com.example.project.service.implementation;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.example.project.model.User;
import com.example.project.model.UserDetailsImpl;
import com.example.project.repository.UserRepository;
import com.example.project.request.SignInRequest;
import com.example.project.request.SignUpRequest;
import com.example.project.response.JwtAuthenticationResponse;
import com.example.project.service.AuthService;
import com.example.project.service.JwtService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private UserService userService;
    private UserRepository userRepository;
    private JwtService jwtService;
    private AuthenticationManager authenticationManager;

    @Override
    public JwtAuthenticationResponse signup(SignUpRequest request) {
        User user = new User(request.getName(), request.getEmail(), request.getPassword());

        userService.saveUser(user);

        String jwt = jwtService.generateToken(new UserDetailsImpl(user));
        
        return new JwtAuthenticationResponse(jwt);
    }

    @Override
    public JwtAuthenticationResponse signin(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        String jwt = jwtService.generateToken(new UserDetailsImpl(user));
        
        return new JwtAuthenticationResponse(jwt);
    }

}