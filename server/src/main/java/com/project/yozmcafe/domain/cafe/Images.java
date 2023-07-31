package com.project.yozmcafe.domain.cafe;

import static com.project.yozmcafe.exception.ErrorCode.NOT_EXISTED_CAFE_IMAGE;

import java.util.List;

import com.project.yozmcafe.exception.BadRequestException;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;

@Embeddable
public class Images {

    private static final int REPRESENTATIVE_INDEX = 0;

    @ElementCollection
    @CollectionTable(name = "image")
    private List<String> urls;

    protected Images() {
    }

    public Images(List<String> urls) {
        this.urls = urls;
    }

    public List<String> getUrls() {
        return urls;
    }

    public String getRepresentativeImage() {
        if (urls.isEmpty()) {
            throw new BadRequestException(NOT_EXISTED_CAFE_IMAGE);
        }
        return urls.get(REPRESENTATIVE_INDEX);
    }
}
