package com.project.yozmcafe.domain.cafe;

import com.project.yozmcafe.domain.cafe.available.AvailableTime;
import com.project.yozmcafe.domain.cafe.available.Days;
import com.project.yozmcafe.exception.BadRequestException;
import com.project.yozmcafe.fixture.Fixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.project.yozmcafe.exception.ErrorCode.INVALID_CAFE_ADDRESS;
import static com.project.yozmcafe.exception.ErrorCode.INVALID_CAFE_NAME;
import static com.project.yozmcafe.exception.ErrorCode.NOT_EXISTED_CAFE_DETAIL;
import static com.project.yozmcafe.exception.ErrorCode.NOT_EXISTED_CAFE_IMAGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

class CafeTest {

    @ParameterizedTest(name = "카페의 이름이 공백이면 예외가 발생한다")
    @NullAndEmptySource
    void invalidNameLength(final String name) {
        assertThatThrownBy(() -> new Cafe(name, "광안리", images(), detail()))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(INVALID_CAFE_NAME.getMessage());
    }

    @Test
    @DisplayName("카페 이름이 " + Cafe.NAME_MAX_LENGTH + " 보다 길면 예외가 발생한다")
    void invalidNameLength2() {
        //given
        final String name = makeString(Cafe.NAME_MAX_LENGTH + 1);

        //when, then
        assertThatThrownBy(() -> new Cafe(name, "광안리", images(), detail()))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(INVALID_CAFE_NAME.getMessage());
    }

    @ParameterizedTest(name = "카페의 주소가 공백이면 예외가 발생한다")
    @NullAndEmptySource
    void invalidAddress(final String address) {
        assertThatThrownBy(() -> new Cafe("연어카페", address, images(), detail()))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(INVALID_CAFE_ADDRESS.getMessage());
    }

    @Test
    @DisplayName("카페 주소가 " + Cafe.ADDRESS_MAX_LENGTH + " 보다 길면 예외가 발생한다")
    void invalidAddress2() {
        //given
        final String address = makeString(Cafe.ADDRESS_MAX_LENGTH + 1);

        //when, then
        assertThatThrownBy(() -> new Cafe("연어카페", address, images(), detail()))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(INVALID_CAFE_ADDRESS.getMessage());
    }

    @Test
    @DisplayName("이미지 정보가 하나도 없으면 예외가 발생한다")
    void invalidImages() {
        assertThatThrownBy(() -> new Images(Collections.emptyList()))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(NOT_EXISTED_CAFE_IMAGE.getMessage());
    }

    @Test
    @DisplayName("카페의 상세정보가 없으면 예외가 발생한다")
    void invalidDetail() {
        assertThatThrownBy(() -> new Cafe("연어카페", "광안리", images(), null))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(NOT_EXISTED_CAFE_DETAIL.getMessage());
    }

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

    @Test
    @DisplayName("카페의 likeCount가 0이면 감소시키지 않는다.")
    void subtractLikeCount2() {
        //given
        final Cafe cafe = Fixture.getCafe("카페", "카페주소", 0);

        //when
        cafe.subtractLikeCount();

        //then
        assertThat(cafe.getLikeCount()).isZero();
    }

    @Nested
    @DisplayName("카페 업데이트 테스트")
    class UpdateTest {
        @Test
        @DisplayName("ID값이 같지 않으면 업데이트되지 않는다.")
        void update() {
            //given
            final Cafe cafe = new Cafe(1L, "연어카페", "광안리", images(), detail(), 10);

            //when
            cafe.update(Fixture.getCafe("새 카페", "새 주소", 100));

            //then
            assertSoftly(softAssertions -> {
                assertThat(cafe.getName()).isEqualTo("연어카페");
                assertThat(cafe.getAddress()).isEqualTo("광안리");
                assertThat(cafe.getLikeCount()).isEqualTo(10);
            });
        }

        @Test
        @DisplayName("ID값이 같으면 전체가 업데이트 된다.")
        void update_name() {
            //given
            final Cafe cafe = new Cafe(1L, "연어카페", "광안리", images(), detail(), 10);

            //when
            cafe.update(Fixture.getCafe(1L, "새 카페", "새 주소", 100));

            //then
            assertSoftly(softAssertions -> {
                assertThat(cafe.getName()).isEqualTo("새 카페");
                assertThat(cafe.getAddress()).isEqualTo("새 주소");
                assertThat(cafe.getLikeCount()).isEqualTo(100);
            });
        }
    }

    private Images images() {
        return new Images(List.of("이미지1", "이미지2"));
    }

    private Detail detail() {
        final AvailableTime time1 = new AvailableTime(Days.MONDAY, LocalTime.now(), LocalTime.now(), true);
        final AvailableTime time2 = new AvailableTime(Days.TUESDAY, LocalTime.now(), LocalTime.now(), true);
        final AvailableTime time3 = new AvailableTime(Days.WEDNESDAY, LocalTime.now(), LocalTime.now(), true);
        final AvailableTime time4 = new AvailableTime(Days.THURSDAY, LocalTime.now(), LocalTime.now(), true);
        final AvailableTime time5 = new AvailableTime(Days.FRIDAY, LocalTime.now(), LocalTime.now(), true);
        final AvailableTime time6 = new AvailableTime(Days.SATURDAY, LocalTime.now(), LocalTime.now(), true);
        final AvailableTime time7 = new AvailableTime(Days.SUNDAY, LocalTime.now(), LocalTime.now(), true);

        return new Detail(List.of(time1, time2, time3, time4, time5, time6, time7), "mapUrl", "description", "phone");
    }

    private String makeString(final int size) {
        return Stream.generate(() -> "a")
                .limit(size)
                .collect(Collectors.joining());
    }
}
