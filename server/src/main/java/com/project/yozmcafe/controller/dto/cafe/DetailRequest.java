package com.project.yozmcafe.controller.dto.cafe;

import com.project.yozmcafe.domain.cafe.Detail;
import com.project.yozmcafe.domain.cafe.available.AvailableTime;

import java.util.List;

public record DetailRequest(List<AvailableTime> openingHours, String mapUrl, String description) {
    public static DetailResponse from(final Detail detail) {
        final List<AvailableTimeResponse> availableTimes = detail.getAvailableTimes().stream()
                .map(AvailableTimeResponse::from)
                .toList();
        return new DetailResponse(availableTimes, detail.getMapUrl(), detail.getDescription());
    }

    public Detail toDetail() {
        return new Detail(openingHours, mapUrl, description);
    }
}

