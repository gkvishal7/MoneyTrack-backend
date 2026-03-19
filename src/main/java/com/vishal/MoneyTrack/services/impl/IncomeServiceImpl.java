package com.vishal.MoneyTrack.services.impl;

import com.vishal.MoneyTrack.dto.requests.IncomeRequest;
import com.vishal.MoneyTrack.dto.responses.IncomeResponse;
import com.vishal.MoneyTrack.dto.responses.PagedResponse;
import com.vishal.MoneyTrack.dto.responses.PagedResponseMapper;
import com.vishal.MoneyTrack.entities.Account;
import com.vishal.MoneyTrack.entities.Income;
import com.vishal.MoneyTrack.entities.IncomeCategory;
import com.vishal.MoneyTrack.entities.User;
import com.vishal.MoneyTrack.exceptions.ResourceNotFoundException;
import com.vishal.MoneyTrack.mappers.IncomeMapper;
import com.vishal.MoneyTrack.repo.AccountRepository;
import com.vishal.MoneyTrack.repo.IncomeCategoryRepository;
import com.vishal.MoneyTrack.repo.IncomeRepository;
import com.vishal.MoneyTrack.repo.UserRepository;
import com.vishal.MoneyTrack.services.IncomeService;
import com.vishal.MoneyTrack.utils.PageableUtils;
import com.vishal.MoneyTrack.utils.SecurityUtils;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class IncomeServiceImpl implements IncomeService {

    private final IncomeRepository incomeRepository;
    private final AccountRepository accountRepository;
    private final IncomeCategoryRepository incomeCategoryRepository;
    private final UserRepository userRepository;
    private final IncomeMapper mapper;

    @Override
    @Transactional
    public IncomeResponse create(IncomeRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();

        Account account = accountRepository.findByIdAndUserId(request.accountId(), userId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found or access denied"));

        IncomeCategory incomeCategory = incomeCategoryRepository.findByIdAndUserId(request.incomeCategoryId(), userId)
                .orElseThrow(() -> new ResourceNotFoundException("Income category not found or access denied"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Income entity = mapper.toEntity(request);
        entity.setAccount(account);
        entity.setIncomeCategory(incomeCategory);
        entity.setUser(user);

        Income saved = incomeRepository.save(entity);

        // Update account balance
        BigDecimal newBalance = account.getBalance().add(request.amount());
        account.setBalance(newBalance);
        accountRepository.save(account);

        return mapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public IncomeResponse getById(UUID id) {
        UUID userId = SecurityUtils.getCurrentUserId();
        Income entity = incomeRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Income not found or access denied"));
        return mapper.toResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<IncomeResponse> getAll(int page, int size, String sortBy, String sortDir) {
        UUID userId = SecurityUtils.getCurrentUserId();
        Pageable pageable = PageableUtils.buildPageable(page, size, sortBy, sortDir);
        Page<Income> incomePage = incomeRepository.findByUserId(userId, pageable);
        return PagedResponseMapper.toPagedResponse(incomePage, mapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<IncomeResponse> getByDateRange(LocalDate startDate, LocalDate endDate, int page, int size, String sortBy, String sortDir) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("startDate must be before or equal to endDate");
        }
        UUID userId = SecurityUtils.getCurrentUserId();
        Pageable pageable = PageableUtils.buildPageable(page, size, sortBy, sortDir);
        Page<Income> incomePage = incomeRepository.findIncomesByDateRange(startDate, endDate, userId, pageable);
        return PagedResponseMapper.toPagedResponse(incomePage, mapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<IncomeResponse> getByAccountId(UUID accountId, int page, int size, String sortBy, String sortDir) {
        UUID userId = SecurityUtils.getCurrentUserId();
        Pageable pageable = PageableUtils.buildPageable(page, size, sortBy, sortDir);
        Page<Income> incomePage = incomeRepository.findByAccountIdAndUserId(accountId, userId, pageable);
        return PagedResponseMapper.toPagedResponse(incomePage, mapper::toResponse);
    }

    @Override
    @Transactional
    public IncomeResponse update(UUID id, IncomeRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        Income entity = incomeRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Income not found or access denied"));

        Account oldAccount = entity.getAccount();
        BigDecimal oldAmount = entity.getAmount();

        Account newAccount = accountRepository.findByIdAndUserId(request.accountId(), userId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found or access denied"));

        IncomeCategory incomeCategory = incomeCategoryRepository.findByIdAndUserId(request.incomeCategoryId(), userId)
                .orElseThrow(() -> new ResourceNotFoundException("Income category not found or access denied"));

        mapper.updateEntity(entity, request);
        entity.setAccount(newAccount);
        entity.setIncomeCategory(incomeCategory);

        Income updated = incomeRepository.save(entity);

        // Update account balances
        if (!oldAccount.getId().equals(newAccount.getId())) {
            // Different accounts - revert old, apply new
            oldAccount.setBalance(oldAccount.getBalance().subtract(oldAmount));
            accountRepository.save(oldAccount);
            newAccount.setBalance(newAccount.getBalance().add(request.amount()));
            accountRepository.save(newAccount);
        } else {
            // Same account - adjust difference
            BigDecimal difference = request.amount().subtract(oldAmount);
            newAccount.setBalance(newAccount.getBalance().add(difference));
            accountRepository.save(newAccount);
        }

        return mapper.toResponse(updated);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        UUID userId = SecurityUtils.getCurrentUserId();
        Income entity = incomeRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Income not found or access denied"));

        Account account = entity.getAccount();
        account.setBalance(account.getBalance().subtract(entity.getAmount()));
        accountRepository.save(account);

        incomeRepository.delete(entity);
    }
}
