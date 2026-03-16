package com.vishal.MoneyTrack.services;

import com.vishal.MoneyTrack.dto.requests.CategoryRequest;
import com.vishal.MoneyTrack.dto.responses.CategoryResponse;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    CategoryResponse create(CategoryRequest request);
    CategoryResponse getById(UUID id);
    List<CategoryResponse> getAll();
    CategoryResponse update(UUID id, CategoryRequest request);
    void delete(UUID id);
}

