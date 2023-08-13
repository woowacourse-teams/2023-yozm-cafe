package com.project.yozmcafe.domain.resizedimage;

import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ResizedImage implements MultipartFile {

    private final String name;
    private final String originalName;
    private final String contentType;
    private final long size;
    private final byte[] bytes;

    private ResizedImage(final String name, final String originalName, final String contentType, final long size,
                         final byte[] bytes) {
        this.name = name;
        this.originalName = originalName;
        this.contentType = contentType;
        this.size = size;
        this.bytes = bytes;
    }

    public static ResizedImage of(byte[] bytes, String contentType, String name, String originalName) {
        return new ResizedImage(
                name,
                originalName,
                contentType,
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
    public byte[] getBytes() {
        return bytes;
    }

    @Override
    public InputStream getInputStream() {
        return new ByteArrayInputStream(bytes);
    }

    @Override
    public void transferTo(final File dest) throws IOException, IllegalStateException {
        FileOutputStream fos = new FileOutputStream(dest);
        fos.write(this.getBytes());
        fos.close();
    }
}
