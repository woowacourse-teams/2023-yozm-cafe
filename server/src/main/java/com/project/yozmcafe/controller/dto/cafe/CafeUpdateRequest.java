package com.project.yozmcafe.controller.dto.cafe;

import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.domain.cafe.Images;

import java.util.List;

public record CafeUpdateRequest(String name, String address, List<String> images, DetailRequest detailRequest,
                                int likeCount) {

    public Cafe toCafeWithId(final Long id) {
        return new Cafe(id, name, address, new Images(images), detailRequest.toDetail(), likeCount);
    }
}
