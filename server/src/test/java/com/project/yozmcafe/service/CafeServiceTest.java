package com.project.yozmcafe.service;

import com.project.yozmcafe.controller.dto.cafe.CafeRankResponse;
import com.project.yozmcafe.controller.dto.cafe.CafeResponse;
import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.domain.cafe.CafeRepository;
import com.project.yozmcafe.domain.member.Member;
import com.project.yozmcafe.domain.member.MemberRepository;
import com.project.yozmcafe.exception.BadRequestException;
import com.project.yozmcafe.fixture.Fixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Arrays;
import java.util.List;

import static com.project.yozmcafe.exception.ErrorCode.RANK_OUT_OF_BOUNDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;

import static com.project.yozmcafe.exception.ErrorCode.NOT_EXISTED_CAFE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@Sql(scripts = "classpath:truncate.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@AutoConfigureTestDatabase(replace = Replace.NONE)
class CafeServiceTest {

    @Autowired
    private CafeService cafeService;
    @Autowired
    private CafeRepository cafeRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("로그인 된 사용자의 안본 카페 목록을 조회한다.")
    void getCafesForLoginMember() {
        //given
        final Member member = memberRepository.save(new Member("1", "폴로", "img"));
        final Cafe cafe1 = cafeRepository.save(Fixture.getCafe("카페1", "주소1", 10));
        final Cafe cafe2 = cafeRepository.save(Fixture.getCafe("카페2", "주소2", 11));
        member.addUnViewedCafes(Arrays.asList(cafe1, cafe2));
        member.updateLikedCafesBy(cafe1, true);

        //when
        final List<CafeResponse> result = cafeService.getCafesForLoginMember(member, 5);

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
        final PageRequest pageRequest = PageRequest.of(0, 5);
        cafeRepository.save(Fixture.getCafe("카페1", "주소1", 11));
        cafeRepository.save(Fixture.getCafe("카페2", "주소2", 12));
        cafeRepository.save(Fixture.getCafe("카페3", "주소3", 13));
        final Cafe savedCafe4 = cafeRepository.save(Fixture.getCafe("카페4", "주소4", 14));
        final Cafe savedCafe5 = cafeRepository.save(Fixture.getCafe("카페5", "주소5", 15));
        final Cafe savedCafe6 = cafeRepository.save(Fixture.getCafe("카페6", "주소6", 16));
        final Cafe savedCafe7 = cafeRepository.save(Fixture.getCafe("카페7", "주소7", 17));
        final Cafe savedCafe8 = cafeRepository.save(Fixture.getCafe("카페8", "주소8", 18));

        //when
        final List<CafeRankResponse> result = cafeService.getCafesOrderByLikeCount(pageRequest);

        //then
        assertThat(result).extracting("id", "rank", "likeCount")
                .containsExactly(tuple(savedCafe8.getId(), 1, savedCafe8.getLikeCount()),
                        tuple(savedCafe7.getId(), 2, savedCafe7.getLikeCount()),
                        tuple(savedCafe6.getId(), 3, savedCafe6.getLikeCount()),
                        tuple(savedCafe5.getId(), 4, savedCafe5.getLikeCount()),
                        tuple(savedCafe4.getId(), 5, savedCafe4.getLikeCount())
                );
    }

    @Test
    @DisplayName("cafe를 likeCount가 많은 순으로 조회할 때, 페이지에 맞는 카페들을 응답한다.")
    void getCafesOrderByLikeCountWhenNotFirstPage() {
        //given
        final PageRequest pageRequest = PageRequest.of(1, 5);
        final Cafe savedCafe1 = cafeRepository.save(Fixture.getCafe("카페1", "주소1", 11));
        final Cafe savedCafe2 = cafeRepository.save(Fixture.getCafe("카페2", "주소2", 12));
        final Cafe savedCafe3 = cafeRepository.save(Fixture.getCafe("카페3", "주소3", 13));
        cafeRepository.save(Fixture.getCafe("카페4", "주소4", 14));
        cafeRepository.save(Fixture.getCafe("카페5", "주소5", 15));
        cafeRepository.save(Fixture.getCafe("카페6", "주소6", 16));
        cafeRepository.save(Fixture.getCafe("카페7", "주소7", 17));
        cafeRepository.save(Fixture.getCafe("카페8", "주소8", 18));

        //when
        final List<CafeRankResponse> result = cafeService.getCafesOrderByLikeCount(pageRequest);

        //then
        assertThat(result).extracting("id", "rank", "likeCount")
                .containsExactly(tuple(savedCafe3.getId(), 6, savedCafe3.getLikeCount()),
                        tuple(savedCafe2.getId(), 7, savedCafe2.getLikeCount()),
                        tuple(savedCafe1.getId(), 8, savedCafe1.getLikeCount())
                );
    }

    @Test
    @DisplayName("cafe를 likeCount가 많은 순으로 조회할 때, 해당 순위 페이지에 카페가 존재하지 않으면 빈 배열을 반환한다.")
    void getCafesOrderByLikeCountWhenNotExist() {
        //given
        final PageRequest pageRequest = PageRequest.of(3, 5);
        cafeRepository.save(Fixture.getCafe("카페1", "주소1", 11));
        cafeRepository.save(Fixture.getCafe("카페2", "주소2", 12));
        cafeRepository.save(Fixture.getCafe("카페3", "주소3", 13));
        cafeRepository.save(Fixture.getCafe("카페4", "주소4", 14));
        cafeRepository.save(Fixture.getCafe("카페5", "주소5", 15));
        cafeRepository.save(Fixture.getCafe("카페6", "주소6", 16));

        //when
        final List<CafeRankResponse> result = cafeService.getCafesOrderByLikeCount(pageRequest);

        //then
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("cafe를 likeCount가 많은 순으로 조회할 때, 최대 순위 페이지를 초과하는 요청이면 예외가 발생한다.")
    void getCafesOrderByLikeCountWhenPageOutFail() {
        //given
        final PageRequest pageRequest = PageRequest.of(4, 10);
        cafeRepository.save(Fixture.getCafe("카페1", "주소1", 11));
        cafeRepository.save(Fixture.getCafe("카페2", "주소2", 12));
        cafeRepository.save(Fixture.getCafe("카페3", "주소3", 13));

        //when,then
        assertThatThrownBy(() -> cafeService.getCafesOrderByLikeCount(pageRequest))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(RANK_OUT_OF_BOUNDS.getMessage());
    }

    @DisplayName("id로 카페를 단 건 조회한다.")
    void getCafeById() {
        //given
        final Cafe savedCafe1 = cafeRepository.save(Fixture.getCafe("카페1", "주소1", 10));
        final Cafe savedCafe2 = cafeRepository.save(Fixture.getCafe("카페2", "주소2", 11));

        //when
        final CafeResponse result = cafeService.getCafeById(savedCafe1.getId());

        //then
        assertThat(result.id()).isEqualTo(savedCafe1.getId());
    }

    @Test
    @DisplayName("id로 카페를 단 건 조회할 때, 존재하지 않는 카페이면 예외가 발생한다.")
    void getCafeByIdFailWhenNotExist() {
        //given
        cafeRepository.save(Fixture.getCafe("카페1", "주소1", 10));
        cafeRepository.save(Fixture.getCafe("카페2", "주소2", 11));
        final long notExistCafeId = 9999L;

        //when, then
        assertThatThrownBy(() -> cafeService.getCafeById(notExistCafeId))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(NOT_EXISTED_CAFE.getMessage());
    }
}
