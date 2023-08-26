package com.project.yozmcafe.controller.dto.cafe;

import java.util.List;

import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.domain.cafe.Images;

public record CafeUpdateRequest(String name, String address, DetailRequest detail, int likeCount) {

    public Cafe toCafeWithId(final Long id, final List<String> images) {
        return new Cafe(id, name, address, new Images(images), detail.toDetail(), likeCount);
    }
}
