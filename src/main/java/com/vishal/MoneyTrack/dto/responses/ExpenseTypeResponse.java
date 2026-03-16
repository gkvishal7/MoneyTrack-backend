package com.vishal.MoneyTrack.dto.responses;

import java.time.LocalDateTime;
import java.util.UUID;

public record ExpenseTypeResponse(
        UUID id,
        String typeName,
        String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}

