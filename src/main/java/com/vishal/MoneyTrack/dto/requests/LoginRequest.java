package com.vishal.MoneyTrack.dto.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "Email is required")
        @Email(message = "Email must be valid")
        String emailId,

        @NotBlank(message = "Password is required")
        String password
) {
}

