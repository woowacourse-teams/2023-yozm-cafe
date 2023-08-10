package com.project.yozmcafe.service;

import com.project.yozmcafe.controller.dto.menu.MenuBoardResponse;
import com.project.yozmcafe.controller.dto.menu.MenuItemResponse;
import com.project.yozmcafe.controller.dto.menu.MenuResponse;
import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.domain.cafe.CafeRepository;
import com.project.yozmcafe.domain.menu.Menu;
import com.project.yozmcafe.domain.menu.MenuBoard;
import com.project.yozmcafe.domain.menu.MenuBoardRepository;
import com.project.yozmcafe.domain.menu.MenuRepository;
import com.project.yozmcafe.fixture.Fixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@Transactional
class MenuServiceTest {

    @Autowired
    private MenuService menuService;
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private MenuBoardRepository menuBoardRepository;
    @Autowired
    private CafeRepository cafeRepository;

    @Test
    @DisplayName("특정 카페의 메뉴판과 메뉴 목록을 조회한다.")
    void getMenus() {
        final Cafe savedCafe = cafeRepository.save(Fixture.getCafe("오션카페", "서울카페", 0));
        final Menu savedMenu = menuRepository.save(new Menu(savedCafe, 1L, "따뜻한 아이스 아메리카노", "아메리카노.img", "고소한 아메리카노", "5000",
                true));
        final MenuBoard savedMenuBoard = menuBoardRepository.save(new MenuBoard(savedCafe, 1L, "메뉴판 이미지"));

        final MenuResponse menuResponse = menuService.getMenus(savedCafe.getId());

        assertAll(
                () -> assertThat(menuResponse.cafeId()).isEqualTo(savedCafe.getId()),
                () -> assertThat(menuResponse.menuBoards()).containsExactly(MenuBoardResponse.from(savedMenuBoard)),
                () -> assertThat(menuResponse.menus()).containsExactly(MenuItemResponse.from(savedMenu))
        );
    }

    @Test
    @DisplayName("카페의 메뉴판과 메뉴 목록은 isRecommend, priority 순으로 정렬하여 조회한다.")
    void getMenus_sort() {
        final Cafe savedCafe = cafeRepository.save(Fixture.getCafe("오션카페", "서울카페", 0));
        final Menu savedMenu = menuRepository.save(new Menu(savedCafe, 1L, "따뜻한 아이스 아메리카노", "아메리카노.img", "고소한 아메리카노", "5000",
                false));
        final Menu savedMenu2 = menuRepository.save(new Menu(savedCafe, 2L, "따뜻한 아이스 아메리카노", "아메리카노.img", "고소한 아메리카노", "5000",
                true));

        final MenuBoard savedMenuBoard = menuBoardRepository.save(new MenuBoard(savedCafe, 1L, "메뉴판 이미지"));
        final MenuBoard savedMenuBoard2 = menuBoardRepository.save(new MenuBoard(savedCafe, 2L, "메뉴판 이미지"));

        final MenuResponse menuResponse = menuService.getMenus(savedCafe.getId());

        assertAll(
                () -> assertThat(menuResponse.cafeId()).isEqualTo(savedCafe.getId()),
                () -> assertThat(menuResponse.menuBoards()).containsExactly(MenuBoardResponse.from(savedMenuBoard),
                        MenuBoardResponse.from(savedMenuBoard2)),
                () -> assertThat(menuResponse.menus()).containsExactly(MenuItemResponse.from(savedMenu2),
                        MenuItemResponse.from(savedMenu))
        );
    }
}
