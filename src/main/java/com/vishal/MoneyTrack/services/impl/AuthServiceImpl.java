package com.vishal.MoneyTrack.services.impl;

// import com.vishal.MoneyTrack.dto.requests.DeleteAccountRequest;
import com.vishal.MoneyTrack.dto.requests.LoginRequest;
import com.vishal.MoneyTrack.dto.requests.RefreshTokenRequest;
import com.vishal.MoneyTrack.dto.requests.RegisterRequest;
import com.vishal.MoneyTrack.dto.responses.AuthResponse;
import com.vishal.MoneyTrack.entities.User;
import com.vishal.MoneyTrack.exceptions.AuthenticationException;
import com.vishal.MoneyTrack.exceptions.DuplicateResourceException;
import com.vishal.MoneyTrack.exceptions.InvalidTokenException;
import com.vishal.MoneyTrack.exceptions.ResourceNotFoundException;
import com.vishal.MoneyTrack.repo.UserRepository;
import com.vishal.MoneyTrack.services.AuthService;
import com.vishal.MoneyTrack.services.JwtService;
import com.vishal.MoneyTrack.services.RefreshTokenService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    @Value("${jwt.access-token-expiration-ms}")
    private long accessTokenExpirationMs;

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmailId(request.emailId())) {
            throw new DuplicateResourceException("Email already exists: " + request.emailId());
        }
        if (userRepository.existsByPhoneNumber(request.phoneNumber())) {
            throw new DuplicateResourceException("Phone number already exists: " + request.phoneNumber());
        }

        User user = new User();
        user.setEmailId(request.emailId());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setPhoneNumber(request.phoneNumber());
        user.setEnabled(true);

        User savedUser = userRepository.save(user);

        String accessToken = jwtService.generateAccessToken(savedUser);
        String refreshToken = jwtService.generateRefreshToken(savedUser);

        LocalDateTime refreshTokenExpiry = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(System.currentTimeMillis() + 604800000L),
                ZoneId.systemDefault()
        );
        refreshTokenService.storeRefreshToken(refreshToken, savedUser.getId(), refreshTokenExpiry);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expiresInSeconds(accessTokenExpirationMs / 1000)
                .build();
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmailId(request.emailId())
                .orElseThrow(() -> new AuthenticationException("Invalid email or password"));

        if (!user.getEnabled()) {
            throw new AuthenticationException("Account is disabled");
        }

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new AuthenticationException("Invalid email or password");
        }

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        LocalDateTime refreshTokenExpiry = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(System.currentTimeMillis() + 604800000L),
                ZoneId.systemDefault()
        );
        refreshTokenService.storeRefreshToken(refreshToken, user.getId(), refreshTokenExpiry);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expiresInSeconds(accessTokenExpirationMs / 1000)
                .build();
    }

    @Override
    public AuthResponse refreshToken(RefreshTokenRequest request) {
        String refreshToken = request.refreshToken();

        if (!jwtService.validateToken(refreshToken) || !jwtService.isRefreshToken(refreshToken)) {
            throw new InvalidTokenException("Invalid or expired refresh token");
        }

        if (!refreshTokenService.isValidRefreshToken(refreshToken)) {
            throw new InvalidTokenException("Refresh token has been revoked or expired");
        }

        UUID userId = refreshTokenService.getUserIdFromToken(refreshToken);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!user.getEnabled()) {
            throw new AuthenticationException("Account is disabled");
        }

        String newAccessToken = jwtService.generateAccessToken(user);
        String newRefreshToken = jwtService.generateRefreshToken(user);

        refreshTokenService.revokeRefreshToken(refreshToken);

        LocalDateTime refreshTokenExpiry = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(System.currentTimeMillis() + 604800000L),
                ZoneId.systemDefault()
        );
        refreshTokenService.storeRefreshToken(newRefreshToken, user.getId(), refreshTokenExpiry);
        
        return AuthResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .expiresInSeconds(accessTokenExpirationMs / 1000)
                .build();
    }

    @Override
    public void logout(String refreshToken) {
        refreshTokenService.revokeRefreshToken(refreshToken);
    }

    @Override
    @Transactional
    public void deleteAccount(UUID userId, String password) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new AuthenticationException("Invalid password");
        }

        userRepository.delete(user);
    }
}

