package com.vishal.MoneyTrack.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AccountUpdateRequest(
        @NotBlank(message = "Account name is required")
        @Size(max = 100, message = "Account name must not exceed 100 characters")
        String name,

        @Size(max = 500, message = "Description must not exceed 500 characters")
        String description
) {
}
