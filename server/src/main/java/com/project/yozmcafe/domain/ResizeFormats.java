package com.project.yozmcafe.domain;

import java.util.Arrays;
import java.util.List;

public enum ResizeFormats {
    ORIGINAL("original/", 0),
    THUMBNAIL("100/", 100),
    MOBILE("500/", 500);

    private final String path;
    private final int width;

    ResizeFormats(final String path, final int width) {
        this.path = path;
        this.width = width;
    }

    public static List<ResizeFormats> getResizedWidthExceptOriginal() {
        return Arrays.stream(ResizeFormats.values())
                .filter(resizeFormats -> !resizeFormats.equals(ORIGINAL))
                .toList();
    }

    public static List<String> addAllPathPrefix(final String fileName) {
        return Arrays.stream(ResizeFormats.values())
                .map(resizeFormats -> resizeFormats.path + fileName)
                .toList();
    }

    public String getPath() {
        return path;
    }

    public int getWidth() {
        return width;
    }
}
