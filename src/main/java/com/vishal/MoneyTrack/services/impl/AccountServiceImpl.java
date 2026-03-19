package com.vishal.MoneyTrack.services.impl;

import com.vishal.MoneyTrack.dto.requests.AccountRequest;
import com.vishal.MoneyTrack.dto.requests.AccountUpdateRequest;
import com.vishal.MoneyTrack.dto.responses.AccountResponse;
import com.vishal.MoneyTrack.dto.responses.PagedResponse;
import com.vishal.MoneyTrack.dto.responses.PagedResponseMapper;
import com.vishal.MoneyTrack.entities.Account;
import com.vishal.MoneyTrack.entities.User;
import com.vishal.MoneyTrack.exceptions.DuplicateResourceException;
import com.vishal.MoneyTrack.exceptions.ResourceNotFoundException;
import com.vishal.MoneyTrack.mappers.AccountMapper;
import com.vishal.MoneyTrack.repo.AccountRepository;
import com.vishal.MoneyTrack.repo.UserRepository;
import com.vishal.MoneyTrack.services.AccountService;
import com.vishal.MoneyTrack.utils.PageableUtils;
import com.vishal.MoneyTrack.utils.SecurityUtils;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository;
    private final UserRepository userRepository;
    private final AccountMapper mapper;

    @Override
    @Transactional
    public AccountResponse create(AccountRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        if (repository.existsByNameAndUserId(request.name(), userId)) {
            throw new DuplicateResourceException("Account with name '" + request.name() + "' already exists for this user");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Account entity = mapper.toEntity(request);
        entity.setUser(user);
        Account saved = repository.save(entity);
        return mapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public AccountResponse getById(UUID id) {
        UUID userId = SecurityUtils.getCurrentUserId();
        Account entity = repository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found or access denied"));
        return mapper.toResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<AccountResponse> getAll(int page, int size, String sortBy, String sortDir) {
        UUID userId = SecurityUtils.getCurrentUserId();
        Pageable pageable = PageableUtils.buildPageable(page, size, sortBy, sortDir);
        Page<Account> accountPage = repository.findByUserId(userId, pageable);
        return PagedResponseMapper.toPagedResponse(accountPage, mapper::toResponse);
    }

    @Override
    @Transactional
    public AccountResponse update(UUID id, AccountUpdateRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        Account entity = repository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found or access denied"));

        if (!entity.getName().equals(request.name()) && repository.existsByNameAndUserId(request.name(), userId)) {
            throw new DuplicateResourceException("Account with name '" + request.name() + "' already exists for this user");
        }

        mapper.updateEntity(entity, request);
        Account updated = repository.save(entity);
        return mapper.toResponse(updated);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        UUID userId = SecurityUtils.getCurrentUserId();
        Account entity = repository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found or access denied"));
        repository.delete(entity);
    }

    @Override
    @Transactional
    public AccountResponse updateBalance(UUID id, BigDecimal newBalance) {
        UUID userId = SecurityUtils.getCurrentUserId();
        Account entity = repository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found or access denied"));
        entity.setBalance(newBalance);
        Account updated = repository.save(entity);
        return mapper.toResponse(updated);
    }
}
