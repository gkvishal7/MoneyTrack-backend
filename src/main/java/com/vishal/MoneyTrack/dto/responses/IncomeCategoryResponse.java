package com.vishal.MoneyTrack.dto.responses;

import java.time.LocalDateTime;
import java.util.UUID;

public record IncomeCategoryResponse(
        UUID id,
        String categoryName,
        String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}

