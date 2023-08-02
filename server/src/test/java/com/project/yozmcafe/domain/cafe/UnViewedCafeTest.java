package com.project.yozmcafe.domain.cafe;

import com.project.yozmcafe.domain.member.Member;
import com.project.yozmcafe.fixture.Fixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UnViewedCafeTest {

    @Test
    @DisplayName("cafeId와 같으면 true 반환")
    void equalsCafeId() {
        //given
        final long cafeId = 100L;

        final UnViewedCafe unViewedCafe = new UnViewedCafe(1L,
                Fixture.getCafe(cafeId, "성수카페", "주소", 6),
                new Member("id", "연어", "사진"));

        //when, then
        assertThat(unViewedCafe.equalsCafeId(cafeId)).isTrue();
    }

    @Test
    @DisplayName("cafeId와 다르면 false 반환")
    void equalsCafeId_false() {
        //given
        final UnViewedCafe unViewedCafe = new UnViewedCafe(1L,
                Fixture.getCafe(100L, "성수카페", "주소", 6),
                new Member("id", "연어", "사진"));

        //when, then
        assertThat(unViewedCafe.equalsCafeId(0L)).isFalse();
    }
}
