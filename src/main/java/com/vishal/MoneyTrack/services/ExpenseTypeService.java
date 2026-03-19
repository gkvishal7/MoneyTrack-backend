package com.vishal.MoneyTrack.services;

import com.vishal.MoneyTrack.dto.requests.ExpenseTypeRequest;
import com.vishal.MoneyTrack.dto.responses.ExpenseTypeResponse;
import com.vishal.MoneyTrack.dto.responses.PagedResponse;

import java.util.UUID;

public interface ExpenseTypeService {
    ExpenseTypeResponse create(ExpenseTypeRequest request);
    ExpenseTypeResponse getById(UUID id);
    PagedResponse<ExpenseTypeResponse> getAll(int page, int size, String sortBy, String sortDir);
    ExpenseTypeResponse update(UUID id, ExpenseTypeRequest request);
    void delete(UUID id);
}
