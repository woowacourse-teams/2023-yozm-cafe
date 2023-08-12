package com.project.yozmcafe.controller.dto.cafe;

import com.project.yozmcafe.domain.cafe.Cafe;

public record CafeRankResponse(int rank, long id, String name, String address, String image, int likeCount) {
    public static CafeRankResponse of(final int rank, final Cafe cafe) {
        return new CafeRankResponse(
                rank,
                cafe.getId(),
                cafe.getName(),
                cafe.getAddress(),
                cafe.getImages().getUrls().get(0),
                cafe.getLikeCount()
        );
    }
}
