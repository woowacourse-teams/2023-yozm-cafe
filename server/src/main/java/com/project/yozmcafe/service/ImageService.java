package com.project.yozmcafe.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImageService {

    private final S3Client s3Client;

    public ImageService(final S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public List<String> uploadImagesToS3(final List<MultipartFile> images) {
        final List<String> imageNames = new ArrayList<>();

        for (final MultipartFile image : images) {
            s3Client.resizeAndUpload(image, 100);
            s3Client.resizeAndUpload(image, 500);
            imageNames.add(s3Client.upload(image));
        }

        return imageNames;
    }
}
