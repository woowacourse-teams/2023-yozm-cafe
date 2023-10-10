package com.project.yozmcafe.service;

import java.util.List;

import org.locationtech.jts.geom.Polygon;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.yozmcafe.controller.dto.cafe.CafeLocationRequest;
import com.project.yozmcafe.controller.dto.cafe.CafeLocationResponse;
import com.project.yozmcafe.domain.cafe.GeometryGenerator;
import com.project.yozmcafe.domain.cafe.coordinate.CafeCoordinateRepository;
import com.project.yozmcafe.domain.cafe.coordinate.dto.CafePinDto;

@Service
@Transactional(readOnly = true)
public class LocationService {

    private final CafeCoordinateRepository cafeCoordinateRepository;

    public LocationService(final CafeCoordinateRepository cafeCoordinateRepository) {
        this.cafeCoordinateRepository = cafeCoordinateRepository;
    }

    public List<CafeLocationResponse> findCafesFromLocations(final CafeLocationRequest cafeLocationRequest) {
        final Polygon area = GeometryGenerator.generatePolygon(cafeLocationRequest);
        final List<CafePinDto> cafeLocationDtos = cafeCoordinateRepository.findCafePinsFromCoordinate(area);

        return cafeLocationDtos.stream()
                .map(CafeLocationResponse::from)
                .toList();
    }
}
