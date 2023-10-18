package com.project.yozmcafe.service;

import static com.project.yozmcafe.exception.ErrorCode.NOT_EXISTED_CAFE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import com.project.yozmcafe.controller.dto.cafe.CafeRankResponse;
import com.project.yozmcafe.controller.dto.cafe.CafeResponse;
import com.project.yozmcafe.controller.dto.cafe.CafeSearchRequest;
import com.project.yozmcafe.controller.dto.cafe.CafeSearchResponse;
import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.domain.cafe.CafeRepository;
import com.project.yozmcafe.domain.member.Member;
import com.project.yozmcafe.domain.member.MemberRepository;
import com.project.yozmcafe.domain.menu.MenuRepository;
import com.project.yozmcafe.exception.BadRequestException;
import com.project.yozmcafe.fixture.Fixture;

class CafeServiceTest extends BaseServiceTest {

    @Autowired
    private CafeService cafeService;
    @Autowired
    private CafeRepository cafeRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MenuRepository menuRepository;

    @Test
    @DisplayName("로그인 된 사용자의 안본 카페 목록을 조회한다.")
    void getCafesForLoginMember() {
        //given
        final Member member = new Member("1", "폴로", "img");
        final Cafe cafe1 = cafeRepository.save(Fixture.getCafe("카페1", "주소1", 10));
        final Cafe cafe2 = cafeRepository.save(Fixture.getCafe("카페2", "주소2", 11));
        member.addUnViewedCafes(Arrays.asList(cafe1, cafe2));
        member.updateLikedCafesBy(cafe1, true);
        memberRepository.save(member);

        //when
        final List<CafeResponse> result = cafeService.getCafesForLoginMember(member.getId(), 5);

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

    @Test
    @DisplayName("cafe를 likeCount가 많은 순으로 조회한다.")
    void getCafesOrderByLikeCount() {
        //given
        final PageRequest pageRequest = PageRequest.of(0, 3);
        final Cafe savedCafe1 = cafeRepository.save(Fixture.getCafe("카페1", "주소1", 11));
        final Cafe savedCafe2 = cafeRepository.save(Fixture.getCafe("카페2", "주소2", 12));
        final Cafe savedCafe3 = cafeRepository.save(Fixture.getCafe("카페3", "주소3", 13));

        //when
        final List<CafeRankResponse> result = cafeService.getCafesOrderByLikeCount(pageRequest);

        //then
        assertThat(result).extracting("id", "rank", "likeCount")
                .containsExactly(
                        tuple(savedCafe3.getId(), 1, savedCafe3.getLikeCount()),
                        tuple(savedCafe2.getId(), 2, savedCafe2.getLikeCount()),
                        tuple(savedCafe1.getId(), 3, savedCafe1.getLikeCount())
                );
    }

    @Test
    @DisplayName("cafe를 likeCount가 많은 순으로 조회할 때, 해당 순위 페이지에 카페가 존재하지 않으면 빈 배열을 반환한다.")
    void getCafesOrderByLikeCountWhenNotExist() {
        //given
        final PageRequest pageRequest = PageRequest.of(1, 3);
        cafeRepository.save(Fixture.getCafe("카페1", "주소1", 11));
        cafeRepository.save(Fixture.getCafe("카페2", "주소2", 12));
        cafeRepository.save(Fixture.getCafe("카페3", "주소3", 13));

        //when
        final List<CafeRankResponse> result = cafeService.getCafesOrderByLikeCount(pageRequest);

        //then
        assertThat(result).isEmpty();
    }

    @DisplayName("id로 카페를 단 건 조회한다.")
    void getCafeById() {
        //given
        final Cafe savedCafe = cafeRepository.save(Fixture.getCafe("카페1", "주소1", 10));

        //when
        final CafeResponse result = cafeService.getCafeByIdOrThrow(savedCafe.getId());

        //then
        assertThat(result.id()).isEqualTo(savedCafe.getId());
    }

    @Test
    @DisplayName("id로 카페를 단 건 조회할 때, 존재하지 않는 카페이면 예외가 발생한다.")
    void getCafeByIdFailWhenNotExist() {
        //given
        final long notExistCafeId = 9999L;

        //when, then
        assertThatThrownBy(() -> cafeService.getCafeByIdOrThrow(notExistCafeId))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(NOT_EXISTED_CAFE.getMessage());
    }

    @Test
    @DisplayName("메뉴가 검색 조건에 있을 때 카페를 검색해 조회한다.")
    void getCafesByKeyWordWhenMenuExist() {
        final Cafe savedCafe1 = cafeRepository.save(Fixture.getCafe("카페1", "주소1", 10));
        menuRepository.save(Fixture.getMenu(savedCafe1, 1, "카페 메뉴"));

        final CafeSearchRequest cafeSearchRequest = new CafeSearchRequest("카페", "카페 메뉴", "주소");
        List<CafeSearchResponse> cafeResponse = cafeService.getCafesBySearch(cafeSearchRequest);
        assertThat(cafeResponse).extracting("id", "name")
                .containsOnly(tuple(savedCafe1.getId(), savedCafe1.getName()));
    }

    @Test
    @DisplayName("메뉴가 검색 조건에 없을 때 카페를 검색해 조회한다.")
    void getCafesByKeyWordWhenMenuNotExist() {
        final Cafe savedCafe1 = cafeRepository.save(Fixture.getCafe("카페1", "주소1", 10));
        final Cafe savedCafe2 = cafeRepository.save(Fixture.getCafe("카페2", "주소2", 11));
        final Cafe savedCafe3 = cafeRepository.save(Fixture.getCafe("카페3", "주소3", 11));
        menuRepository.save(Fixture.getMenu(savedCafe1, 1, "카페 메뉴1"));
        menuRepository.save(Fixture.getMenu(savedCafe2, 1, "카페 메뉴2"));
        menuRepository.save(Fixture.getMenu(savedCafe3, 1, "카페 메뉴3"));

        final CafeSearchRequest cafeSearchRequest = new CafeSearchRequest("카페1", null, "주소");
        List<CafeSearchResponse> cafeResponse = cafeService.getCafesBySearch(cafeSearchRequest);
        assertThat(cafeResponse).extracting("id", "name")
                .containsOnly(tuple(savedCafe1.getId(), savedCafe1.getName()));
    }
}
