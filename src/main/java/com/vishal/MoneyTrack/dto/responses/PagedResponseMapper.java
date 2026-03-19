package com.vishal.MoneyTrack.dto.responses;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;

public final class PagedResponseMapper {

    private PagedResponseMapper() {
    }

    public static <T, R> PagedResponse<R> toPagedResponse(Page<T> page, Function<T, R> mapper) {
        List<R> content = page.getContent().stream()
                .map(mapper)
                .toList();
        return new PagedResponse<>(
                content,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast()
        );
    }
}
