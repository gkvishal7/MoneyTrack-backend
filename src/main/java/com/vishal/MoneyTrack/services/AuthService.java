package com.vishal.MoneyTrack.services;

import com.vishal.MoneyTrack.dto.requests.LoginRequest;
import com.vishal.MoneyTrack.dto.requests.RefreshTokenRequest;
import com.vishal.MoneyTrack.dto.requests.RegisterRequest;
import com.vishal.MoneyTrack.dto.responses.AuthResponse;

import java.util.UUID;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
    AuthResponse refreshToken(RefreshTokenRequest request);
    void logout(String refreshToken);
    void deleteAccount(UUID userId, String password);
}

