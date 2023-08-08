package com.project.yozmcafe.controller.dto.cafe;

import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.domain.cafe.Images;

import java.util.List;

public record CafeRequest(String name, String address, List<String> images, DetailRequest detail) {

    public Cafe toCafe() {
        return new Cafe(name, address, new Images(images), detail.toDetail());
    }
}
