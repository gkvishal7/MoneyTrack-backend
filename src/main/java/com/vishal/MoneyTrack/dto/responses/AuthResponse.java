package com.vishal.MoneyTrack.dto.responses;

import lombok.Builder;

@Builder
public record AuthResponse(
        String accessToken,
        String refreshToken,
        Long expiresIn
) {
}

