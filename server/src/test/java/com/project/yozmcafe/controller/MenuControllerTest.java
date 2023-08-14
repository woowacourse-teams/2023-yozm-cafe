package com.project.yozmcafe.controller;

import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.domain.cafe.CafeRepository;
import com.project.yozmcafe.domain.menu.Menu;
import com.project.yozmcafe.domain.menu.MenuBoard;
import com.project.yozmcafe.domain.menu.MenuBoardRepository;
import com.project.yozmcafe.domain.menu.MenuRepository;
import com.project.yozmcafe.fixture.Fixture;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.document;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;

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
        final MenuBoard savedMenuBoard = menuBoardRepository.save(new MenuBoard(cafe, 1L, "메뉴판"));

        //when
        final Response response = given(spec).log().all()
                .filter(document("메뉴/메뉴 조회",
                        pathParameters(
                                parameterWithName("cafeId").description("카페 ID")
                        ), responseFields(
                                fieldWithPath("cafeId").description("카페 ID"),
                                fieldWithPath("menuBoards[].id").description("메뉴판 ID"),
                                fieldWithPath("menuBoards[].priority").description("메뉴판 우선순위"),
                                fieldWithPath("menuBoards[].imageUrl").description("메뉴판 이미지 url"),
                                fieldWithPath("menus[].id").description("메뉴 ID"),
                                fieldWithPath("menus[].priority").description("메뉴 우선순위"),
                                fieldWithPath("menus[].name").description("메뉴 이름"),
                                fieldWithPath("menus[].imageUrl").description("메뉴 이미지 url"),
                                fieldWithPath("menus[].description").description("메뉴 설명"),
                                fieldWithPath("menus[].price").description("메뉴 가격"),
                                fieldWithPath("menus[].isRecommended").description("대표 메뉴 여부")
                        )
                ))
                .when()
                .get("/cafes/{cafeId}/menus", cafe.getId());

        //then
        assertAll(
                () -> assertThat(response.jsonPath().getString("menus[0].name")).isEqualTo(savedMenu.getName()),
                () -> assertThat(response.jsonPath().getString("menus[0].imageUrl")).isEqualTo(savedMenu.getImageUrl()),
                () -> assertThat(response.jsonPath().getString("menus[0].description")).isEqualTo(savedMenu.getDescription()),
                () -> assertThat(response.jsonPath().getString("menuBoards[0].imageUrl")).isEqualTo(savedMenuBoard.getImageUrl())
        );
    }
}
