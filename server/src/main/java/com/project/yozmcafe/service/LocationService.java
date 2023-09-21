package com.project.yozmcafe.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.yozmcafe.controller.dto.cafe.CafeLocationRequest;
import com.project.yozmcafe.controller.dto.cafe.CafeLocationResponse;
import com.project.yozmcafe.domain.cafe.GeometryGenerator;
import com.project.yozmcafe.domain.cafe.coordinate.CafeCoordinateRepository;
import com.project.yozmcafe.domain.cafe.coordinate.RadiusCalculator;
import com.project.yozmcafe.domain.cafe.coordinate.dto.CafePinDto;

@Service
@Transactional(readOnly = true)
public class LocationService {

    private final CafeCoordinateRepository cafeCoordinateRepository;

    public LocationService(final CafeCoordinateRepository cafeCoordinateRepository) {
        this.cafeCoordinateRepository = cafeCoordinateRepository;
    }

    public List<CafeLocationResponse> findCafesFromLocations(final CafeLocationRequest cafeLocationRequest) {
        final double latitude = cafeLocationRequest.latitude();
        final double longitude = cafeLocationRequest.longitude();
        final double latitudeDelta = cafeLocationRequest.latitudeDelta();
        final double longitudeDelta = cafeLocationRequest.longitudeDelta();

        final String point = GeometryGenerator.generateStringPoint(latitude, longitude);
        final double radius = RadiusCalculator.calculate(latitude, latitudeDelta, longitudeDelta);
        final List<CafePinDto> cafeLocationDtos = cafeCoordinateRepository.findCafePinsFromCoordinate(point, radius);

        return cafeLocationDtos.stream()
                .map(CafeLocationResponse::from)
                .toList();
    }
}
