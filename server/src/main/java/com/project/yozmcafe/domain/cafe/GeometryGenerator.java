package com.project.yozmcafe.domain.cafe;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

import com.project.yozmcafe.controller.dto.cafe.CafeLocationRequest;

public class GeometryGenerator {

    private static final GeometryFactory GEOMETRY_FACTORY = new GeometryFactory();
    /**
     * SRID(Spatial Reference Identifier)는 고유한 공간 좌표 식별자입니다.
     * <p>
     * 그 중, 4326은 WGS84 경위도 좌표계를 의미합니다. GPS 기술에서도 사용되는 좌표계로 범용적으로 가장 널리 사용되는 좌표계입니다.
     */
    private static final int SRID = 4326;
    private static final String STRING_POLYGON_FORMAT = "POLYGON((%s))";
    private static final String STRING_GEOMETRY_DELIMITER = " ";
    private static final String POINT_DELIMITER = ", ";

    private GeometryGenerator() {
    }

    public static Point generatePointWithCoordinate(final double latitude, final double longitude) {
        final Point point = GEOMETRY_FACTORY.createPoint(new Coordinate(longitude, latitude));
        point.setSRID(SRID);
        return point;
    }

    public static String generateStringPolygon(final CafeLocationRequest cafeLocationRequest) {
        final double latitude = cafeLocationRequest.latitude();
        final double longitude = cafeLocationRequest.longitude();
        final double latitudeDelta = cafeLocationRequest.latitudeDelta();
        final double longitudeDelta = cafeLocationRequest.longitudeDelta();

        final String minLatitude = String.valueOf(latitude - latitudeDelta);
        final String maxLatitude = String.valueOf(latitude + latitudeDelta);
        final String minLongitude = String.valueOf(longitude - longitudeDelta);
        final String maxLongitude = String.valueOf(longitude + longitudeDelta);

        final String firstVertex = String.join(STRING_GEOMETRY_DELIMITER, minLatitude, maxLongitude);
        final String secondVertex = String.join(STRING_GEOMETRY_DELIMITER, maxLatitude, maxLongitude);
        final String thirdVertex = String.join(STRING_GEOMETRY_DELIMITER, maxLatitude, minLongitude);
        final String fourthVertex = String.join(STRING_GEOMETRY_DELIMITER, minLatitude, minLongitude);

        final String vertexes = String.join(POINT_DELIMITER, firstVertex, secondVertex, thirdVertex, fourthVertex,
                firstVertex);
        return String.format(STRING_POLYGON_FORMAT, vertexes);
    }
}
