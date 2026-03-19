package com.vishal.MoneyTrack.services;

import com.vishal.MoneyTrack.entities.RefreshToken;
import com.vishal.MoneyTrack.entities.User;
import com.vishal.MoneyTrack.repo.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public void storeRefreshToken(String token, User user, LocalDateTime expiryDate) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(token);
        refreshToken.setUser(user);
        refreshToken.setExpiryDate(expiryDate);
        refreshToken.setRevoked(false);
        refreshTokenRepository.save(refreshToken);
    }

    public boolean isValidRefreshToken(String token) {
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByTokenAndRevokedFalse(token);
        if (refreshToken.isEmpty()) {
            return false;
        }
        if (refreshToken.get().getExpiryDate().isBefore(LocalDateTime.now())) {
            revokeRefreshToken(token);
            return false;
        }
        return true;
    }

    public UUID getUserIdFromToken(String token) {
        return refreshTokenRepository.findByTokenAndRevokedFalse(token)
                .map(rt -> rt.getUser().getId())
                .orElse(null);
    }

    @Transactional
    public void revokeRefreshToken(String token) {
        refreshTokenRepository.findByToken(token).ifPresent(rt -> {
            rt.setRevoked(true);
            refreshTokenRepository.save(rt);
        });
    }

    @Transactional
    public void revokeAllTokensForUser(UUID userId) {
        refreshTokenRepository.revokeAllByUserId(userId);
    }

    public boolean tokenExists(String token) {
        return refreshTokenRepository.findByToken(token).isPresent();
    }
}
