package com.vishal.MoneyTrack.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ExpenseTypeRequest(
        @NotBlank(message = "Type name is required")
        @Size(max = 100, message = "Type name must not exceed 100 characters")
        String typeName,

        @Size(max = 500, message = "Description must not exceed 500 characters")
        String description
) {
}

