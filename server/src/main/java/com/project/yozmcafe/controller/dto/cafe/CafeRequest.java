package com.project.yozmcafe.controller.dto.cafe;

import java.util.List;

import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.domain.cafe.Images;

public record CafeRequest(String name, String address, DetailRequest detail) {

    public Cafe toCafe(List<String> fileNames) {
        return new Cafe(name, address, new Images(fileNames), detail.toDetail());
    }
}
