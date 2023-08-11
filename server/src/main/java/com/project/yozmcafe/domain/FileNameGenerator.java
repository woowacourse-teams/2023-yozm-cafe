package com.project.yozmcafe.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileNameGenerator {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSSSSS");
    private static final String EXTENSION_DELIMITER = ".";

    private final String fileName;

    private FileNameGenerator(final String fileName) {
        this.fileName = fileName;
    }

    public static FileNameGenerator from(String originalFileName) {
        final String fileName = FORMATTER.format(LocalDateTime.now());
        final String extension = getExtension(originalFileName);
        return new FileNameGenerator(fileName + extension);
    }

    private static String getExtension(final String originalFileName) {
        return originalFileName.substring(originalFileName.lastIndexOf(EXTENSION_DELIMITER));
    }

    public String getFileName() {
        return fileName;
    }
}
