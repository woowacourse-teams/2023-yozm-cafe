package com.project.yozmcafe.domain.cafe.available;

import com.project.yozmcafe.exception.BadRequestException;
import com.project.yozmcafe.exception.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class AvailableTimeTest {

    @Test
    @DisplayName("생성 테스트")
    void construct() {
        assertDoesNotThrow(() -> new AvailableTime(Days.MONDAY, LocalTime.now(), LocalTime.now(), true));
    }

    @Test
    @DisplayName("open이 close보다 이후일 경우 예외가 발생한다")
    void construct_fail() {
        //given
        final LocalTime open = LocalTime.of(23, 0);
        final LocalTime close = LocalTime.of(22, 0);

        //when, then
        assertThatThrownBy(() -> new AvailableTime(Days.MONDAY, open, close, true))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(ErrorCode.INVALID_CAFE_AVAILABLE_TIME.getMessage());
    }
}
