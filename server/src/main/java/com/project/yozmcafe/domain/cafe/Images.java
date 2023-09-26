package com.project.yozmcafe.domain.cafe;

import com.project.yozmcafe.exception.BadRequestException;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;

import java.util.ArrayList;
import java.util.List;

import static com.project.yozmcafe.exception.ErrorCode.NOT_EXISTED_CAFE_IMAGE;

@Embeddable
public class Images {

    private static final int REPRESENTATIVE_INDEX = 0;

    @ElementCollection
    @CollectionTable(name = "image")
    private List<String> urls = new ArrayList<>();

    protected Images() {
    }

    public Images(final List<String> urls) {
        if (urls.isEmpty()) {
            throw new BadRequestException(NOT_EXISTED_CAFE_IMAGE);
        }

        this.urls = urls;
    }

    public List<String> getUrls() {
        return new ArrayList<>(urls);
    }

    public String getRepresentativeImage() {
        return urls.get(REPRESENTATIVE_INDEX);
    }
}
