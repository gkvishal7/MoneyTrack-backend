package com.vishal.MoneyTrack.dto.requests;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record AccountRequest(
        @NotBlank(message = "Account name is required")
        @Size(max = 100, message = "Account name must not exceed 100 characters")
        String name,

        @NotNull(message = "Balance is required")
        @DecimalMin(value = "0.0", inclusive = true, message = "Balance must be greater than or equal to 0")
        BigDecimal balance,

        @Size(max = 500, message = "Description must not exceed 500 characters")
        String description
) {
}

