package com.project.yozmcafe.controller.dto.cafe;

import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.domain.cafe.LikedCafe;

public record LikedCafeResponse(Long cafeId, String imageUrl) {

    public static LikedCafeResponse from(LikedCafe likedCafe) {
        final Cafe cafe = likedCafe.getCafe();
        return new LikedCafeResponse(cafe.getId(), cafe.getRepresentativeImage());
    }
}
