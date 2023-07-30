package com.project.yozmcafe.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.data.domain.PageRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class RandomPageRequestGeneratorTest {

    private final RandomPageRequestGenerator pageRequestGenerator = new RandomPageRequestGenerator();

    @DisplayName("생성된 pageRequest 의 페이지 범위가 카페 개수에 따라 가능한 범위 이내 이어야 한다.")
    @RepeatedTest(20)
    void getPageRequest() {
        //given
        //when
        PageRequest result = pageRequestGenerator.getPageRequestWithCafeCount(12L, 5);

        //then
        int pageNumber = result.getPageNumber();
        assertAll(
                () -> assertThat(pageNumber).isGreaterThanOrEqualTo(0),
                () -> assertThat(pageNumber).isLessThanOrEqualTo(12 / 5)
        );
    }
}
