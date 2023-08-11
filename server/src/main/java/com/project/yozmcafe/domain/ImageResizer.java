package com.project.yozmcafe.domain;

import static com.project.yozmcafe.domain.ResizeFormats.ORIGINAL;
import static com.project.yozmcafe.exception.ErrorCode.INVALID_IMAGE_SIZE;
import static com.project.yozmcafe.exception.ErrorCode.NOT_IMAGE;
import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.web.multipart.MultipartFile;

import com.project.yozmcafe.exception.BadRequestException;

public class ImageResizer {

    public static final int MAX_IMAGE_SIZE = 1024 * 1024 * 5;
    private static final String CONTENT_TYPE_DELIMITER = "/";
    private static final String IMAGE_FORMAT_PREFIX = "image/";
    private static final int FORMAT_INDEX = 1;

    private final MultipartFile image;
    private final String fileName;

    public ImageResizer(final MultipartFile image, final String fileName) {
        validate(image);
        this.image = image;
        this.fileName = fileName;
    }

    private void validate(final MultipartFile image) {
        if (isNull(image.getContentType()) || isNotImage(image)) {
            throw new BadRequestException(NOT_IMAGE);
        }
        if (image.getSize() > MAX_IMAGE_SIZE) {
            throw new BadRequestException(INVALID_IMAGE_SIZE);
        }
    }

    private boolean isNotImage(final MultipartFile image) {
        final String contentType = image.getContentType();
        return !requireNonNull(contentType).startsWith(IMAGE_FORMAT_PREFIX);
    }

    public List<MultipartFile> generateResizedImages() {
        try {
            final MultipartFile originalSizeImage = getOriginalImageWithNewName();
            final List<ResizeFormats> resizedFormatExceptOriginal = ResizeFormats.getResizedWidthExceptOriginal();

            final List<MultipartFile> resizedImages = new ArrayList<>(getResizedImages(resizedFormatExceptOriginal));
            resizedImages.add(originalSizeImage);

            return resizedImages;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    private MultipartFile getOriginalImageWithNewName() throws IOException {
        final BufferedImage bufferedImage = ImageIO.read(image.getInputStream());
        final byte[] bytes = toByteArray(bufferedImage);

        return ResizedImageFile.from(bytes, image, fileName, ORIGINAL);
    }

    private List<MultipartFile> getResizedImages(final List<ResizeFormats> resizeFormats) {
        return resizeFormats.stream()
                .map(this::resize)
                .toList();
    }

    private MultipartFile resize(final ResizeFormats resizeFormats) {
        try {
            final BufferedImage source = ImageIO.read(image.getInputStream());
            final BufferedImage result = getScaledImage(resizeFormats.getWidth(), source);
            final byte[] bytes = toByteArray(result);

            return ResizedImageFile.from(bytes, image, fileName, resizeFormats);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    private BufferedImage getScaledImage(final int width, final BufferedImage source) {
        final int height = getResizedHeight(width, source);
        final BufferedImage scaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        final Graphics graphics = scaledImage.getGraphics();
        graphics.drawImage(source.getScaledInstance(width, height, Image.SCALE_FAST), 0, 0, null);
        graphics.dispose();

        return scaledImage;
    }

    private int getResizedHeight(final int width, final BufferedImage bufferedImage) {
        return bufferedImage.getHeight() * width / bufferedImage.getWidth();
    }

    private byte[] toByteArray(final BufferedImage result) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        ImageIO.write(result, getFormat(), byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private String getFormat() {
        return image.getContentType().split(CONTENT_TYPE_DELIMITER)[FORMAT_INDEX];
    }

    public String getFileName() {
        return fileName;
    }
}
