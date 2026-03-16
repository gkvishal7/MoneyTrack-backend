package com.vishal.MoneyTrack.controllers;

import com.vishal.MoneyTrack.dto.requests.ExpenseRequest;
import com.vishal.MoneyTrack.dto.responses.ApiResponse;
import com.vishal.MoneyTrack.dto.responses.ExpenseResponse;
import com.vishal.MoneyTrack.services.ExpenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/expense")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService service;

    @PostMapping
    public ResponseEntity<ApiResponse<ExpenseResponse>> create(@Valid @RequestBody ExpenseRequest request) {
        ExpenseResponse response = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(response, "Expense created successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ExpenseResponse>> getById(@PathVariable UUID id) {
        ExpenseResponse response = service.getById(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ExpenseResponse>>> getAll() {
        List<ExpenseResponse> responses = service.getAll();
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @GetMapping("/date-range")
    public ResponseEntity<ApiResponse<List<ExpenseResponse>>> getByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<ExpenseResponse> responses = service.getByDateRange(startDate, endDate);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<ApiResponse<List<ExpenseResponse>>> getByAccountId(@PathVariable UUID accountId) {
        List<ExpenseResponse> responses = service.getByAccountId(accountId);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse<List<ExpenseResponse>>> getByCategoryId(@PathVariable UUID categoryId) {
        List<ExpenseResponse> responses = service.getByCategoryId(categoryId);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @GetMapping("/sub-category/{subCategoryId}")
    public ResponseEntity<ApiResponse<List<ExpenseResponse>>> getBySubCategoryId(@PathVariable UUID subCategoryId) {
        List<ExpenseResponse> responses = service.getBySubCategoryId(subCategoryId);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ExpenseResponse>> update(
            @PathVariable UUID id,
            @Valid @RequestBody ExpenseRequest request) {
        ExpenseResponse response = service.update(id, request);
        return ResponseEntity.ok(ApiResponse.success(response, "Expense updated successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Expense deleted successfully"));
    }
}

