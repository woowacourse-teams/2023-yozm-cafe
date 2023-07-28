package com.project.yozmcafe.domain.cafe;

import com.project.yozmcafe.domain.cafe.available.AvailableTime;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Lob;

import java.util.List;

@Embeddable
public class Detail {
    @ElementCollection
    private List<AvailableTime> availableTimes;
    @Column(nullable = false)
    private String mapUrl;
    @Lob
    @Column(columnDefinition = "text")
    private String description;
    private String phone;

    protected Detail() {
    }

    public Detail(final List<AvailableTime> availableTimes, final String mapUrl, final String description,
                  final String phone) {
        this.availableTimes = availableTimes;
        this.mapUrl = mapUrl;
        this.description = description;
        this.phone = phone;
    }

    public List<AvailableTime> getAvailableTimes() {
        return availableTimes;
    }

    public String getMapUrl() {
        return mapUrl;
    }

    public String getDescription() {
        return description;
    }

    public String getPhone() {
        return phone;
    }
}
