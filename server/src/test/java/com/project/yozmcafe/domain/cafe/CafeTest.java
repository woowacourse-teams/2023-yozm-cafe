package com.project.yozmcafe.domain.cafe;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.project.yozmcafe.fixture.Fixture;

class CafeTest {
    @Test
    @DisplayName("카페의 likeCount를 1 추가한다.")
    void addLikeCount() {
        //given
        final Cafe cafe = Fixture.getCafe("카페", "카페주소", 10);

        //when
        cafe.addLikeCount();

        //then
        assertThat(cafe.getLikeCount()).isEqualTo(11);
    }

    @Test
    @DisplayName("카페의 likeCount를 1 감소시킨다.")
    void subtractLikeCount() {
        //given
        final Cafe cafe = Fixture.getCafe("카페", "카페주소", 10);

        //when
        cafe.subtractLikeCount();

        //then
        assertThat(cafe.getLikeCount()).isEqualTo(9);
    }
}
