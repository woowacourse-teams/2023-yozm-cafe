package com.project.yozmcafe.controller.dto.cafe;

import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.domain.cafe.UnViewedCafe;

import java.util.List;

public record CafeResponse(Long id, String name, String address, List<String> images, boolean isLiked, int likeCount,
                           DetailResponse detail) {

    private static final boolean UN_LOGGED_IN_USER_LIKED = false;

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

    public static CafeResponse fromLoggedInUser(final UnViewedCafe unViewedCafe, final boolean isLiked) {
        return fromLoggedInUser(unViewedCafe.getCafe(), isLiked);
    }

    public static CafeResponse fromUnLoggedInUser(final Cafe cafe) {
        return fromLoggedInUser(cafe, UN_LOGGED_IN_USER_LIKED);
    }
}
