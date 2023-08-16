package com.project.yozmcafe.domain.resizedimage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ImageName {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSSSSS");
    private static final String EXTENSION_DELIMITER = ".";

    private final String fileName;

    private ImageName(final String fileName) {
        this.fileName = fileName;
    }

    public static ImageName from(String originalFileName) {
        final String fileName = FORMATTER.format(LocalDateTime.now());
        final String extension = getExtension(originalFileName);
        return new ImageName(fileName + extension);
    }

    private static String getExtension(final String originalFileName) {
        return originalFileName.substring(originalFileName.lastIndexOf(EXTENSION_DELIMITER));
    }

    public String get() {
        return fileName;
    }
}
