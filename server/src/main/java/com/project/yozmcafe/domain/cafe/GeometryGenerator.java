package com.project.yozmcafe.domain.cafe;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;

import com.project.yozmcafe.controller.dto.cafe.CafeLocationRequest;

public class GeometryGenerator {

    private static final GeometryFactory GEOMETRY_FACTORY = new GeometryFactory();
    /**
     * SRID(Spatial Reference Identifier)는 고유한 공간 좌표 식별자입니다.
     * <p>
     * 그 중, 4326은 WGS84 경위도 좌표계를 의미합니다. GPS 기술에서도 사용되는 좌표계로 범용적으로 가장 널리 사용되는 좌표계입니다.
     */
    private static final int SRID = 4326;

    private GeometryGenerator() {
    }

    public static Point generatePointWithCoordinate(final double latitude, final double longitude) {
        final Point point = GEOMETRY_FACTORY.createPoint(new Coordinate(longitude, latitude));
        point.setSRID(SRID);
        return point;
    }

    public static Polygon generatePolygon(final CafeLocationRequest cafeLocationRequest) {
        final double latitude = cafeLocationRequest.latitude();
        final double longitude = cafeLocationRequest.longitude();
        final double latitudeDelta = cafeLocationRequest.latitudeDelta();
        final double longitudeDelta = cafeLocationRequest.longitudeDelta();

        final double minLatitude = latitude - latitudeDelta;
        final double maxLatitude = latitude + latitudeDelta;
        final double minLongitude = longitude - longitudeDelta;
        final double maxLongitude = longitude + longitudeDelta;

        final Coordinate[] vertexes = new Coordinate[]{
                new Coordinate(maxLongitude, minLatitude),
                new Coordinate(maxLongitude, maxLatitude),
                new Coordinate(minLongitude, maxLatitude),
                new Coordinate(minLongitude, minLatitude),
                new Coordinate(maxLongitude, minLatitude)
        };

        final Polygon polygon = GEOMETRY_FACTORY.createPolygon(vertexes);
        polygon.setSRID(SRID);
        return polygon;
    }
}
