package com.vishal.MoneyTrack.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record IncomeCategoryRequest(
        @NotBlank(message = "Category name is required")
        @Size(max = 100, message = "Category name must not exceed 100 characters")
        String categoryName,

        @Size(max = 500, message = "Description must not exceed 500 characters")
        String description
) {
}

