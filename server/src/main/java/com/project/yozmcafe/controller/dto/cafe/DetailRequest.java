package com.project.yozmcafe.controller.dto.cafe;

import com.project.yozmcafe.domain.cafe.Detail;
import com.project.yozmcafe.domain.cafe.available.AvailableTime;

import java.util.List;

public record DetailRequest(List<AvailableTimeRequest> openingHours, String mapUrl, String description,
                            String phone) {
    public Detail toDetail() {
        List<AvailableTime> availableTimes = openingHours.stream()
                .map(AvailableTimeRequest::toAvailableTime)
                .toList();
        return new Detail(availableTimes, mapUrl, description, phone);
    }
}
