package com.project.yozmcafe.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project.yozmcafe.domain.FileNameGenerator;
import com.project.yozmcafe.domain.ImageResizer;
import com.project.yozmcafe.domain.S3Client;

@Service
public class ImageService {

    private final S3Client s3Client;

    public ImageService(final S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public List<String> upload(List<MultipartFile> files) {
        final List<ImageResizer> resizers = createResizers(files);

        final List<MultipartFile> resizedImages = getResizedImages(resizers);
        resizedImages.forEach(s3Client::upload);

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

    private List<MultipartFile> getResizedImages(final List<ImageResizer> resizers) {
        return resizers.stream()
                .map(ImageResizer::generateResizedImages)
                .flatMap(List::stream)
                .toList();
    }

}
