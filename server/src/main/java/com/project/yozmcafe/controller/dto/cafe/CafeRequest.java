package com.project.yozmcafe.controller.dto.cafe;

import com.project.yozmcafe.domain.cafe.Detail;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record CafeRequest(String name, String address, List<MultipartFile> images, DetailRequest detail) {

    public Detail getDetail() {
        return detail.toDetail();
    }
}
