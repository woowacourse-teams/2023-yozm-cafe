package com.project.yozmcafe.domain.cafe;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

public class PointGenerator {

    private static final GeometryFactory GEOMETRY_FACTORY = new GeometryFactory();
    private static final int SRID = 4326;

    private PointGenerator() {
    }

    public static Point generateWithCoordinate(final double latitude, final double longitude) {
        final Point point = GEOMETRY_FACTORY.createPoint(new Coordinate(longitude, latitude));
        point.setSRID(SRID);
        return point;
    }
}
