package com.project.yozmcafe.domain;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project.yozmcafe.domain.resizedimage.ImageName;
import com.project.yozmcafe.domain.resizedimage.ImageResizer;
import com.project.yozmcafe.domain.resizedimage.Size;

@Service
public class ImageHandler {

    private final S3Client s3Client;

    public ImageHandler(final S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public List<String> resizeAndUploadToAllSizes(final List<MultipartFile> files) {
        final List<ImageResizer> resizers = files.stream()
                .map(this::multipartfileToImageResizer)
                .toList();

        resizers.parallelStream()
                .forEach(resizer -> resizer.resizeImageToAllSizes()
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

    public String resizeAndUploadToFixedSize(final MultipartFile image, final Size size) {
        final ImageResizer imageResizer = multipartfileToImageResizer(image);

        final MultipartFile resizedImages = imageResizer.getResizedImage(size);
        s3Client.upload(resizedImages);

        return imageResizer.getFileName();
    }
}
