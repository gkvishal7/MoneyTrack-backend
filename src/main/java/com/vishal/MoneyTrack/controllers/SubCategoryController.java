package com.vishal.MoneyTrack.controllers;

import com.vishal.MoneyTrack.dto.requests.SubCategoryRequest;
import com.vishal.MoneyTrack.dto.responses.ApiResponse;
import com.vishal.MoneyTrack.dto.responses.SubCategoryResponse;
import com.vishal.MoneyTrack.services.SubCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/sub-category")
@RequiredArgsConstructor
public class SubCategoryController {

    private final SubCategoryService service;

    @PostMapping
    public ResponseEntity<ApiResponse<SubCategoryResponse>> create(@Valid @RequestBody SubCategoryRequest request) {
        SubCategoryResponse response = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(response, "Sub category created successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SubCategoryResponse>> getById(@PathVariable UUID id) {
        SubCategoryResponse response = service.getById(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<SubCategoryResponse>>> getAll() {
        List<SubCategoryResponse> responses = service.getAll();
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse<List<SubCategoryResponse>>> getByCategoryId(@PathVariable UUID categoryId) {
        List<SubCategoryResponse> responses = service.getByCategoryId(categoryId);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SubCategoryResponse>> update(
            @PathVariable UUID id,
            @Valid @RequestBody SubCategoryRequest request) {
        SubCategoryResponse response = service.update(id, request);
        return ResponseEntity.ok(ApiResponse.success(response, "Sub category updated successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Sub category deleted successfully"));
    }
}

