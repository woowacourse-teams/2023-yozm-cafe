package com.project.yozmcafe.controller.dto.cafe;

import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.domain.cafe.LikedCafe;

public record CafeThumbnailResponse(Long cafeId, String imageUrl) {

    public static CafeThumbnailResponse from(LikedCafe likedCafe) {
        final Cafe cafe = likedCafe.getCafe();
        return new CafeThumbnailResponse(cafe.getId(), cafe.getRepresentativeImage());
    }
}
