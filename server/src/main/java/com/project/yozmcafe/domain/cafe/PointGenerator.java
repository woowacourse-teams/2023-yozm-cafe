package com.project.yozmcafe.domain.cafe;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

public class PointGenerator {

    private static final GeometryFactory GEOMETRY_FACTORY = new GeometryFactory();
    /**
     * SRID(Spatial Reference Identifier)는 고유한 공간 좌표 식별자입니다.
     * <p>
     * 그 중, 4326은 WGS84 경위도 좌표계를 의미합니다. GPS 기술에서도 사용되는 좌표계로 범용적으로 가장 널리 사용되는 좌표계입니다.
     */
    private static final int SRID = 4326;
    private static final String STRING_POINT_FORMAT = "POINT(%s)";
    private static final String STRING_POINT_DELIMITER = " ";

    private PointGenerator() {
    }

    public static Point generateWithCoordinate(final double latitude, final double longitude) {
        final Point point = GEOMETRY_FACTORY.createPoint(new Coordinate(longitude, latitude));
        point.setSRID(SRID);
        return point;
    }

    public static String generateStringPoint(final double latitude, final double longitude) {
        final String coordinate = String.join(
                STRING_POINT_DELIMITER,
                String.valueOf(latitude),
                String.valueOf(longitude)
        );
        return String.format(STRING_POINT_FORMAT, coordinate);
    }
}
