package com.vishal.MoneyTrack.dto.requests;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ExpenseRequest(
        @NotNull(message = "Expense date is required")
        @PastOrPresent(message = "Expense date cannot be in the future")
        LocalDate expenseDate,

        @NotNull(message = "Expense type ID is required")
        UUID expenseTypeId,

        @NotNull(message = "Amount is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than 0")
        BigDecimal amount,

        @NotNull(message = "Category ID is required")
        UUID categoryId,

        @NotNull(message = "Sub category ID is required")
        UUID subCategoryId,

        @NotNull(message = "Account ID is required")
        UUID accountId,

        @Size(max = 1000, message = "Notes must not exceed 1000 characters")
        String notes
) {
}

