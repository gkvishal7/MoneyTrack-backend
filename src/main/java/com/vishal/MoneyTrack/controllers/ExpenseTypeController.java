package com.vishal.MoneyTrack.controllers;

import com.vishal.MoneyTrack.dto.requests.ExpenseTypeRequest;
import com.vishal.MoneyTrack.dto.responses.ApiResponse;
import com.vishal.MoneyTrack.dto.responses.ExpenseTypeResponse;
import com.vishal.MoneyTrack.dto.responses.PagedResponse;
import com.vishal.MoneyTrack.services.ExpenseTypeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/expense-type")
@RequiredArgsConstructor
@Validated
public class ExpenseTypeController {

    private final ExpenseTypeService service;

    @PostMapping
    public ResponseEntity<ApiResponse<ExpenseTypeResponse>> create(@Valid @RequestBody ExpenseTypeRequest request) {
        ExpenseTypeResponse response = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(response, "Expense type created successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ExpenseTypeResponse>> getById(@PathVariable UUID id) {
        ExpenseTypeResponse response = service.getById(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PagedResponse<ExpenseTypeResponse>>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") @Max(200) int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        PagedResponse<ExpenseTypeResponse> responses = service.getAll(page, size, sortBy, sortDir);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<ExpenseTypeResponse>> update(
            @PathVariable UUID id,
            @Valid @RequestBody ExpenseTypeRequest request) {
        ExpenseTypeResponse response = service.update(id, request);
        return ResponseEntity.ok(ApiResponse.success(response, "Expense type updated successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Expense type deleted successfully"));
    }
}
