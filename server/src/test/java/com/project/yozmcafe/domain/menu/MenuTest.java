package com.project.yozmcafe.domain.menu;

import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.exception.BadRequestException;
import com.project.yozmcafe.exception.ErrorCode;
import com.project.yozmcafe.fixture.Fixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MenuTest {
    @ParameterizedTest(name = "메뉴의 이름이 공백이면 예외가 발생한다.")
    @NullAndEmptySource
    void invalidName(final String name) {
        //given
        final Cafe cafe = Fixture.getCafe("오션카페", "서울카페", 0);

        //when & then
        assertThatThrownBy(() -> new Menu(cafe, 1, name, "아메리카노.img", "고소한 아메리카노", "5000",
                true))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(ErrorCode.INVALID_MENU_NAME.getMessage());
    }

    @Test
    @DisplayName("메뉴 이름 길이가 " + Menu.MAX_NAME_LENGTH + "를 초과하면 예외가 발생한다.")
    void invalidNameLength() {
        //given
        final Cafe cafe = Fixture.getCafe("오션카페", "서울카페", 0);

        //when & then
        assertThatThrownBy(() -> new Menu(cafe, 1, "따뜻하면서도차갑고식지않는아이스아메리카노", "아메리카노.img", "고소한 아메리카노", "5000",
                true))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(ErrorCode.INVALID_MENU_NAME.getMessage());
    }

    @ParameterizedTest(name = "메뉴 가격이 공백이면 예외가 발생한다.")
    @NullAndEmptySource
    void invalidPrice(final String price) {
        //given
        final Cafe cafe = Fixture.getCafe("오션카페", "서울카페", 0);

        //when & then
        assertThatThrownBy(() -> new Menu(cafe, 1, "따듯한 아이스 아메리카노", "아메리카노.img", "고소한 아메리카노",
                price, true))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(ErrorCode.NOT_EXISTED_MENU_PRICE.getMessage());
    }
}
