package com.project.yozmcafe.domain.cafe.coordinate;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RadiusCalculatorTest {

    @Test
    @DisplayName("radius를 계산하여 반환한다")
    void calculate() {
        //given
        final double latitude = 20;
        final double latitudeDelta = 0.001;
        final double longitudeDelta = 10;

        //when
        final double radius = RadiusCalculator.calculate(latitude, latitudeDelta, longitudeDelta);

        //then
        assertThat(radius).isEqualTo(111);
    }
}
