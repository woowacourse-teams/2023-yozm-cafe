package com.project.yozmcafe.domain.cafe;

import com.project.yozmcafe.BaseTest;
import com.project.yozmcafe.domain.menu.MenuRepository;
import com.project.yozmcafe.fixture.Fixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CafeQueryRepositoryTest extends BaseTest {

    @Autowired
    private CafeQueryRepository cafeQueryRepository;
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private CafeRepository cafeRepository;

    private Cafe cafe1, cafe2, cafe3, cafe4;

    @BeforeEach
    void setUp() {
        cafe1 = cafeRepository.save(Fixture.getCafe("카페1", "도치 주소1", 1));
        cafe2 = cafeRepository.save(Fixture.getCafe("카페2", "카페 주소2", 2));
        cafe3 = cafeRepository.save(Fixture.getCafe("도치카페3", "카페 주소3", 3));
        cafe4 = cafeRepository.save(Fixture.getCafe("도치카페4", "도치 카페 주소4", 4));
    }

    @Test
    @DisplayName("카페 이름으로 카페를 조회한다")
    void findByCafeName() {
        // given
        final String searchWord = "도치카페";
        final boolean isCafeName = true;
        final boolean isMenu = false;
        final boolean isAddress = false;

        // when
        final List<Cafe> cafes = cafeQueryRepository.searchCafesByWord(searchWord, isCafeName, isMenu, isAddress);

        // then
        assertAll(
                () -> assertThat(cafes).extracting(Cafe::getName).containsOnly("도치카페3", "도치카페4"),
                () -> assertThat(cafes).hasSize(2)
        );
    }

    @Test
    @DisplayName("메뉴 이름으로 카페를 조회한다")
    void findByMenuName() {
        // given
        final String searchWord = "도치 음료";
        final boolean isCafeName = false;
        final boolean isMenu = true;
        final boolean isAddress = false;
        menuRepository.save(Fixture.getMenu(null, cafe1, 1, "도치 음료", "imageUrl1", "description", "3000원", true));
        menuRepository.save(Fixture.getMenu(null, cafe1, 2, "아무 음료", "imageUrl2", "description", "3500원", true));
        menuRepository.save(Fixture.getMenu(null, cafe2, 2, "도치 음료", "imageUrl3", "description", "4000원", false));
        menuRepository.save(Fixture.getMenu(null, cafe3, 1, "도치", "imageUrl4", "description", "2000원", true));

        // when
        final List<Cafe> cafes = cafeQueryRepository.searchCafesByWord(searchWord, isCafeName, isMenu, isAddress);

        // then
        assertAll(
                () -> assertThat(cafes).extracting(Cafe::getName).containsOnly(cafe1.getName(), cafe2.getName()),
                () -> assertThat(cafes).hasSize(2)
        );
    }

    @Test
    @DisplayName("주소로 카페를 조회한다")
    void findByAddress() {
        // given
        final String searchWord = "카페 주소";
        final boolean isCafeName = false;
        final boolean isMenu = false;
        final boolean isAddress = true;

        // when
        final List<Cafe> cafes = cafeQueryRepository.searchCafesByWord(searchWord, isCafeName, isMenu, isAddress);

        // then
        assertAll(
                () -> assertThat(cafes).extracting(Cafe::getAddress).containsOnly("카페 주소2", "카페 주소3", "도치 카페 주소4"),
                () -> assertThat(cafes).extracting(Cafe::getName).containsOnly(cafe2.getName(), cafe3.getName(), cafe4.getName()),
                () -> assertThat(cafes).hasSize(3)
        );
    }

    @Test
    @DisplayName("카페 이름,메뉴로 카페를 조회한다")
    void findByNameAndMenu() {
        // given
        final String searchWord = "도치";
        final boolean isCafeName = true;
        final boolean isMenu = true;
        final boolean isAddress = false;
        menuRepository.save(Fixture.getMenu(null, cafe3, 1, "도치 음료", "imageUrl1", "description", "3000원", true));

        // when
        final List<Cafe> cafes = cafeQueryRepository.searchCafesByWord(searchWord, isCafeName, isMenu, isAddress);

        // then
        assertThat(cafes).extracting(Cafe::getName).containsOnly(cafe3.getName());
    }

    @Test
    @DisplayName("카페 이름,주소로 카페를 조회한다")
    void findByNameAndAddress() {
        // given
        final String searchWord = "도치";
        final boolean isCafeName = true;
        final boolean isMenu = false;
        final boolean isAddress = true;

        // when
        final List<Cafe> cafes = cafeQueryRepository.searchCafesByWord(searchWord, isCafeName, isMenu, isAddress);

        // then
        assertThat(cafes).extracting(Cafe::getName).containsOnly(cafe4.getName());
    }

    @Test
    @DisplayName("카페 이름,메뉴,주소로 카페를 조회한다")
    void findByAllFilters() {
        // given
        final String searchWord = "도치";
        final boolean isCafeName = true;
        final boolean isMenu = true;
        final boolean isAddress = true;
        menuRepository.save(Fixture.getMenu(null, cafe4, 1, "도치 음료", "imageUrl1", "description", "3000원", true));

        // when
        final List<Cafe> cafes = cafeQueryRepository.searchCafesByWord(searchWord, isCafeName, isMenu, isAddress);

        // then
        assertAll(
                () -> assertThat(cafes).extracting(Cafe::getName).containsOnly(cafe4.getName()),
                () -> assertThat(cafes).hasSize(1)
        );
    }

    @Test
    @DisplayName("아무 검색 기준도 없이 카페를 조회한다 - 카페명or카페메뉴or카페주소 검색")
    void findByNoFilter() {
        // given
        final String searchWord = "도치";
        final boolean isCafeName = false;
        final boolean isMenu = false;
        final boolean isAddress = false;

        // when
        final List<Cafe> cafes = cafeQueryRepository.searchCafesByWord(searchWord, isCafeName, isMenu, isAddress);

        // then
        assertAll(
                () -> assertThat(cafes).extracting(Cafe::getName).containsOnly(cafe1.getName(), cafe3.getName(), cafe4.getName()),
                () -> assertThat(cafes).hasSize(3)
        );
    }
}

