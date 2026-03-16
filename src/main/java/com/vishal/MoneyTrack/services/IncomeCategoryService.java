package com.vishal.MoneyTrack.services;

import com.vishal.MoneyTrack.dto.requests.IncomeCategoryRequest;
import com.vishal.MoneyTrack.dto.responses.IncomeCategoryResponse;

import java.util.List;
import java.util.UUID;

public interface IncomeCategoryService {
    IncomeCategoryResponse create(IncomeCategoryRequest request);
    IncomeCategoryResponse getById(UUID id);
    List<IncomeCategoryResponse> getAll();
    IncomeCategoryResponse update(UUID id, IncomeCategoryRequest request);
    void delete(UUID id);
}

