package com.project.yozmcafe.controller.dto.cafe;

import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.domain.cafe.LikedCafe;

public record LikedCafeThumbnailResponse(Long cafeId, String imageUrl) {

    public static LikedCafeThumbnailResponse from(LikedCafe likedCafe) {
        final Cafe cafe = likedCafe.getCafe();
        return new LikedCafeThumbnailResponse(cafe.getId(), cafe.getRepresentativeImage());
    }
}
