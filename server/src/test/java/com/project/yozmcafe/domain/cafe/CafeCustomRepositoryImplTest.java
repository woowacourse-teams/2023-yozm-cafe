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
import static org.junit.jupiter.api.Assertions.assertAll;

class CafeCustomRepositoryImplTest extends BaseTest {

    @Autowired
    private CafeCustomRepositoryImpl cafeCustomRepositoryImpl;
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private CafeRepository cafeRepository;

    private Cafe cafe1, cafe2, cafe3, cafe4;

    @BeforeEach
    void setUp() {
        cafe1 = cafeRepository.save(Fixture.getCafe("카페1", "도치 주소1", 1));
        cafe2 = cafeRepository.save(Fixture.getCafe("카페2", "카페 주소2", 2));
        cafe3 = cafeRepository.save(Fixture.getCafe("도치 카페3", "카페 주소3", 3));
        cafe4 = cafeRepository.save(Fixture.getCafe("도치 카페4", "도치 카페 주소4", 4));
    }

    @Test
    @DisplayName("카페 이름으로 카페를 조회한다")
    void findByCafeName() {
        // given
        final String cafeName = "도치 카페";
        final String address = null;

        // when
        final List<Cafe> cafes = cafeCustomRepositoryImpl.findAllBy(cafeName, address);

        // then
        assertAll(
                () -> assertThat(cafes).extracting(Cafe::getName).containsOnly("도치 카페3", "도치 카페4"),
                () -> assertThat(cafes).hasSize(2)
        );
    }

    @Test
    @DisplayName("메뉴 이름으로 카페를 조회한다")
    void findByMenuName() {
        // given
        final String cafeName = null;
        final String menu = "도치 음료";
        final String address = null;

        menuRepository.save(Fixture.getMenu(cafe1, 1, "도치 음료1"));
        menuRepository.save(Fixture.getMenu(cafe1, 2, "도치 음료2"));
        menuRepository.save(Fixture.getMenu(cafe1, 3, "아무 음료"));
        menuRepository.save(Fixture.getMenu(cafe2, 2, "도치 음료"));
        menuRepository.save(Fixture.getMenu(cafe3, 1, "도치"));

        // when
        final List<Cafe> cafes = cafeCustomRepositoryImpl.findAllBy(cafeName, menu, address);

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
        final String cafeName = null;
        final String address = "카페 주소";

        // when
        final List<Cafe> cafes = cafeCustomRepositoryImpl.findAllBy(cafeName, address);

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
        final String cafeName = "카페1";
        final String menu = "음료";
        final String address = null;

        menuRepository.save(Fixture.getMenu(cafe1, 1, "음료1"));
        menuRepository.save(Fixture.getMenu(cafe2, 1, "음료2"));

        // when
        final List<Cafe> cafes = cafeCustomRepositoryImpl.findAllBy(cafeName, menu, address);

        // then
        assertThat(cafes).extracting(Cafe::getName).containsOnly(cafe1.getName());
    }

    @Test
    @DisplayName("카페 이름,주소로 카페를 조회한다")
    void findByNameAndAddress() {
        // given
        final String cafeName = "카페";
        final String address = "주소3";

        // when
        final List<Cafe> cafes = cafeCustomRepositoryImpl.findAllBy(cafeName, address);

        // then
        assertThat(cafes).extracting(Cafe::getName).containsOnly(cafe3.getName());
    }

    @Test
    @DisplayName("카페 메뉴,주소로 카페를 조회한다")
    void findByMenuAndAddress() {
        // given
        final String cafeName = null;
        final String menu = "음료";
        final String address = "카페 주소";

        menuRepository.save(Fixture.getMenu(cafe2, 1, "음료2"));
        menuRepository.save(Fixture.getMenu(cafe3, 1, "음료3"));

        // when
        final List<Cafe> cafes = cafeCustomRepositoryImpl.findAllBy(cafeName, menu, address);

        // then
        assertThat(cafes).extracting(Cafe::getName).containsOnly(cafe2.getName(), cafe3.getName());
    }

    @Test
    @DisplayName("카페 이름,메뉴,주소로 카페를 조회한다")
    void findByAllFilters() {
        // given
        final String cafeName = "카페";
        final String menu = "도치 음료";
        final String address = "주소";

        menuRepository.save(Fixture.getMenu(cafe1, 1, "도치 음료"));
        menuRepository.save(Fixture.getMenu(cafe2, 1, "도치 음료"));

        // when
        final List<Cafe> cafes = cafeCustomRepositoryImpl.findAllBy(cafeName, menu, address);

        // then
        assertAll(
                () -> assertThat(cafes).extracting(Cafe::getName).containsOnly(cafe1.getName(), cafe2.getName()),
                () -> assertThat(cafes).hasSize(2)
        );
    }

    @Test
    @DisplayName("아무 조건을 걸지 않고 카페를 조회한다")
    void findByNoFilters() {
        // given
        final String cafeName = null;
        final String address = null;

        menuRepository.save(Fixture.getMenu(cafe2, 1, "음료2"));
        menuRepository.save(Fixture.getMenu(cafe3, 1, "음료3"));

        // when
        final List<Cafe> cafes = cafeCustomRepositoryImpl.findAllBy(cafeName, address);

        // then
        assertThat(cafes).extracting(Cafe::getName)
                .containsOnly(cafe1.getName(), cafe2.getName(), cafe3.getName(), cafe4.getName());
    }
}

