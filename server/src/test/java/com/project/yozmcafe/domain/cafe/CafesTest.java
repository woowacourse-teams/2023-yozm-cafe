package com.project.yozmcafe.domain.cafe;

import static com.project.yozmcafe.fixture.Fixture.CAFE_1;
import static com.project.yozmcafe.fixture.Fixture.CAFE_2;
import static com.project.yozmcafe.fixture.Fixture.CAFE_3;
import static com.project.yozmcafe.fixture.Fixture.CAFE_4;
import static com.project.yozmcafe.fixture.Fixture.CAFE_5;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CafesTest {

    @Test
    @DisplayName("카페리스트에서 중복되지 않는 3개의 랜덤한 카페를 가져온다.")
    void pickRandomCafe() {
        //given
        final Cafes cafes = new Cafes(List.of(CAFE_1, CAFE_2, CAFE_3, CAFE_4, CAFE_5));

        //when
        final List<Cafe> randomCafes = cafes.pickRandomCafe();

        //then
        Assertions.assertThat(randomCafes).hasSize(3);
    }
}
