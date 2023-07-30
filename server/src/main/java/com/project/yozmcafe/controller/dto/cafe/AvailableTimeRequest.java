package com.project.yozmcafe.controller.dto.cafe;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.yozmcafe.domain.cafe.available.AvailableTime;
import com.project.yozmcafe.domain.cafe.available.Days;

import java.time.LocalTime;

public record AvailableTimeRequest(Days day, @JsonProperty("open") LocalTime open, @JsonProperty("close") LocalTime close,
                                   @JsonProperty("opened") boolean opened) {

    public AvailableTime toAvailableTime() {
        return new AvailableTime(day, open, close, opened);
    }
}
