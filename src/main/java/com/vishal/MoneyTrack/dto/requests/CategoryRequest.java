package com.vishal.MoneyTrack.dto.requests;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record CategoryRequest(
        @NotBlank(message = "Category name is required")
        @Size(max = 100, message = "Category name must not exceed 100 characters")
        String name,

        @NotNull(message = "Budget is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "Budget must be greater than 0")
        BigDecimal budget,

        @Size(max = 500, message = "Description must not exceed 500 characters")
        String description
) {
}

