package com.project.yozmcafe.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.project.yozmcafe.domain.FileNameGenerator;
import com.project.yozmcafe.domain.ImageResizer;

public class ImageService {

    public List<String> upload(List<MultipartFile> files) {
        final List<ImageResizer> resizers = createResizers(files);

        final List<List<MultipartFile>> resizedImages = getResizedImages(resizers);
// TODO: 2023/08/11 s3 업로드 해야됨 
        return resizers.stream()
                .map(ImageResizer::getFileName)
                .toList();
    }

    private List<ImageResizer> createResizers(final List<MultipartFile> files) {
        List<ImageResizer> imageResizers = new ArrayList<>();
        for (final MultipartFile file : files) {
            final FileNameGenerator fileNameGenerator = FileNameGenerator.from(file.getOriginalFilename());
            final String fileName = fileNameGenerator.getFileName();
            imageResizers.add(new ImageResizer(file, fileName));
        }
        return imageResizers;
    }

    private List<List<MultipartFile>> getResizedImages(final List<ImageResizer> resizers) {
        return resizers.stream()
                .map(ImageResizer::generateResizedImages)
                .toList();
    }

}
