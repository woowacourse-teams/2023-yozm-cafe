package com.project.yozmcafe.controller.menu;

import static com.project.yozmcafe.controller.menu.MenuDocuments.menuAndMenuBoardDocument;
import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.project.yozmcafe.controller.BaseControllerTest;
import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.domain.cafe.CafeRepository;
import com.project.yozmcafe.domain.menu.Menu;
import com.project.yozmcafe.domain.menu.MenuBoard;
import com.project.yozmcafe.domain.menu.MenuBoardRepository;
import com.project.yozmcafe.domain.menu.MenuRepository;
import com.project.yozmcafe.fixture.Fixture;

import io.restassured.response.Response;

class MenuControllerTest extends BaseControllerTest {

    @Autowired
    private CafeRepository cafeRepository;
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private MenuBoardRepository menuBoardRepository;

    @Test
    @DisplayName("특정 카페의 메뉴와 메뉴판을 조회 한다.")
    void getMenus() {
        //given
        final Cafe cafe = cafeRepository.save(Fixture.getCafe("오션카페", "선릉역", 1));
        final Menu savedMenu = menuRepository.save(new Menu(cafe, 1, "뜨거운 아이스 아메리카노",
                "아메리카노.img", "뜨겁지만 차가워요", "5000", true));
        final MenuBoard savedMenuBoard = menuBoardRepository.save(new MenuBoard(cafe, 1, "메뉴판"));

        //when
        final Response response = customGivenWithDocs(menuAndMenuBoardDocument())
                .get("/cafes/{cafeId}/menus", cafe.getId());

        //then
        SoftAssertions.assertSoftly(softAssertions -> {
            assertThat(response.jsonPath().getString("menus[0].name")).isEqualTo(savedMenu.getName());
            assertThat(response.jsonPath().getString("menus[0].imageUrl")).isEqualTo(savedMenu.getImageUrl());
            assertThat(response.jsonPath().getString("menus[0].description")).isEqualTo(savedMenu.getDescription());
            assertThat(response.jsonPath().getString("menuBoards[0].imageUrl")).isEqualTo(savedMenuBoard.getImageUrl());
        });
    }
}
