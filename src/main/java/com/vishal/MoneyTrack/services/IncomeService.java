package com.vishal.MoneyTrack.services;

import com.vishal.MoneyTrack.dto.requests.IncomeRequest;
import com.vishal.MoneyTrack.dto.responses.IncomeResponse;
import com.vishal.MoneyTrack.dto.responses.PagedResponse;

import java.time.LocalDate;
import java.util.UUID;

public interface IncomeService {
    IncomeResponse create(IncomeRequest request);
    IncomeResponse getById(UUID id);
    PagedResponse<IncomeResponse> getAll(int page, int size, String sortBy, String sortDir);
    PagedResponse<IncomeResponse> getByDateRange(LocalDate startDate, LocalDate endDate, int page, int size, String sortBy, String sortDir);
    PagedResponse<IncomeResponse> getByAccountId(UUID accountId, int page, int size, String sortBy, String sortDir);
    IncomeResponse update(UUID id, IncomeRequest request);
    void delete(UUID id);
}
