package com.vishal.MoneyTrack.services;

import com.vishal.MoneyTrack.dto.requests.IncomeCategoryRequest;
import com.vishal.MoneyTrack.dto.responses.IncomeCategoryResponse;
import com.vishal.MoneyTrack.dto.responses.PagedResponse;

import java.util.UUID;

public interface IncomeCategoryService {
    IncomeCategoryResponse create(IncomeCategoryRequest request);
    IncomeCategoryResponse getById(UUID id);
    PagedResponse<IncomeCategoryResponse> getAll(int page, int size, String sortBy, String sortDir);
    IncomeCategoryResponse update(UUID id, IncomeCategoryRequest request);
    void delete(UUID id);
}
