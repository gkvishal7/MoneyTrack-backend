package com.vishal.MoneyTrack.dto.responses;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record SubCategoryResponse(
        UUID id,
        String name,
        BigDecimal budget,
        UUID categoryId,
        String categoryName,
        String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}

