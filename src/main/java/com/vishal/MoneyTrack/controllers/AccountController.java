package com.vishal.MoneyTrack.controllers;

import com.vishal.MoneyTrack.dto.requests.AccountRequest;
import com.vishal.MoneyTrack.dto.requests.AccountUpdateRequest;
import com.vishal.MoneyTrack.dto.requests.BalanceUpdateRequest;
import com.vishal.MoneyTrack.dto.responses.AccountResponse;
import com.vishal.MoneyTrack.dto.responses.ApiResponse;
import com.vishal.MoneyTrack.dto.responses.PagedResponse;
import com.vishal.MoneyTrack.services.AccountService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
@Validated
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
    public ResponseEntity<ApiResponse<PagedResponse<AccountResponse>>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") @Max(200) int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        PagedResponse<AccountResponse> responses = service.getAll(page, size, sortBy, sortDir);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<AccountResponse>> update(
            @PathVariable UUID id,
            @Valid @RequestBody AccountUpdateRequest request) {
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
}
