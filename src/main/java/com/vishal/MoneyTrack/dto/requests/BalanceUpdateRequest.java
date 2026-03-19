package com.vishal.MoneyTrack.dto.requests;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record BalanceUpdateRequest(
        @NotNull(message = "Balance is required")
        @DecimalMin(value = "0.0", inclusive = true, message = "Balance must be greater than or equal to 0")
        BigDecimal balance
) {
}
