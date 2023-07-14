package com.project.yozmcafe.domain.cafe;

import java.util.List;

import com.project.yozmcafe.domain.cafe.available.AvailableTime;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;

@Embeddable
public class Detail {
    @ElementCollection
    private List<AvailableTime> availableTimes;
    @Column(nullable = false)
    private String mapUrl;
    private String description;

    protected Detail() {
    }

    public Detail(final List<AvailableTime> availableTimes, final String mapUrl, final String description) {
        this.availableTimes = availableTimes;
        this.mapUrl = mapUrl;
        this.description = description;
    }
}
