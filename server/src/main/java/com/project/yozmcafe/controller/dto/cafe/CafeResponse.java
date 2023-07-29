package com.project.yozmcafe.controller.dto.cafe;

import java.util.List;

import com.project.yozmcafe.domain.cafe.Cafe;

public record CafeResponse(Long id, String name, String address, List<String> images, boolean isLiked, int likeCount,
                           DetailResponse detail) {
    public static CafeResponse fromLoggedInUser(final Cafe cafe, final boolean isLiked) {
        return new CafeResponse(
                cafe.getId(),
                cafe.getName(),
                cafe.getAddress(),
                cafe.getImages().getUrls(),
                isLiked,
                cafe.getLikeCount(),
                DetailResponse.from(cafe.getDetail()));
    }

    public static CafeResponse fromUnLoggedInUser(final Cafe cafe) {
        return new CafeResponse(
                cafe.getId(),
                cafe.getName(),
                cafe.getAddress(),
                cafe.getImages().getUrls(),
                false,
                cafe.getLikeCount(),
                DetailResponse.from(cafe.getDetail()));
    }
}
