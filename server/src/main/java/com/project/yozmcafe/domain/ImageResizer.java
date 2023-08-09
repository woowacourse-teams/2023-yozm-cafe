package com.project.yozmcafe.domain;

import com.project.yozmcafe.exception.BadRequestException;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static com.project.yozmcafe.exception.ErrorCode.INVALID_IMAGE_SIZE;
import static com.project.yozmcafe.exception.ErrorCode.NOT_IMAGE;
import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

public class ImageResizer {

    public static final int MAX_IMAGE_SIZE = 5000000;

    private static final String CONTENT_TYPE_DELIMETER = "/";
    private static final String IMAGE_FORMAT_PREFIX = "image/";
    private static final int FORMAT_INDEX = 1;

    private final MultipartFile image;

    public ImageResizer(final MultipartFile image) {
        validate(image);
        this.image = image;
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

    public byte[] resize(final int width) {
        try {
            final BufferedImage source = ImageIO.read(image.getInputStream());
            final BufferedImage result = getScaledImage(width, source);

            return toByteArray(result);
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
        return image.getContentType().split(CONTENT_TYPE_DELIMETER)[FORMAT_INDEX];
    }
}
