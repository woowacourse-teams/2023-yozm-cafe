package com.project.yozmcafe.domain.resizedimage;

import java.util.Arrays;
import java.util.List;

public enum Size {
    THUMBNAIL("100/", 100),
    MOBILE("500/", 500);

    private final String path;
    private final int width;

    Size(final String path, final int width) {
        this.path = path;
        this.width = width;
    }

    public static List<String> getFileNamesWithPath(final String fileName) {
        return Arrays.stream(Size.values())
                .map(size -> size.getFileNameWithPath(fileName))
                .toList();
    }

    public String getFileNameWithPath(final String fileName) {
        return path + fileName;
    }

    public String getPath() {
        return path;
    }

    public int getWidth() {
        return width;
    }
}
