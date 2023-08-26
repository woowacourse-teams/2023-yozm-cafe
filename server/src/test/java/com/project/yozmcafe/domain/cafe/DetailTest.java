package com.project.yozmcafe.domain.cafe;

import com.project.yozmcafe.domain.cafe.available.AvailableTime;
import com.project.yozmcafe.domain.cafe.available.Days;
import com.project.yozmcafe.exception.BadRequestException;
import com.project.yozmcafe.exception.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.project.yozmcafe.exception.ErrorCode.INVALID_CAFE_MAP_URL;
import static com.project.yozmcafe.exception.ErrorCode.INVALID_CAFE_PHONE;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class DetailTest {

    @Test
    @DisplayName("중복된 날짜를 입력하면 예외가 발생한다")
    void validateAvailableTimes_fail1() {
        //given
        final AvailableTime time = new AvailableTime(Days.MONDAY, LocalTime.now(), LocalTime.now(), true);

        //when, then
        assertThatThrownBy(() -> new Detail(List.of(time, time, time, time, time, time, time),
                "mapUrl", "description", "phone"))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(ErrorCode.DUPLICATED_CAFE_AVAILABLE_TIMES.getMessage());
    }

    @Test
    @DisplayName("7일을 모두 입력하지 않으면 예외가 발생한다")
    void validateAvailableTimes_fail2() {
        //given
        final AvailableTime time = new AvailableTime(Days.MONDAY, LocalTime.now(), LocalTime.now(), true);

        //when, then
        assertThatThrownBy(() -> new Detail(List.of(time), "mapUrl", "description", "phone"))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(ErrorCode.INVALID_CAFE_AVAILABLE_TIMES.getMessage());
    }

    @Test
    @DisplayName("중복되지 않은 날짜들로 7개의 정보를 넣으면 예외가 발생하지 않는다")
    void validateAvailableTimes_success() {
        //given
        final AvailableTime time1 = new AvailableTime(Days.MONDAY, LocalTime.now(), LocalTime.now(), true);
        final AvailableTime time2 = new AvailableTime(Days.TUESDAY, LocalTime.now(), LocalTime.now(), true);
        final AvailableTime time3 = new AvailableTime(Days.WEDNESDAY, LocalTime.now(), LocalTime.now(), true);
        final AvailableTime time4 = new AvailableTime(Days.THURSDAY, LocalTime.now(), LocalTime.now(), true);
        final AvailableTime time5 = new AvailableTime(Days.FRIDAY, LocalTime.now(), LocalTime.now(), true);
        final AvailableTime time6 = new AvailableTime(Days.SATURDAY, LocalTime.now(), LocalTime.now(), true);
        final AvailableTime time7 = new AvailableTime(Days.SUNDAY, LocalTime.now(), LocalTime.now(), true);

        //when, then
        assertDoesNotThrow(() -> new Detail(List.of(time1, time2, time3, time4, time5, time6, time7),
                "mapUrl", "description", "phone"));
    }

    @ParameterizedTest(name = "지도 URL이 비어있으면 예외가 발생한다")
    @NullAndEmptySource
    void invalidMapUrlLength(final String mapUrl) {
        assertThatThrownBy(() -> new Detail(availableTimes(), mapUrl, "description", "phone"))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(INVALID_CAFE_MAP_URL.getMessage());
    }

    @Test
    @DisplayName("지도 URL의 길이가 " + Detail.MAP_URL_MAX_LENGTH + " 보다 길면 예외가 발생한다")
    void invalidMapUrlLength2() {
        //given
        final String mapUrl = makeString(Detail.MAP_URL_MAX_LENGTH + 1);

        //when, then
        assertThatThrownBy(() -> new Detail(availableTimes(), mapUrl, "description", "phone"))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(INVALID_CAFE_MAP_URL.getMessage());
    }

    @Test
    @DisplayName("휴대폰 번호의 길이가 " + Detail.PHONE_MAX_LENGTH + " 보다 길면 예외가 발생한다")
    void invalidPhoneLength() {
        //given
        final String phone = makeString(Detail.PHONE_MAX_LENGTH + 1);

        //when, then
        assertThatThrownBy(() -> new Detail(availableTimes(), "mapUrl", "description", phone))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(INVALID_CAFE_PHONE.getMessage());
    }

    private List<AvailableTime> availableTimes() {
        final AvailableTime time1 = new AvailableTime(Days.MONDAY, LocalTime.now(), LocalTime.now(), true);
        final AvailableTime time2 = new AvailableTime(Days.TUESDAY, LocalTime.now(), LocalTime.now(), true);
        final AvailableTime time3 = new AvailableTime(Days.WEDNESDAY, LocalTime.now(), LocalTime.now(), true);
        final AvailableTime time4 = new AvailableTime(Days.THURSDAY, LocalTime.now(), LocalTime.now(), true);
        final AvailableTime time5 = new AvailableTime(Days.FRIDAY, LocalTime.now(), LocalTime.now(), true);
        final AvailableTime time6 = new AvailableTime(Days.SATURDAY, LocalTime.now(), LocalTime.now(), true);
        final AvailableTime time7 = new AvailableTime(Days.SUNDAY, LocalTime.now(), LocalTime.now(), true);

        return List.of(time1, time2, time3, time4, time5, time6, time7);
    }

    private String makeString(final int size) {
        return Stream.generate(() -> "a")
                .limit(size)
                .collect(Collectors.joining());
    }
}
