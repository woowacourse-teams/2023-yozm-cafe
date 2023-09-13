package com.project.yozmcafe.service;

import static com.project.yozmcafe.domain.resizedimage.Size.MOBILE;
import static com.project.yozmcafe.domain.resizedimage.Size.THUMBNAIL;

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

    public List<String> resizeToAllSizesAndUpload(final List<MultipartFile> files) {
        final List<ImageResizer> imageResizers = files.stream()
                .map(this::multipartfileToImageResizer)
                .toList();

        imageResizers.parallelStream()
                .forEach(imageResizer -> imageResizer.resizeImageToAllSizes()
                        .forEach(s3Client::upload));

        return imageResizers.stream()
                .map(ImageResizer::getFileName)
                .toList();
    }

    private ImageResizer multipartfileToImageResizer(final MultipartFile file) {
        final ImageName imageName = ImageName.from(file.getOriginalFilename());
        return new ImageResizer(file, imageName.get());
    }

    public String resizeToThumbnailSizeAndUpload(MultipartFile file) {
        return resizeToFixedImageAndUpload(file, THUMBNAIL);
    }

    public String resizeToMobileSizeAndUpload(MultipartFile file) {
        return resizeToFixedImageAndUpload(file, MOBILE);
    }

    private String resizeToFixedImageAndUpload(final MultipartFile file, final Size size) {
        final ImageResizer imageResizer = multipartfileToImageResizer(file);

        final MultipartFile resizedImages = imageResizer.resizeToFixedImage(size);
        s3Client.upload(resizedImages);

        return imageResizer.getFileName();
    }

    public void deleteAll(final List<String> imageNames) {
        imageNames.stream()
                .map(Size::getFileNamesWithPath)
                .flatMap(List::stream)
                .forEach(s3Client::delete);
    }
}
