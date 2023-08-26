package com.project.yozmcafe.service;

import java.util.ArrayList;
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

    public List<String> uploadAndGetImageNames(final List<MultipartFile> files) {
        final List<ImageResizer> resizers = createResizers(files);
        resizers.forEach(this::resizeAndUpload);

        return resizers.stream()
                .map(ImageResizer::getFileName)
                .toList();
    }

    private List<ImageResizer> createResizers(final List<MultipartFile> files) {
        List<ImageResizer> imageResizers = new ArrayList<>();

        for (final MultipartFile file : files) {
            final ImageName imageName = ImageName.from(file.getOriginalFilename());
            imageResizers.add(new ImageResizer(file, imageName.get()));
        }
        return imageResizers;
    }

    private void resizeAndUpload(final ImageResizer imageResizer) {
        final MultipartFile originalImage = imageResizer.getOriginalImage();
        final List<MultipartFile> resizedImages = imageResizer.getResizedImages(Size.getAllSizesExceptOriginal());

        s3Client.upload(originalImage);
        resizedImages.forEach(s3Client::upload);
    }

    public void deleteAll(final List<String> imageNames) {
        imageNames.stream()
                .map(Size::getFileNamesWithPath)
                .flatMap(List::stream)
                .forEach(s3Client::delete);
    }

    public String singleImageUploadAndGetName(final MultipartFile image, Size size) {
        final ImageName imageName = ImageName.from(image.getOriginalFilename());
        final ImageResizer imageResizer = new ImageResizer(image, imageName.get());
        final List<MultipartFile> resizedImages = imageResizer.getResizedImages(List.of(size));
        s3Client.upload(resizedImages.get(0));
        return imageResizer.getFileName();
    }

    public String uploadOriginalImage(final MultipartFile image) {
        final ImageName imageName = ImageName.from(image.getOriginalFilename());
        final ImageResizer imageResizer = new ImageResizer(image, imageName.get());
        final MultipartFile originalImage = imageResizer.getOriginalImage();
        s3Client.upload(originalImage);
        return imageResizer.getFileName();
    }
}
