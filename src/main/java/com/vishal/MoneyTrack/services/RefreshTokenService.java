package com.vishal.MoneyTrack.services;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RefreshTokenService {

    private final Map<String, RefreshTokenInfo> refreshTokens = new ConcurrentHashMap<>();

    public void storeRefreshToken(String token, UUID userId, LocalDateTime expiryDate) {
        refreshTokens.put(token, new RefreshTokenInfo(userId, expiryDate, false));
    }

    public boolean isValidRefreshToken(String token) {
        RefreshTokenInfo info = refreshTokens.get(token);
        if (info == null || info.revoked) {
            return false;
        }
        if (info.expiryDate.isBefore(LocalDateTime.now())) {
            refreshTokens.remove(token);
            return false;
        }
        return true;
    }

    public UUID getUserIdFromToken(String token) {
        RefreshTokenInfo info = refreshTokens.get(token);
        return info != null ? info.userId : null;
    }

    public void revokeRefreshToken(String token) {
        RefreshTokenInfo info = refreshTokens.get(token);
        if (info != null) {
            info.revoked = true;
            refreshTokens.remove(token);
        }
    }

    private static class RefreshTokenInfo {
        final UUID userId;
        final LocalDateTime expiryDate;
        boolean revoked;

        RefreshTokenInfo(UUID userId, LocalDateTime expiryDate, boolean revoked) {
            this.userId = userId;
            this.expiryDate = expiryDate;
            this.revoked = revoked;
        }
    }
}

