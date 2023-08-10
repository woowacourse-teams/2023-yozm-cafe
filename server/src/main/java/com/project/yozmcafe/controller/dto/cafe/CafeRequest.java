package com.project.yozmcafe.controller.dto.cafe;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public record CafeRequest(String name, String address, List<MultipartFile> images, DetailRequest detail) {
}
