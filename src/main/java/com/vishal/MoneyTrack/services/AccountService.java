package com.vishal.MoneyTrack.services;

import com.vishal.MoneyTrack.dto.requests.AccountRequest;
import com.vishal.MoneyTrack.dto.requests.AccountUpdateRequest;
import com.vishal.MoneyTrack.dto.responses.AccountResponse;
import com.vishal.MoneyTrack.dto.responses.PagedResponse;

import java.math.BigDecimal;
import java.util.UUID;

public interface AccountService {
    AccountResponse create(AccountRequest request);
    AccountResponse getById(UUID id);
    PagedResponse<AccountResponse> getAll(int page, int size, String sortBy, String sortDir);
    AccountResponse update(UUID id, AccountUpdateRequest request);
    void delete(UUID id);
    AccountResponse updateBalance(UUID id, BigDecimal newBalance);
}
