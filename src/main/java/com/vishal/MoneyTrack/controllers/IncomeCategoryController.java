package com.vishal.MoneyTrack.controllers;

import com.vishal.MoneyTrack.dto.requests.IncomeCategoryRequest;
import com.vishal.MoneyTrack.dto.responses.ApiResponse;
import com.vishal.MoneyTrack.dto.responses.IncomeCategoryResponse;
import com.vishal.MoneyTrack.dto.responses.PagedResponse;
import com.vishal.MoneyTrack.services.IncomeCategoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/income-category")
@RequiredArgsConstructor
@Validated
public class IncomeCategoryController {

    private final IncomeCategoryService service;

    @PostMapping
    public ResponseEntity<ApiResponse<IncomeCategoryResponse>> create(@Valid @RequestBody IncomeCategoryRequest request) {
        IncomeCategoryResponse response = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(response, "Income category created successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<IncomeCategoryResponse>> getById(@PathVariable UUID id) {
        IncomeCategoryResponse response = service.getById(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PagedResponse<IncomeCategoryResponse>>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") @Max(200) int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        PagedResponse<IncomeCategoryResponse> responses = service.getAll(page, size, sortBy, sortDir);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<IncomeCategoryResponse>> update(
            @PathVariable UUID id,
            @Valid @RequestBody IncomeCategoryRequest request) {
        IncomeCategoryResponse response = service.update(id, request);
        return ResponseEntity.ok(ApiResponse.success(response, "Income category updated successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Income category deleted successfully"));
    }
}
