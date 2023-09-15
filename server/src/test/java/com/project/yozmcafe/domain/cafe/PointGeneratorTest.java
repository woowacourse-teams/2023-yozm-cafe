package com.project.yozmcafe.domain.cafe;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Point;

class PointGeneratorTest {

    @Test
    @DisplayName("포인트 생성 테스트")
    void generate() {
        //given
        final double latitude = 20;
        final double longitude = 10;
        final Point point = PointGenerator.generateWithCoordinate(latitude, longitude);

        //when
        final int srid = point.getSRID();
        final double resultLatitude = point.getY();
        final double resultLongitude = point.getX();

        //then
        SoftAssertions.assertSoftly(softAssertions -> {
            assertThat(srid).isEqualTo(4326);
            assertThat(resultLatitude).isEqualTo(latitude);
            assertThat(resultLongitude).isEqualTo(longitude);
        });
    }

    @Test
    @DisplayName("스트링 포인트 생성")
    void generateStringPoint() {
        //given
        final double latitude = 20;
        final double longitude = 10;

        //when
        final String point = PointGenerator.generateStringPoint(latitude, longitude);

        //then
        assertThat(point).isEqualTo("POINT(20.0 10.0)");
    }
}
