package com.project.yozmcafe.domain;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Component
public class S3Client {

    private final AmazonS3 s3;

    @Value("${s3.bucket}")
    private String bucket;

    public S3Client(final AmazonS3 s3) {
        this.s3 = s3;
    }

    public void upload(final MultipartFile multipartFile) {
        try {
            final File file = new File(multipartFile.getOriginalFilename());
            multipartFile.transferTo(file);
            s3.putObject(new PutObjectRequest(bucket, multipartFile.getOriginalFilename(), file));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(final String key) {
        s3.deleteObject(new DeleteObjectRequest(bucket, key));
    }
}
