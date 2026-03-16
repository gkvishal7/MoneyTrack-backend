package com.vishal.MoneyTrack.controllers;

import com.vishal.MoneyTrack.dto.requests.AccountRequest;
import com.vishal.MoneyTrack.dto.responses.AccountResponse;
import com.vishal.MoneyTrack.dto.responses.ApiResponse;
import com.vishal.MoneyTrack.services.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService service;

    @PostMapping
    public ResponseEntity<ApiResponse<AccountResponse>> create(@Valid @RequestBody AccountRequest request) {
        AccountResponse response = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(response, "Account created successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AccountResponse>> getById(@PathVariable UUID id) {
        AccountResponse response = service.getById(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<AccountResponse>>> getAll() {
        List<AccountResponse> responses = service.getAll();
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AccountResponse>> update(
            @PathVariable UUID id,
            @Valid @RequestBody AccountRequest request) {
        AccountResponse response = service.update(id, request);
        return ResponseEntity.ok(ApiResponse.success(response, "Account updated successfully"));
    }

    @PatchMapping("/{id}/balance")
    public ResponseEntity<ApiResponse<AccountResponse>> updateBalance(
            @PathVariable UUID id,
            @Valid @RequestBody BalanceUpdateRequest request) {
        AccountResponse response = service.updateBalance(id, request.balance());
        return ResponseEntity.ok(ApiResponse.success(response, "Account balance updated successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Account deleted successfully"));
    }

    public record BalanceUpdateRequest(
            @jakarta.validation.constraints.NotNull(message = "Balance is required")
            @jakarta.validation.constraints.DecimalMin(value = "0.0", inclusive = true, message = "Balance must be greater than or equal to 0")
            BigDecimal balance
    ) {
    }
}
