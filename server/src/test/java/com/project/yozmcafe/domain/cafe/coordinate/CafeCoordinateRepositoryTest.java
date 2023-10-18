package com.project.yozmcafe.domain.cafe.coordinate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Polygon;
import org.springframework.beans.factory.annotation.Autowired;

import com.project.yozmcafe.BaseTest;
import com.project.yozmcafe.controller.dto.cafe.CafeLocationRequest;
import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.domain.cafe.CafeRepository;
import com.project.yozmcafe.domain.cafe.GeometryGenerator;
import com.project.yozmcafe.domain.cafe.coordinate.dto.CafePinDto;
import com.project.yozmcafe.fixture.Fixture;

class CafeCoordinateRepositoryTest extends BaseTest {

    @Autowired
    private CafeCoordinateRepository cafeCoordinateRepository;

    @Autowired
    private CafeRepository cafeRepository;

    @Test
    @DisplayName("영역 내의 모든 카페 정보를 반환한다.")
    void findCafePinsFromCoordinateWithAreaTest() {
        //given
        final Cafe cafe1 = cafeRepository.save(Fixture.getCafe("cafe1", "주소1", 0));
        final Cafe cafe2 = cafeRepository.save(Fixture.getCafe("cafe2", "주소2", 0));

        final CafeCoordinate coordinate1 = new CafeCoordinate(GeometryGenerator.generatePointWithCoordinate(20, 10),
                cafe1);
        cafeCoordinateRepository.save(coordinate1);

        final CafeCoordinate coordinate2 = new CafeCoordinate(GeometryGenerator.generatePointWithCoordinate(60, 50),
                cafe2);
        cafeCoordinateRepository.save(coordinate2);
        final CafeLocationRequest cafeLocationRequest = new CafeLocationRequest(20, 10, 3, 1);
        final Polygon area = GeometryGenerator.generatePolygon(cafeLocationRequest);

        //when
        final List<CafePinDto> cafePins = cafeCoordinateRepository.findCafePinsFromCoordinate(area);

        //then
        assertSoftly(softAssertions -> {
            assertThat(cafePins).hasSize(1);
            assertThat(cafePins.get(0).getId()).isEqualTo(cafe1.getId());
        });
    }
}
