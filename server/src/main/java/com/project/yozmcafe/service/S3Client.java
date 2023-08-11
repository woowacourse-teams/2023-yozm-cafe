package com.project.yozmcafe.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.project.yozmcafe.domain.ImageResizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
public class S3Client {

    @Value("${s3.bucket}")
    private String bucket;
    @Value("${s3.endpoint}")
    private String endpoint;

    private final AmazonS3 amazonS3;

    public S3Client(final AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    public String upload(final MultipartFile image) {
        try {
            amazonS3.putObject(new PutObjectRequest(bucket,
                    image.getOriginalFilename(),
                    image.getInputStream(),
                    createImageMetaData(image)));

            return image.getOriginalFilename();
        } catch (final IOException e) {
            throw new RuntimeException();
        }
    }

    public String resizeAndUpload(final MultipartFile image, final int width) {
        final MultipartFile resizedImage = new ImageResizer(image).resize(width);

        return upload(resizedImage);
    }

    private ObjectMetadata createImageMetaData(final MultipartFile image) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(image.getContentType());
        objectMetadata.setContentLength(image.getSize());

        return objectMetadata;
    }

    public void deleteByImageName(final String imageName, final int width) {
        amazonS3.deleteObject(bucket, "key");
    }
}
