package com.vishal.MoneyTrack.dto.responses;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record CategoryResponse(
        UUID id,
        String name,
        BigDecimal budget,
        String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}

