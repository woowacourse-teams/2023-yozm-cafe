package com.project.yozmcafe.domain.resizedimage;

import com.project.yozmcafe.exception.BadRequestException;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import static com.project.yozmcafe.exception.ErrorCode.INVALID_IMAGE_SIZE;
import static com.project.yozmcafe.exception.ErrorCode.NOT_IMAGE;
import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

public class ImageResizer {

    public static final int MAX_IMAGE_SIZE = 1024 * 1024 * 10;
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

    public List<MultipartFile> getResizedImages(final List<Size> sizes) {
        return sizes.stream()
                .map(this::getResizedImage)
                .toList();
    }

    public MultipartFile getResizedImage(final Size size) {
        final BufferedImage bufferedImage = getBufferedImage();

        final int width = size.getWidth();
        final int height = getResizedHeight(width, bufferedImage);
        final BufferedImage scaledImage = resize(bufferedImage, width, height);

        final byte[] bytes = toByteArray(scaledImage);
        return toMultipartFile(bytes, size);
    }

    private BufferedImage getBufferedImage() {
        try {
            return ImageIO.read(image.getInputStream());
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    private int getResizedHeight(final int resizedWidth, final BufferedImage bufferedImage) {
        final double ratio = (double) resizedWidth / bufferedImage.getWidth();
        return (int) (bufferedImage.getHeight() * ratio);
    }

    private BufferedImage resize(final BufferedImage image, final int width, final int height) {
        final BufferedImage canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        final Graphics graphics = canvas.getGraphics();
        graphics.drawImage(image.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
        graphics.dispose();

        return canvas;
    }

    private byte[] toByteArray(final BufferedImage result) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            ImageIO.write(result, getFormat(), byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ResizedImage toMultipartFile(final byte[] bytes, final Size imageSize) {
        return ResizedImage.of(bytes, image.getContentType(), fileName, imageSize.getFileNameWithPath(fileName));
    }

    private String getFormat() {
        return image.getContentType().split(CONTENT_TYPE_DELIMITER)[FORMAT_INDEX];
    }

    public String getFileName() {
        return fileName;
    }
}
