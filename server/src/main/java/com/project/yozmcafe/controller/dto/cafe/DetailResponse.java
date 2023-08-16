package com.project.yozmcafe.controller.dto.cafe;

import com.project.yozmcafe.domain.cafe.Detail;

import java.util.List;

public record DetailResponse(List<AvailableTimeResponse> openingHours, String mapUrl, String description,
                             String phone) {
    public static DetailResponse from(final Detail detail) {
        final List<AvailableTimeResponse> availableTimes = detail.getAvailableTimes().stream()
                .map(AvailableTimeResponse::from)
                .toList();
        return new DetailResponse(availableTimes, detail.getMapUrl(), detail.getDescription(), detail.getPhone());
    }
}
