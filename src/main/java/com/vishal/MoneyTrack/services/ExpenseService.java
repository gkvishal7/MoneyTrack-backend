package com.vishal.MoneyTrack.services;

import com.vishal.MoneyTrack.dto.requests.ExpenseRequest;
import com.vishal.MoneyTrack.dto.responses.ExpenseResponse;
import com.vishal.MoneyTrack.dto.responses.PagedResponse;

import java.time.LocalDate;
import java.util.UUID;

public interface ExpenseService {
    ExpenseResponse create(ExpenseRequest request);
    ExpenseResponse getById(UUID id);
    PagedResponse<ExpenseResponse> getAll(int page, int size, String sortBy, String sortDir);
    PagedResponse<ExpenseResponse> getByDateRange(LocalDate startDate, LocalDate endDate, int page, int size, String sortBy, String sortDir);
    PagedResponse<ExpenseResponse> getByAccountId(UUID accountId, int page, int size, String sortBy, String sortDir);
    PagedResponse<ExpenseResponse> getByCategoryId(UUID categoryId, int page, int size, String sortBy, String sortDir);
    PagedResponse<ExpenseResponse> getBySubCategoryId(UUID subCategoryId, int page, int size, String sortBy, String sortDir);
    ExpenseResponse update(UUID id, ExpenseRequest request);
    void delete(UUID id);
}
