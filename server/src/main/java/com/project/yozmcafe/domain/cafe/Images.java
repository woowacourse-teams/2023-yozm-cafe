package com.project.yozmcafe.domain.cafe;

import java.util.List;

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
        return urls.get(REPRESENTATIVE_INDEX);
    }
}
