package com.vishal.MoneyTrack.controllers;

import com.vishal.MoneyTrack.dto.requests.SubCategoryRequest;
import com.vishal.MoneyTrack.dto.responses.ApiResponse;
import com.vishal.MoneyTrack.dto.responses.PagedResponse;
import com.vishal.MoneyTrack.dto.responses.SubCategoryResponse;
import com.vishal.MoneyTrack.services.SubCategoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/sub-category")
@RequiredArgsConstructor
@Validated
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
    public ResponseEntity<ApiResponse<PagedResponse<SubCategoryResponse>>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") @Max(200) int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        PagedResponse<SubCategoryResponse> responses = service.getAll(page, size, sortBy, sortDir);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse<PagedResponse<SubCategoryResponse>>> getByCategoryId(
            @PathVariable UUID categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") @Max(200) int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        PagedResponse<SubCategoryResponse> responses = service.getByCategoryId(categoryId, page, size, sortBy, sortDir);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @PatchMapping("/{id}")
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
