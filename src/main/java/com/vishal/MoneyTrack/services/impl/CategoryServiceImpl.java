package com.vishal.MoneyTrack.services.impl;

import com.vishal.MoneyTrack.dto.requests.CategoryRequest;
import com.vishal.MoneyTrack.dto.responses.CategoryResponse;
import com.vishal.MoneyTrack.dto.responses.PagedResponse;
import com.vishal.MoneyTrack.dto.responses.PagedResponseMapper;
import com.vishal.MoneyTrack.entities.Category;
import com.vishal.MoneyTrack.entities.User;
import com.vishal.MoneyTrack.exceptions.DuplicateResourceException;
import com.vishal.MoneyTrack.exceptions.ResourceNotFoundException;
import com.vishal.MoneyTrack.mappers.CategoryMapper;
import com.vishal.MoneyTrack.repo.CategoryRepository;
import com.vishal.MoneyTrack.repo.UserRepository;
import com.vishal.MoneyTrack.services.CategoryService;
import com.vishal.MoneyTrack.utils.PageableUtils;
import com.vishal.MoneyTrack.utils.SecurityUtils;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;
    private final UserRepository userRepository;
    private final CategoryMapper mapper;

    @Override
    @Transactional
    public CategoryResponse create(CategoryRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        if (repository.existsByNameAndUserId(request.name(), userId)) {
            throw new DuplicateResourceException("Category with name '" + request.name() + "' already exists for this user");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Category entity = mapper.toEntity(request);
        entity.setUser(user);
        Category saved = repository.save(entity);
        return mapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryResponse getById(UUID id) {
        UUID userId = SecurityUtils.getCurrentUserId();
        Category entity = repository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found or access denied"));
        return mapper.toResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<CategoryResponse> getAll(int page, int size, String sortBy, String sortDir) {
        UUID userId = SecurityUtils.getCurrentUserId();
        Pageable pageable = PageableUtils.buildPageable(page, size, sortBy, sortDir);
        Page<Category> categoryPage = repository.findByUserId(userId, pageable);
        return PagedResponseMapper.toPagedResponse(categoryPage, mapper::toResponse);
    }

    @Override
    @Transactional
    public CategoryResponse update(UUID id, CategoryRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        Category entity = repository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found or access denied"));

        if (!entity.getName().equals(request.name()) && repository.existsByNameAndUserId(request.name(), userId)) {
            throw new DuplicateResourceException("Category with name '" + request.name() + "' already exists for this user");
        }

        mapper.updateEntity(entity, request);
        Category updated = repository.save(entity);
        return mapper.toResponse(updated);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        UUID userId = SecurityUtils.getCurrentUserId();
        Category entity = repository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found or access denied"));
        repository.delete(entity);
    }
}
