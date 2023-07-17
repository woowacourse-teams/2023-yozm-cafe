package com.project.yozmcafe.controller.dto.cafe;

import java.time.LocalDateTime;

import com.project.yozmcafe.domain.cafe.available.AvailableTime;

public record AvailableTimeDto(String day, LocalDateTime open, LocalDateTime close, boolean opened) {
    public static AvailableTimeDto of(final AvailableTime availableTime) {
        return new AvailableTimeDto(
                availableTime.getDay().name(),
                availableTime.getOpen(),
                availableTime.getClose(),
                availableTime.isOpened()
        );
    }
}
