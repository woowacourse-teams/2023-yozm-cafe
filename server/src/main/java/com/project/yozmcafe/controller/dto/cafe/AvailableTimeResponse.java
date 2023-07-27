package com.project.yozmcafe.controller.dto.cafe;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.yozmcafe.domain.cafe.available.AvailableTime;

import java.time.LocalTime;

public record AvailableTimeResponse(String day, @JsonFormat(pattern = "HH:mm") LocalTime open,
                                    @JsonFormat(pattern = "HH:mm") LocalTime close, boolean opened) {
    public static AvailableTimeResponse from(final AvailableTime availableTime) {
        return new AvailableTimeResponse(
                availableTime.getDay().name(),
                availableTime.getOpen(),
                availableTime.getClose(),
                availableTime.isOpened()
        );
    }
}
