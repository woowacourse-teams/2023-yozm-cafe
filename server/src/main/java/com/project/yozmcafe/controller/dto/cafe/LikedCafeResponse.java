package com.project.yozmcafe.controller.dto.cafe;

import com.project.yozmcafe.domain.cafe.Cafe;

import java.util.List;

public record LikedCafeResponse(Long id, String name, String address, List<String> images,
                                boolean isLiked, int likeCount, DetailResponse detail) {

    private static final boolean IS_LIKED_CAFE = true;

    public static LikedCafeResponse from(final Cafe cafe) {
        return new LikedCafeResponse(
                cafe.getId(),
                cafe.getName(),
                cafe.getAddress(),
                cafe.getImages().getUrls(),
                IS_LIKED_CAFE,
                cafe.getLikeCount(),
                DetailResponse.from(cafe.getDetail()));
    }
}
