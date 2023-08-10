package com.project.yozmcafe.controller.dto.cafe;

import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.domain.cafe.LikedCafe;

import java.util.List;

public record LikedCafeDetailResponse(Long id, String name, String address, List<String> images, boolean isLiked,
                                      int likeCount, DetailResponse detail) {

    private static final boolean IS_LIKED_CAFE = true;

    public static LikedCafeDetailResponse fromLikedCafe(final LikedCafe likedCafe) {
        return of(likedCafe.getCafe(), IS_LIKED_CAFE);
    }

    private static LikedCafeDetailResponse of(final Cafe cafe, final boolean isLiked) {
        return new LikedCafeDetailResponse(
                cafe.getId(),
                cafe.getName(),
                cafe.getAddress(),
                cafe.getImages().getUrls(),
                isLiked,
                cafe.getLikeCount(),
                DetailResponse.from(cafe.getDetail()));
    }
}
