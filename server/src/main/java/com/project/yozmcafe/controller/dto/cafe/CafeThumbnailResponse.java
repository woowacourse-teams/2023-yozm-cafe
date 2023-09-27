package com.project.yozmcafe.controller.dto.cafe;

import com.project.yozmcafe.domain.cafe.Cafe;

public record CafeThumbnailResponse(Long cafeId, String imageUrl) {

    public static CafeThumbnailResponse from(final Cafe cafe) {
        return new CafeThumbnailResponse(cafe.getId(), cafe.getRepresentativeImage());
    }
}
