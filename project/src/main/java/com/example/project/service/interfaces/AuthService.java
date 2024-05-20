package com.example.project.service.interfaces;

import com.example.project.request.SignInRequest;
import com.example.project.request.SignUpRequest;
import com.example.project.response.JwtAuthenticationResponse;

public interface AuthService {
    JwtAuthenticationResponse signup(SignUpRequest request);

    JwtAuthenticationResponse signin(SignInRequest request);
}
