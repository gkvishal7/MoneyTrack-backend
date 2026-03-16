package com.vishal.MoneyTrack.dto.responses;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record ExpenseResponse(
        UUID id,
        LocalDate expenseDate,
        UUID expenseTypeId,
        String expenseTypeName,
        BigDecimal amount,
        UUID categoryId,
        String categoryName,
        UUID subCategoryId,
        String subCategoryName,
        UUID accountId,
        String accountName,
        String notes,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}

