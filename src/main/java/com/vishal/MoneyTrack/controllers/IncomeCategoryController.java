package com.vishal.MoneyTrack.controllers;

import com.vishal.MoneyTrack.dto.requests.IncomeCategoryRequest;
import com.vishal.MoneyTrack.dto.responses.ApiResponse;
import com.vishal.MoneyTrack.dto.responses.IncomeCategoryResponse;
import com.vishal.MoneyTrack.services.IncomeCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/income-category")
@RequiredArgsConstructor
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
    public ResponseEntity<ApiResponse<List<IncomeCategoryResponse>>> getAll() {
        List<IncomeCategoryResponse> responses = service.getAll();
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @PutMapping("/{id}")
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

