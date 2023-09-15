package com.project.yozmcafe.controller.dto.cafe;

import com.project.yozmcafe.domain.cafe.coordinate.dto.CafePinDto;

public record CafeLocationResponse(Long id, String name, String address, double latitude, double longitude) {

    public static CafeLocationResponse from(final CafePinDto cafePinDto) {
        return new CafeLocationResponse(
                cafePinDto.getId(),
                cafePinDto.getName(),
                cafePinDto.getName(),
                cafePinDto.getLatitude(),
                cafePinDto.getLongitude()
        );
    }
}
