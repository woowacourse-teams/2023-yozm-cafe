package com.project.yozmcafe.controller.dto.cafe;

import java.util.List;

import com.project.yozmcafe.domain.cafe.Cafe;

public record CafeDto(Long id, String name, String address, List<String> images, boolean isLiked, int likeCount,
                      DetailDto detail) {

    public static CafeDto of(Cafe cafe) {
        return new CafeDto(
                cafe.getId(),
                cafe.getName(),
                cafe.getAddress(),
                cafe.getImages().getUrls(),
                false,
                cafe.getLikeCount(),
                DetailDto.of(cafe.getDetail()));
    }
}
