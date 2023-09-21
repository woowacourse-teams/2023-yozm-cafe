package com.project.yozmcafe.domain.cafe;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Point;

import com.project.yozmcafe.domain.cafe.coordinate.CafeCoordinate;
import com.project.yozmcafe.fixture.Fixture;

class CafeCoordinateTest {

    @Test
    @DisplayName("위도와 경도를 반환한다.")
    void getLatitudeAndLongitude() {
        //given
        final double latitude = 20.0;
        final double longitude = 10.0;
        final Point point = GeometryGenerator.generatePointWithCoordinate(latitude, longitude);
        final Cafe cafe = Fixture.getCafe("카페", "성수동", 0);
        final CafeCoordinate cafeCoordinate = new CafeCoordinate(point, cafe);

        //when
        final double resultLatitude = cafeCoordinate.getLatitude();
        final double resultLongitude = cafeCoordinate.getLongitude();

        //then
        assertSoftly(softAssertions -> {
            assertThat(resultLatitude).isEqualTo(latitude);
            assertThat(resultLongitude).isEqualTo(longitude);
        });
    }
}
