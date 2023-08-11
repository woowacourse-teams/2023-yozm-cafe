package com.project.yozmcafe.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class FileNameParser {
    private static final String DIRECTORY_DELIMITER = "/";
    private static final String THUMBNAIL_SIZE = "100";
    private static final String MOBILE_SIZE = "500";
    private static final String ORIGINAL = "ORIGINAL";
    private static final List<String> RESIZE_FORMATS = List.of(ORIGINAL, THUMBNAIL_SIZE, MOBILE_SIZE);
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSSSSS");
    private static final String EXTENSION_DELIMITER = ".";

    public List<String> generateFileNames(String originalFileName) {
        final LocalDateTime now = LocalDateTime.now();
        final String fileName = FORMATTER.format(now);

        return RESIZE_FORMATS.stream()
                .map(resizeFormat -> resizeFormat + DIRECTORY_DELIMITER + fileName + getExtension(originalFileName))
                .toList();
    }

    private String getExtension(final String originalFileName) {
        return originalFileName.substring(originalFileName.lastIndexOf(EXTENSION_DELIMITER));
    }

}
