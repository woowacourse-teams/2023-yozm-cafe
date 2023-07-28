package com.project.yozmcafe.service;

import com.project.yozmcafe.controller.dto.cafe.CafeResponse;
import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.domain.cafe.CafeRepository;
import com.project.yozmcafe.domain.member.Member;
import com.project.yozmcafe.domain.member.MemberRepository;
import com.project.yozmcafe.fixture.Fixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class CafeServiceTest {

    private CafeService cafeService;
    @Autowired
    private CafeRepository cafeRepository;
    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        cafeService = new CafeService(cafeRepository);
    }

    @Test
    @DisplayName("로그인 된 사용자의 안본 카페 목록을 조회한다.")
    void getCafesForLoginMember() {
        //given
        final PageRequest pageRequest = PageRequest.of(0, 5);
        final Member member = memberRepository.save(new Member("1", "폴로", "img"));
        final Cafe cafe1 = cafeRepository.save(Fixture.getCafe("카페1", "주소1", 10));
        final Cafe cafe2 = cafeRepository.save(Fixture.getCafe("카페2", "주소2", 11));
        member.addUnViewedCafes(List.of(cafe1, cafe2));
        member.addLikedCafe(cafe1);

        //when
        final List<CafeResponse> result = cafeService.getCafesForLoginMember(pageRequest, member);

        //then
        assertAll(
                () -> assertThat(result).hasSize(2),
                () -> assertThat(result.get(0).isLiked()).isTrue(),
                () -> assertThat(result.get(1).isLiked()).isFalse()
        );
    }

    @Test
    @DisplayName("로그인 되지 않은 사용자의 안본 카페 목록을 조회한다.")
    void getCafesForUnLoginMember() {
        //given
        final PageRequest pageRequest = PageRequest.of(0, 5);
        cafeRepository.save(Fixture.getCafe("카페1", "주소1", 10));
        cafeRepository.save(Fixture.getCafe("카페2", "주소2", 11));

        //when
        final List<CafeResponse> result = cafeService.getCafesForUnLoginMember(pageRequest);

        //then
        assertAll(
                () -> assertThat(result).hasSize(2),
                () -> assertThat(result.get(0).isLiked()).isFalse(),
                () -> assertThat(result.get(1).isLiked()).isFalse()
        );
    }
}
