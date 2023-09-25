package com.project.yozmcafe.domain.cafe;

import com.project.yozmcafe.BaseTest;
import com.project.yozmcafe.fixture.Fixture;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

class CafeRepositoryTest extends BaseTest {

    @Autowired
    private CafeRepository cafeRepository;

    private Cafe cafe1;
    private Cafe cafe2;
    private Cafe cafe3;
    private Cafe cafe4;
    private Cafe cafe5;

    @BeforeEach
    void setUp() {
        cafe1 = cafeRepository.save(Fixture.getCafe("카페1", "주소1", 3));
        cafe2 = cafeRepository.save(Fixture.getCafe("카페2", "주소2", 4));
        cafe3 = cafeRepository.save(Fixture.getCafe("카페3", "주소3", 5));
        cafe4 = cafeRepository.save(Fixture.getCafe("카페4", "주소4", 6));
        cafe5 = cafeRepository.save(Fixture.getCafe("카페5", "주소5", 7));
    }

    @AfterEach
    void after() {
        cafeRepository.deleteAll();
    }

    @Test
    @DisplayName("비회원일 경우 요청한 페이지에 따른 카페정보 5개를 반환한다.")
    void findSliceBy() {
        //given
        PageRequest pageRequest = PageRequest.of(0, 5);

        //when
        final List<Cafe> cafes = cafeRepository.findSliceBy(pageRequest).getContent();

        //then
        assertSoftly(softAssertions -> {
            assertThat(cafes).hasSize(5);
            assertThat(cafes).containsExactlyInAnyOrder(cafe1, cafe2, cafe3, cafe4, cafe5);
        });
    }
}
