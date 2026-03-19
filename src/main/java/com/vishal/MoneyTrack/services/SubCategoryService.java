package com.vishal.MoneyTrack.services;

import com.vishal.MoneyTrack.dto.requests.SubCategoryRequest;
import com.vishal.MoneyTrack.dto.responses.PagedResponse;
import com.vishal.MoneyTrack.dto.responses.SubCategoryResponse;

import java.util.UUID;

public interface SubCategoryService {
    SubCategoryResponse create(SubCategoryRequest request);
    SubCategoryResponse getById(UUID id);
    PagedResponse<SubCategoryResponse> getAll(int page, int size, String sortBy, String sortDir);
    PagedResponse<SubCategoryResponse> getByCategoryId(UUID categoryId, int page, int size, String sortBy, String sortDir);
    SubCategoryResponse update(UUID id, SubCategoryRequest request);
    void delete(UUID id);
}
