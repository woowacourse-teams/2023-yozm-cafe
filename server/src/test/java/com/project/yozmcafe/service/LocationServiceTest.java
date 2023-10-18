package com.project.yozmcafe.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.project.yozmcafe.controller.dto.cafe.CafeLocationRequest;
import com.project.yozmcafe.controller.dto.cafe.CafeLocationResponse;
import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.domain.cafe.CafeRepository;
import com.project.yozmcafe.domain.cafe.GeometryGenerator;
import com.project.yozmcafe.domain.cafe.coordinate.CafeCoordinate;
import com.project.yozmcafe.domain.cafe.coordinate.CafeCoordinateRepository;
import com.project.yozmcafe.fixture.Fixture;

class LocationServiceTest extends BaseServiceTest {

    @Autowired
    private LocationService locationService;

    @Autowired
    private CafeCoordinateRepository cafeCoordinateRepository;

    @Autowired
    private CafeRepository cafeRepository;

    @Test
    @DisplayName("기준 좌표와, 델타 좌표를 받아 반경안에 포함된 카페를 반환한다.")
    void findCafesFromLocations() {
        //given
        final Cafe cafe1 = cafeRepository.save(Fixture.getCafe("cafe1", "주소1", 0));
        final Cafe cafe2 = cafeRepository.save(Fixture.getCafe("cafe2", "주소2", 0));

        final CafeCoordinate coordinate1 = new CafeCoordinate(GeometryGenerator.generatePointWithCoordinate(20, 10),
                cafe1);
        cafeCoordinateRepository.save(coordinate1);

        final CafeCoordinate coordinate2 = new CafeCoordinate(GeometryGenerator.generatePointWithCoordinate(60, 50),
                cafe2);
        cafeCoordinateRepository.save(coordinate2);

        final CafeLocationRequest cafeLocationRequest = new CafeLocationRequest(
                20.00001,
                10.00001,
                0.004504504505,
                1
        );

        //when
        final List<CafeLocationResponse> results = locationService.findCafesFromLocations(
                cafeLocationRequest
        );

        //then
        assertSoftly(softAssertions -> {
            assertThat(results).hasSize(1);
            assertThat(results.get(0).id()).isEqualTo(cafe1.getId());
            assertThat(results.get(0).latitude()).isEqualTo(20);
            assertThat(results.get(0).longitude()).isEqualTo(10);
        });
    }
}
