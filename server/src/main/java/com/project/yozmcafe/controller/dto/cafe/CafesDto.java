package com.project.yozmcafe.controller.dto.cafe;

import java.util.List;

import com.project.yozmcafe.domain.cafe.Cafe;

public record CafesDto(List<CafeDto> cafes) {

    public static CafesDto of(List<Cafe> cafes) {
        return new CafesDto(cafes.stream()
                .map(CafeDto::of)
                .toList()
        );
    }
}
