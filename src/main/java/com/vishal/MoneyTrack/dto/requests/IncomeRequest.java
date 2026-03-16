package com.vishal.MoneyTrack.dto.requests;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record IncomeRequest(
        @NotNull(message = "Income date is required")
        @PastOrPresent(message = "Income date cannot be in the future")
        LocalDate incomeDate,

        @NotBlank(message = "Source of income is required")
        @Size(max = 200, message = "Source of income must not exceed 200 characters")
        String sourceOfIncome,

        @NotNull(message = "Amount is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than 0")
        BigDecimal amount,

        @NotNull(message = "Account ID is required")
        UUID accountId,

        @NotNull(message = "Income category ID is required")
        UUID incomeCategoryId,

        @Size(max = 1000, message = "Notes must not exceed 1000 characters")
        String notes
) {
}

