package com.project.yozmcafe.controller.dto.cafe;

import com.project.yozmcafe.domain.cafe.Cafe;

public record CafeSearchResponse(long id, String name, String address, String image, int likeCount) {
    public static CafeSearchResponse from(final Cafe cafe) {
        return new CafeSearchResponse(
                cafe.getId(),
                cafe.getName(),
                cafe.getAddress(),
                cafe.getRepresentativeImage(),
                cafe.getLikeCount()
        );
    }
}
