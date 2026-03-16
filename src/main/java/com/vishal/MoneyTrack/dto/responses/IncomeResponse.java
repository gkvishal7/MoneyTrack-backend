package com.vishal.MoneyTrack.dto.responses;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record IncomeResponse(
        UUID id,
        LocalDate incomeDate,
        String sourceOfIncome,
        BigDecimal amount,
        UUID accountId,
        String accountName,
        UUID incomeCategoryId,
        String incomeCategoryName,
        String notes,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}

