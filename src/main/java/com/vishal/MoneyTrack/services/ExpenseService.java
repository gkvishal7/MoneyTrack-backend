package com.vishal.MoneyTrack.services;

import com.vishal.MoneyTrack.dto.requests.ExpenseRequest;
import com.vishal.MoneyTrack.dto.responses.ExpenseResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ExpenseService {
    ExpenseResponse create(ExpenseRequest request);
    ExpenseResponse getById(UUID id);
    List<ExpenseResponse> getAll();
    List<ExpenseResponse> getByDateRange(LocalDate startDate, LocalDate endDate);
    List<ExpenseResponse> getByAccountId(UUID accountId);
    List<ExpenseResponse> getByCategoryId(UUID categoryId);
    List<ExpenseResponse> getBySubCategoryId(UUID subCategoryId);
    ExpenseResponse update(UUID id, ExpenseRequest request);
    void delete(UUID id);
}

