package com.project.yozmcafe.domain.cafe;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

public class PointGenerator {

    private static final GeometryFactory GEOMETRY_FACTORY = new GeometryFactory();
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
