package com.project.yozmcafe.domain;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public class ResizedImageFile implements MultipartFile {

    private final String name;
    private final String originalName;
    private final String contentType;
    private final long size;
    private final byte[] bytes;

    private ResizedImageFile(final String name, final String originalName, final String contentType, final long size,
                             final byte[] bytes) {
        this.name = name;
        this.originalName = originalName;
        this.contentType = contentType;
        this.size = size;
        this.bytes = bytes;
    }

    public static ResizedImageFile from(byte[] bytes, MultipartFile image, String fileName,
                                        ResizeFormats resizeFormats) {
        String originalFileName = resizeFormats.getPath() + fileName;
        return new ResizedImageFile(
                fileName,
                originalFileName,
                image.getContentType(),
                bytes.length,
                bytes
        );
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getOriginalFilename() {
        return originalName;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public long getSize() {
        return size;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return bytes;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(bytes);
    }

    @Override
    public void transferTo(final File dest) throws IOException, IllegalStateException {

    }
}
