package com.project.yozmcafe.controller.dto.cafe;

import java.time.LocalDateTime;

import com.project.yozmcafe.domain.cafe.available.AvailableTime;

public record AvailableTimeResponse(String day, LocalDateTime open, LocalDateTime close, boolean opened) {
    public static AvailableTimeResponse of(final AvailableTime availableTime) {
        return new AvailableTimeResponse(
                availableTime.getDay().name(),
                availableTime.getOpen(),
                availableTime.getClose(),
                availableTime.isOpened()
        );
    }
}
