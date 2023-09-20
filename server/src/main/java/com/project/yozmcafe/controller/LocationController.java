package com.project.yozmcafe.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.yozmcafe.controller.dto.cafe.CafeLocationRequest;
import com.project.yozmcafe.controller.dto.cafe.CafeLocationResponse;
import com.project.yozmcafe.service.LocationService;

@RestController
@RequestMapping("/cafes/location")
public class LocationController {

    private final LocationService locationService;

    public LocationController(final LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping
    public ResponseEntity<List<CafeLocationResponse>> findCafesFromLocation(
            final CafeLocationRequest cafeLocationRequest) {
        final List<CafeLocationResponse> cafesFromLocations = locationService.findCafesFromLocations(
                cafeLocationRequest);
        return ResponseEntity.ok(cafesFromLocations);
    }
}
