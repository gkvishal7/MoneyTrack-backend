package com.vishal.MoneyTrack.controllers;

import com.vishal.MoneyTrack.dto.requests.ExpenseTypeRequest;
import com.vishal.MoneyTrack.dto.responses.ApiResponse;
import com.vishal.MoneyTrack.dto.responses.ExpenseTypeResponse;
import com.vishal.MoneyTrack.services.ExpenseTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/expense-type")
@RequiredArgsConstructor
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
    public ResponseEntity<ApiResponse<List<ExpenseTypeResponse>>> getAll() {
        List<ExpenseTypeResponse> responses = service.getAll();
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @PutMapping("/{id}")
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

