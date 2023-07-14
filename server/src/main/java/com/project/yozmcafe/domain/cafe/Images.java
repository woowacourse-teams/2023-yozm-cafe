package com.project.yozmcafe.domain.cafe;

import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;

@Embeddable
public class Images {

    @ElementCollection
    @CollectionTable(name = "image")
    private List<String> urls;

    protected Images() {
    }

    public Images(List<String> urls) {
        this.urls = urls;
    }
}
