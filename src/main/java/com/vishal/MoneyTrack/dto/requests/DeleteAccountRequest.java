package com.vishal.MoneyTrack.dto.requests;

import jakarta.validation.constraints.NotBlank;

public record DeleteAccountRequest(
        @NotBlank(message = "Password is required for account deletion")
        String password
) {
}

