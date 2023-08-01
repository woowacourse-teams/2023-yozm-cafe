package com.project.yozmcafe.service;

import com.project.yozmcafe.controller.dto.LikedCafeResponse;
import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.domain.cafe.CafeRepository;
import com.project.yozmcafe.domain.member.Member;
import com.project.yozmcafe.domain.member.MemberRepository;
import com.project.yozmcafe.exception.BadRequestException;
import com.project.yozmcafe.fixture.Fixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.project.yozmcafe.exception.ErrorCode.NOT_EXISTED_MEMBER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@Transactional
class LikedCafeServiceTest {

    @Autowired
    private LikedCafeService likedCafeService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private CafeRepository cafeRepository;

    @Test
    @DisplayName("멤버가 좋아요한 카페 목록을 조회한다.")
    void findLikedCafesById() {
        //given
        final Cafe savedCafe = cafeRepository.save(Fixture.getCafe("오션의 귀여운 카페", "인천 오션동", 5));
        final Member member = new Member("1234", "오션", "오션.img");
        member.updateLikedCafesBy(savedCafe, true);
        memberRepository.save(member);

        //when
        final List<LikedCafeResponse> likedCafes = likedCafeService.findLikedCafesById(member.getId(), 1, 15);

        //then
        assertThat(likedCafes.get(0).cafeId()).isEqualTo(savedCafe.getId());
    }

    @Test
    @DisplayName("좋아요 카페 목록을 조회할 멤버가 없을 경우 예외가 발생한다.")
    void findLikedCafesById_fail() {
        //given
        //when
        //then
        assertThatThrownBy(() -> likedCafeService.findLikedCafesById("findLikedCafesById_fail", 1, 15))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(NOT_EXISTED_MEMBER.getMessage());
    }

    @Test
    @DisplayName("member의 likedCafes에 cafeId에 해당하는 카페가 존재하지 않고, isLiked가 true인 경우 likedCafes에 카페를 추가한다.")
    void updateLikeAdd() {
        //given
        final Member member = memberRepository.save(new Member("memberId", "폴로", "폴로사진"));
        final Cafe cafe = cafeRepository.save(Fixture.getCafe("카페", "카페주소", 10));

        //when
        likedCafeService.updateLike(member, cafe.getId(), true);
        final Member updatedMember = memberRepository.findById(member.getId()).get();

        //then
        assertAll(
                () -> assertThat(updatedMember.getLikedCafes()).hasSize(1),
                () -> assertThat(updatedMember.isLike(cafe)).isTrue(),
                () -> assertThat(cafe.getLikeCount()).isEqualTo(11)
        );
    }

    @Test
    @DisplayName("member의 likedCafes에 cafeId에 해당하는 카페가 존재하고, isLiked가 false 경우 likedCafes에서 카페를 삭제한다.")
    void updateLikeRemove() {
        //given
        final Member member = memberRepository.save(new Member("memberId", "폴로", "폴로사진"));
        final Cafe cafe = cafeRepository.save(Fixture.getCafe("카페", "카페주소", 10));
        member.updateLikedCafesBy(cafe, true);

        //when
        likedCafeService.updateLike(member, cafe.getId(), false);
        final Member updatedMember = memberRepository.findById(member.getId()).get();

        //then
        assertAll(
                () -> assertThat(updatedMember.getLikedCafes()).isEmpty(),
                () -> assertThat(updatedMember.isLike(cafe)).isFalse(),
                () -> assertThat(cafe.getLikeCount()).isEqualTo(10)
        );
    }

    @Test
    @DisplayName("member의 likedCafes에 cafeId에 해당하는 카페가 존재하고, isLiked가 true인 경우 기존의 상태를 유지한다.")
    void updateLikeAlreadySatisfied() {
        //given
        final Member member = memberRepository.save(new Member("memberId", "폴로", "폴로사진"));
        final Cafe cafe = cafeRepository.save(Fixture.getCafe("카페", "카페주소", 10));
        member.updateLikedCafesBy(cafe, true);

        //when
        likedCafeService.updateLike(member, cafe.getId(), true);
        final Member updatedMember = memberRepository.findById(member.getId()).get();

        //then
        assertAll(
                () -> assertThat(updatedMember.getLikedCafes()).hasSize(1),
                () -> assertThat(updatedMember.isLike(cafe)).isTrue(),
                () -> assertThat(cafe.getLikeCount()).isEqualTo(11)
        );
    }

    @Test
    @DisplayName("좋아요 상태를 변경하려는 카페가 존재하지 않는 경우 예외가 발생한다.")
    void updateLikeFail() {
        //given
        final Member member = memberRepository.save(new Member("memberId", "폴로", "폴로사진"));
        final Cafe cafe = Fixture.getCafe(1L, "카페", "카페주소", 10);

        //when & then
        assertThatThrownBy(() -> likedCafeService.updateLike(member, cafe.getId(), true))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당하는 카페가 존재하지 않습니다.");
    }
}
