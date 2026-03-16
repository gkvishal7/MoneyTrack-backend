package com.vishal.MoneyTrack.controllers;

import com.vishal.MoneyTrack.dto.requests.IncomeRequest;
import com.vishal.MoneyTrack.dto.responses.ApiResponse;
import com.vishal.MoneyTrack.dto.responses.IncomeResponse;
import com.vishal.MoneyTrack.services.IncomeService;
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
@RequestMapping("/api/income")
@RequiredArgsConstructor
public class IncomeController {

    private final IncomeService service;

    @PostMapping
    public ResponseEntity<ApiResponse<IncomeResponse>> create(@Valid @RequestBody IncomeRequest request) {
        IncomeResponse response = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(response, "Income created successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<IncomeResponse>> getById(@PathVariable UUID id) {
        IncomeResponse response = service.getById(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<IncomeResponse>>> getAll() {
        List<IncomeResponse> responses = service.getAll();
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @GetMapping("/date-range")
    public ResponseEntity<ApiResponse<List<IncomeResponse>>> getByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<IncomeResponse> responses = service.getByDateRange(startDate, endDate);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<ApiResponse<List<IncomeResponse>>> getByAccountId(@PathVariable UUID accountId) {
        List<IncomeResponse> responses = service.getByAccountId(accountId);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<IncomeResponse>> update(
            @PathVariable UUID id,
            @Valid @RequestBody IncomeRequest request) {
        IncomeResponse response = service.update(id, request);
        return ResponseEntity.ok(ApiResponse.success(response, "Income updated successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Income deleted successfully"));
    }
}

