package com.project.yozmcafe.domain.cafe.coordinate;

public class RadiusCalculator {
    private static final int METER_PER_DEGREE = 111_000;

    private RadiusCalculator() {
    }

    public static double calculate(final double latitude, final double latitudeDelta, final double longitudeDelta) {
        double radianLatitude = Math.toRadians(latitude);

        double metersLatitude = latitudeDelta * METER_PER_DEGREE;
        double metersLongitude = Math.cos(radianLatitude) * longitudeDelta * METER_PER_DEGREE;

        return Math.min(metersLatitude, metersLongitude);
    }
}
