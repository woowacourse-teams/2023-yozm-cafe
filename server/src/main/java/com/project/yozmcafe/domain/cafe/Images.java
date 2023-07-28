package com.project.yozmcafe.domain.cafe;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;

import java.util.List;

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
            throw new IllegalArgumentException("카페 이미지가 존재하지 않습니다.");
        }
        return urls.get(REPRESENTATIVE_INDEX);
    }
}
