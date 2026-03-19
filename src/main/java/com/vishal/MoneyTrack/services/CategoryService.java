package com.vishal.MoneyTrack.services;

import com.vishal.MoneyTrack.dto.requests.CategoryRequest;
import com.vishal.MoneyTrack.dto.responses.CategoryResponse;
import com.vishal.MoneyTrack.dto.responses.PagedResponse;

import java.util.UUID;

public interface CategoryService {
    CategoryResponse create(CategoryRequest request);
    CategoryResponse getById(UUID id);
    PagedResponse<CategoryResponse> getAll(int page, int size, String sortBy, String sortDir);
    CategoryResponse update(UUID id, CategoryRequest request);
    void delete(UUID id);
}
