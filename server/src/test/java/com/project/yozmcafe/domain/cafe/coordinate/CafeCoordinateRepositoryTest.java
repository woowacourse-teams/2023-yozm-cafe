package com.project.yozmcafe.domain.cafe.coordinate;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.project.yozmcafe.BaseTest;
import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.domain.cafe.CafeRepository;
import com.project.yozmcafe.domain.cafe.PointGenerator;
import com.project.yozmcafe.domain.cafe.coordinate.dto.CafePinDto;
import com.project.yozmcafe.fixture.Fixture;

class CafeCoordinateRepositoryTest extends BaseTest {

    @Autowired
    private CafeCoordinateRepository cafeCoordinateRepository;

    @Autowired
    private CafeRepository cafeRepository;

    @Test
    @DisplayName("반경내의 카페 정보를 모두 반환한다.")
    void findCafePinsFromCoordinate() {
        //given
        final Cafe cafe1 = cafeRepository.save(Fixture.getCafe("cafe1", "주소1", 0));
        final Cafe cafe2 = cafeRepository.save(Fixture.getCafe("cafe2", "주소2", 0));

        final CafeCoordinate coordinate1 = new CafeCoordinate(PointGenerator.generateWithCoordinate(20, 10), cafe1);
        cafeCoordinateRepository.save(coordinate1);

        final CafeCoordinate coordinate2 = new CafeCoordinate(PointGenerator.generateWithCoordinate(60, 50), cafe2);
        cafeCoordinateRepository.save(coordinate2);

        //when
        final List<CafePinDto> cafePins = cafeCoordinateRepository.findCafePinsFromCoordinate(
                "POINT(20.00001 10.00001)", 500);

        //then
        assertThat(cafePins).hasSize(1);
    }
}
