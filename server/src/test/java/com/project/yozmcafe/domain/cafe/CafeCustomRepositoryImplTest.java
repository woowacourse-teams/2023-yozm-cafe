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
        final String menu = "";
        final String address = "";

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
        final String cafeName = "";
        final String menu = "도치 음료";
        final String address = "";
        menuRepository.save(Fixture.getMenu(null, cafe1, 1, "도치 음료1", "imageUrl1", "description", "3000원", true));
        menuRepository.save(Fixture.getMenu(null, cafe1, 2, "도치 음료2", "imageUrl1", "description", "3000원", true));
        menuRepository.save(Fixture.getMenu(null, cafe1, 3, "아무 음료", "imageUrl2", "description", "3500원", true));
        menuRepository.save(Fixture.getMenu(null, cafe2, 2, "도치 음료", "imageUrl3", "description", "4000원", false));
        menuRepository.save(Fixture.getMenu(null, cafe3, 1, "도치", "imageUrl4", "description", "2000원", true));

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
        final String cafeName = "";
        final String menu = "";
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
        final String address = "";
        menuRepository.save(Fixture.getMenu(null, cafe1, 1, "음료1", "imageUrl1", "description", "3000원", true));
        menuRepository.save(Fixture.getMenu(null, cafe2, 1, "음료2", "imageUrl2", "description", "3000원", true));

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
    @DisplayName("카페 이름,메뉴,주소로 카페를 조회한다")
    void findByAllFilters() {
        // given
        final String cafeName = "카페";
        final String menu = "도치 음료";
        final String address = "주소";
        menuRepository.save(Fixture.getMenu(null, cafe1, 1, "도치 음료", "imageUrl1", "description", "3000원", true));
        menuRepository.save(Fixture.getMenu(null, cafe2, 1, "도치 음료", "imageUrl1", "description", "3000원", true));

        // when
        final List<Cafe> cafes = cafeCustomRepositoryImpl.findAllBy(cafeName, menu, address);

        // then
        assertAll(
                () -> assertThat(cafes).extracting(Cafe::getName).containsOnly(cafe1.getName(), cafe2.getName()),
                () -> assertThat(cafes).hasSize(2)
        );
    }
}

