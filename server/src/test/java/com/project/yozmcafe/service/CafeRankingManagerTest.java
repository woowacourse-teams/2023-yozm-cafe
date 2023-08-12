package com.project.yozmcafe.service;

import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.exception.BadRequestException;
import com.project.yozmcafe.exception.ErrorCode;
import com.project.yozmcafe.fixture.Fixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CafeRankingManagerTest {

    private final CafeRankingManager cafeRankingManager = new CafeRankingManager();

    @Test
    @DisplayName("카페의 순위를 계산한다.")
    void getRank() {
        //given
        final Cafe cafe6 = Fixture.getCafe("cafe6", "address6", 2);
        final List<Cafe> cafes = List.of(Fixture.getCafe("cafe1", "address1", 7),
                Fixture.getCafe("cafe2", "address2", 6),
                Fixture.getCafe("cafe3", "address3", 5),
                Fixture.getCafe("cafe4", "address4", 4),
                Fixture.getCafe("cafe5", "address5", 3),
                cafe6,
                Fixture.getCafe("cafe7", "address7", 1)
        );
        final Pageable pageRequest = PageRequest.of(1, 5);

        //when
        final int rank = cafeRankingManager.getRank(cafes, cafe6, pageRequest);

        //then
        assertThat(rank).isEqualTo(6);
    }

    @Test
    @DisplayName("카페의 순위를 계산할 때, 순위 범위를 벗어나면 예외를 발생시킨다.")
    void getRankFailWhenOutOfBounds() {
        //given
        final Cafe cafe1 = Fixture.getCafe("cafe1", "address1", 5);
        final List<Cafe> cafes = List.of(cafe1,
                Fixture.getCafe("cafe2", "address2", 4),
                Fixture.getCafe("cafe3", "address3", 3),
                Fixture.getCafe("cafe4", "address4", 2),
                Fixture.getCafe("cafe5", "address5", 1)
        );
        final Pageable pageRequest = PageRequest.of(3, 10);

        //when, then
        assertThatThrownBy(() -> cafeRankingManager.getRank(cafes, cafe1, pageRequest))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(ErrorCode.RANK_OUT_OF_BOUNDS.getMessage());
    }
}
