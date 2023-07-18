package com.project.yozmcafe.controller.dto.cafe;

import java.util.List;

import com.project.yozmcafe.domain.cafe.Detail;

public record DetailDto(List<AvailableTimeDto> openingHours, String mapUrl, String description) {
    public static DetailDto of(final Detail detail) {
        final List<AvailableTimeDto> availableTimes = detail.getAvailableTimes().stream()
                .map(AvailableTimeDto::of)
                .toList();
        return new DetailDto(availableTimes, detail.getMapUrl(), detail.getDescription());
    }
}
