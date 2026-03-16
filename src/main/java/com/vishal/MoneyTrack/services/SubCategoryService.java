package com.vishal.MoneyTrack.services;

import com.vishal.MoneyTrack.dto.requests.SubCategoryRequest;
import com.vishal.MoneyTrack.dto.responses.SubCategoryResponse;

import java.util.List;
import java.util.UUID;

public interface SubCategoryService {
    SubCategoryResponse create(SubCategoryRequest request);
    SubCategoryResponse getById(UUID id);
    List<SubCategoryResponse> getAll();
    List<SubCategoryResponse> getByCategoryId(UUID categoryId);
    SubCategoryResponse update(UUID id, SubCategoryRequest request);
    void delete(UUID id);
}

