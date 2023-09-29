package com.project.yozmcafe.controller.dto.cafe;

import org.locationtech.jts.geom.Point;

import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.domain.cafe.GeometryGenerator;
import com.project.yozmcafe.domain.cafe.coordinate.CafeCoordinate;

public record CafeCoordinateRequest(double latitude, double longitude) {

    public CafeCoordinate toCafeCoordinateWithCafe(final Cafe cafe) {
        final Point point = GeometryGenerator.generatePointWithCoordinate(latitude, longitude);
        return new CafeCoordinate(point, cafe);
    }
}
