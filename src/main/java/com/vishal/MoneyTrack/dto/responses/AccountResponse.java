package com.vishal.MoneyTrack.dto.responses;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record AccountResponse(
        UUID id,
        String name,
        BigDecimal balance,
        String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
