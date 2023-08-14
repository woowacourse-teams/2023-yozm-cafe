package com.project.yozmcafe.domain.menu;

import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.exception.BadRequestException;
import com.project.yozmcafe.exception.ErrorCode;
import com.project.yozmcafe.fixture.Fixture;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MenuBoardTest {

    @ParameterizedTest(name = "메뉴판 이미지가 공백이면 예외가 발생한다.")
    @NullAndEmptySource
    void invalidImageUrl(String imageUrl) {
        //given
        final Cafe cafe = Fixture.getCafe("오션카페", "서울카페", 0);

        //when & then
        assertThatThrownBy(() -> new MenuBoard(cafe, 1L, imageUrl))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(ErrorCode.NOT_EXISTED_MENU_BOARD_IMAGE.getMessage());
    }
}
