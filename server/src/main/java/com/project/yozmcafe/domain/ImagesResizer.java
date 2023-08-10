package com.project.yozmcafe.domain;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class ImagesResizer {

    private final List<ImageResizer> images;

    public ImagesResizer(final List<MultipartFile> images) {
        this.images = images.stream()
                .map(ImageResizer::new)
                .toList();
    }

    public List<MultipartFile> resize(final int width) {
        return images.stream()
                .map(imageResizer -> imageResizer.resize(width))
                .toList();
    }
}
