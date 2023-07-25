package com.project.yozmcafe.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.project.yozmcafe.controller.dto.MemberResponse;
import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.domain.cafe.CafeRepository;
import com.project.yozmcafe.domain.member.Member;
import com.project.yozmcafe.domain.member.MemberRepository;
import com.project.yozmcafe.fixture.Fixture;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private CafeRepository cafeRepository;

    @Test
    @DisplayName("아이디로 회원을 조회할 수 있다")
    void findById() {
        //given
        final Member member = memberRepository.save(new Member("12413", "연어", "image"));
        final MemberResponse expected = MemberResponse.from(member);

        //when
        final MemberResponse result = memberService.findById(member.getId());

        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("존재하지 않는 아이디로 회원을 조회하면 예외가 발생한다")
    void findById_fail() {
        assertThatThrownBy(() -> memberService.findById("nothing"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당하는 회원이 존재하지 않습니다.");
    }

    @Test
    @DisplayName("member의 likedCafes에 cafeId에 해당하는 카페가 존재하지 않고, isLiked가 true인 경우 likedCafes에 카페를 추가한다.")
    void updateLikeAdd() {
        //given
        final Member member = memberRepository.save(new Member("memberId", "폴로", "폴로사진"));
        final Cafe cafe = cafeRepository.save(Fixture.getCafe("카페", "카페주소", 10));

        //when
        memberService.updateLike(member, cafe.getId(), true);
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
        member.addLikedCafe(cafe);

        //when
        memberService.updateLike(member, cafe.getId(), false);
        final Member updatedMember = memberRepository.findById(member.getId()).get();

        //then
        assertAll(
                () -> assertThat(updatedMember.getLikedCafes()).isEmpty(),
                () -> assertThat(updatedMember.isLike(cafe)).isFalse(),
                () -> assertThat(cafe.getLikeCount()).isEqualTo(9)
        );
    }

    @Test
    @DisplayName("좋아요 상태를 변경하려는 카페가 존재하지 않는 경우 예외가 발생한다.")
    void updateLikeFail() {
        //given
        final Member member = memberRepository.save(new Member("memberId", "폴로", "폴로사진"));
        final Cafe cafe = Fixture.getCafe(1L, "카페", "카페주소", 10);

        //when & then
        assertThatThrownBy(() -> memberService.updateLike(member, cafe.getId(), true))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당하는 카페가 존재하지 않습니다.");
    }
}
