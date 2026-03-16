package com.vishal.MoneyTrack.services.impl;

import com.vishal.MoneyTrack.config.SecurityUtils;
import com.vishal.MoneyTrack.dto.requests.IncomeCategoryRequest;
import com.vishal.MoneyTrack.dto.responses.IncomeCategoryResponse;
import com.vishal.MoneyTrack.entities.IncomeCategory;
import com.vishal.MoneyTrack.entities.User;
import com.vishal.MoneyTrack.exceptions.DuplicateResourceException;
import com.vishal.MoneyTrack.exceptions.ResourceNotFoundException;
import com.vishal.MoneyTrack.mappers.IncomeCategoryMapper;
import com.vishal.MoneyTrack.repo.IncomeCategoryRepository;
import com.vishal.MoneyTrack.repo.UserRepository;
import com.vishal.MoneyTrack.services.IncomeCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class IncomeCategoryServiceImpl implements IncomeCategoryService {

    private final IncomeCategoryRepository repository;
    private final UserRepository userRepository;
    private final IncomeCategoryMapper mapper;

    @Override
    @Transactional
    public IncomeCategoryResponse create(IncomeCategoryRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        if (repository.existsByCategoryNameAndUserId(request.categoryName(), userId)) {
            throw new DuplicateResourceException("Income category with name '" + request.categoryName() + "' already exists for this user");
        }
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        IncomeCategory entity = mapper.toEntity(request);
        entity.setUser(user);
        IncomeCategory saved = repository.save(entity);
        return mapper.toResponse(saved);
    }

    @Override
    public IncomeCategoryResponse getById(UUID id) {
        UUID userId = SecurityUtils.getCurrentUserId();
        IncomeCategory entity = repository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Income category not found or access denied"));
        return mapper.toResponse(entity);
    }

    @Override
    public List<IncomeCategoryResponse> getAll() {
        UUID userId = SecurityUtils.getCurrentUserId();
        return repository.findByUserId(userId).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public IncomeCategoryResponse update(UUID id, IncomeCategoryRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        IncomeCategory entity = repository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Income category not found or access denied"));
        
        if (!entity.getCategoryName().equals(request.categoryName()) && 
            repository.existsByCategoryNameAndUserId(request.categoryName(), userId)) {
            throw new DuplicateResourceException("Income category with name '" + request.categoryName() + "' already exists for this user");
        }
        
        mapper.updateEntity(entity, request);
        IncomeCategory updated = repository.save(entity);
        return mapper.toResponse(updated);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        UUID userId = SecurityUtils.getCurrentUserId();
        IncomeCategory entity = repository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Income category not found or access denied"));
        repository.delete(entity);
    }
}
