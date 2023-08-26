package com.project.yozmcafe.controller.dto.cafe;

import com.project.yozmcafe.domain.cafe.available.AvailableTime;
import com.project.yozmcafe.domain.cafe.available.Days;

import java.time.LocalTime;

public record AvailableTimeRequest(Days day, LocalTime open, LocalTime close, boolean opened) {

    public AvailableTime toAvailableTime() {
        return new AvailableTime(day, open, close, opened);
    }
}
