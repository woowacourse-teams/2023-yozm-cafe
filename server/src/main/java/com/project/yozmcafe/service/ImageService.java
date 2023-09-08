package com.project.yozmcafe.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project.yozmcafe.domain.S3Client;
import com.project.yozmcafe.domain.resizedimage.ImageName;
import com.project.yozmcafe.domain.resizedimage.ImageResizer;
import com.project.yozmcafe.domain.resizedimage.Size;

@Service
public class ImageService {

    private final S3Client s3Client;

    public ImageService(final S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public List<String> resizeAndUpload(final List<MultipartFile> files, final List<Size> sizes) {
        final List<ImageResizer> resizers = files.stream()
                .map(this::multipartfileToImageResizer)
                .toList();

        resizers.parallelStream()
                .forEach(resizer -> resizer.getResizedImages(sizes)
                        .forEach(s3Client::upload)
                );

        return resizers.stream()
                .map(ImageResizer::getFileName)
                .toList();
    }

    private ImageResizer multipartfileToImageResizer(final MultipartFile file) {
        final ImageName imageName = ImageName.from(file.getOriginalFilename());
        return new ImageResizer(file, imageName.get());
    }

    public void deleteAll(final List<String> imageNames) {
        imageNames.stream()
                .map(Size::getFileNamesWithPath)
                .flatMap(List::stream)
                .forEach(s3Client::delete);
    }

    public String resizeAndUpload(final MultipartFile image, final Size size) {
        final ImageResizer imageResizer = multipartfileToImageResizer(image);

        final MultipartFile resizedImages = imageResizer.getResizedImage(size);
        s3Client.upload(resizedImages);

        return imageResizer.getFileName();
    }
}
