package com.project.yozmcafe.domain.cafe;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;

import com.project.yozmcafe.controller.dto.cafe.CafeLocationRequest;

class GeometryGeneratorTest {

    @Test
    @DisplayName("포인트 생성 테스트")
    void generate() {
        //given
        final double latitude = 20;
        final double longitude = 10;
        final Point point = GeometryGenerator.generatePointWithCoordinate(latitude, longitude);

        //when
        final int srid = point.getSRID();
        final double resultLatitude = point.getY();
        final double resultLongitude = point.getX();

        //then
        assertSoftly(softAssertions -> {
            assertThat(srid).isEqualTo(4326);
            assertThat(resultLatitude).isEqualTo(latitude);
            assertThat(resultLongitude).isEqualTo(longitude);
        });
    }

    @Test
    @DisplayName("폴리곤 생성 테스트")
    void generatePolygonTest() {
        //given
        final CafeLocationRequest cafeLocationRequest = new CafeLocationRequest(20, 10, 3, 1);

        //when
        final Polygon polygon = GeometryGenerator.generatePolygon(cafeLocationRequest);

        //then
        assertSoftly(softAssertions -> {
            assertThat(polygon.getCoordinates()).hasSize(5);
            assertThat(polygon.getCoordinates()).containsExactly(
                    new Coordinate(11, 17),
                    new Coordinate(11, 23),
                    new Coordinate(9, 23),
                    new Coordinate(9, 17),
                    new Coordinate(11, 17)
            );
        });
    }
}
