package com.project.yozmcafe.service;

import com.project.yozmcafe.BaseTest;
import com.project.yozmcafe.controller.dto.cafe.CafeThumbnailResponse;
import com.project.yozmcafe.controller.dto.cafe.LikedCafeResponse;
import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.domain.cafe.CafeRepository;
import com.project.yozmcafe.domain.member.Member;
import com.project.yozmcafe.domain.member.MemberRepository;
import com.project.yozmcafe.exception.BadRequestException;
import com.project.yozmcafe.fixture.Fixture;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static com.project.yozmcafe.exception.ErrorCode.NOT_EXISTED_MEMBER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class LikedCafeServiceTest extends BaseTest {

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
        final PageRequest pageRequest = PageRequest.of(0, 15);
        member.updateLikedCafesBy(savedCafe, true);
        memberRepository.save(member);

        //when
        final List<CafeThumbnailResponse> likedCafes = likedCafeService.findLikedCafeThumbnailsByMemberId(member.getId(), pageRequest);

        //then
        assertThat(likedCafes.get(0).cafeId()).isEqualTo(savedCafe.getId());
    }

    @Test
    @DisplayName("좋아요 카페 목록을 조회할 멤버가 없을 경우 예외가 발생한다.")
    void findLikedCafesById_fail() {
        //given
        final PageRequest pageRequest = PageRequest.of(0, 15);

        //when
        //then
        assertThatThrownBy(() -> likedCafeService.findLikedCafeThumbnailsByMemberId("findLikedCafesById_fail", pageRequest))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(NOT_EXISTED_MEMBER.getMessage());
    }

    @Test
    @DisplayName("좋아요 목록 수를 초과한 page 요청 시 빈 list를 반환한다.")
    void findLikedCafesById_empty() {
        //given
        final Cafe cafe1 = Fixture.getCafe(1L, "카페1", "주소1", 3);
        final Cafe cafe2 = Fixture.getCafe(2L, "카페2", "주소2", 3);
        cafeRepository.save(cafe1);
        cafeRepository.save(cafe2);
        Member member = new Member("1234", "오션", "오션사진");
        member.updateLikedCafesBy(cafe1, true);
        member.updateLikedCafesBy(cafe2, true);
        memberRepository.save(member);
        final PageRequest pageRequest = PageRequest.of(1, 2);

        //when
        final List<CafeThumbnailResponse> likedCafesById = likedCafeService.findLikedCafeThumbnailsByMemberId(member.getId(), pageRequest);

        //then
        assertThat(likedCafesById).isEmpty();
    }

    @Test
    @DisplayName("좋아요 카페 목록의 카페 정보들을 조회한다.")
    void findLikedCafesDetails() {
        //given
        final Cafe savedCafe1 = cafeRepository.save(Fixture.getCafe("카페1", "주소1", 10));
        final Cafe savedCafe2 = cafeRepository.save(Fixture.getCafe("카페2", "주소2", 11));
        cafeRepository.save(Fixture.getCafe("카페3", "주소3", 12));

        final Member member = new Member("1234", "도치", "도치.img");
        member.updateLikedCafesBy(savedCafe1, true);
        member.updateLikedCafesBy(savedCafe2, true);
        final Member savedMember = memberRepository.save(member);

        //when
        final List<LikedCafeResponse> result = likedCafeService.findLikedCafesByMemberId(savedMember.getId());

        //then
        assertAll(
                () -> assertThat(result).extracting("id").containsExactly(savedCafe2.getId(), savedCafe1.getId()),
                () -> assertThat(result).extracting("isLiked").doesNotContain(false)
        );
    }

    @Test
    @DisplayName("좋아요 카페 목록의 카페 정보들을 조회할 때, 좋아요 된 카페가 없으면 빈 배열을 반환한다")
    void findLikedCafesDetailsWhenLikeCafeNotExist() {
        //given
        cafeRepository.save(Fixture.getCafe("카페1", "주소1", 10));
        cafeRepository.save(Fixture.getCafe("카페2", "주소2", 11));
        final Member member = new Member("1234", "도치", "도치.img");
        final Member savedMember = memberRepository.save(member);

        //when
        final List<LikedCafeResponse> result = likedCafeService.findLikedCafesByMemberId(savedMember.getId());

        //then
        assertThat(result).isEmpty();
    }

    @Test
    @Transactional
    @DisplayName("member의 likedCafes에 cafeId에 해당하는 카페가 존재하지 않고, isLiked가 true인 경우 likedCafes에 카페를 추가한다.")
    void updateLikeAdd() {
        //given
        final Member member = memberRepository.save(new Member("memberId", "폴로", "폴로사진"));
        final Cafe cafe = cafeRepository.save(Fixture.getCafe("카페", "카페주소", 10));

        //when
        likedCafeService.updateLike(member.getId(), cafe.getId(), true);
        final Member updatedMember = memberRepository.findById(member.getId()).get();

        //then
        assertAll(
                () -> assertThat(updatedMember.getLikedCafes()).hasSize(1),
                () -> assertThat(updatedMember.isLike(cafe)).isTrue(),
                () -> assertThat(cafe.getLikeCount()).isEqualTo(11)
        );
    }

    @Test
    @Transactional
    @DisplayName("member의 likedCafes에 cafeId에 해당하는 카페가 존재하고, isLiked가 false 경우 likedCafes에서 카페를 삭제한다.")
    void updateLikeRemove() {
        //given
        final Member member = memberRepository.save(new Member("memberId", "폴로", "폴로사진"));
        final Cafe cafe = cafeRepository.save(Fixture.getCafe("카페", "카페주소", 10));
        member.updateLikedCafesBy(cafe, true);

        //when
        likedCafeService.updateLike(member.getId(), cafe.getId(), false);
        final Member updatedMember = memberRepository.findById(member.getId()).get();

        //then
        assertAll(
                () -> assertThat(updatedMember.getLikedCafes()).isEmpty(),
                () -> assertThat(updatedMember.isLike(cafe)).isFalse(),
                () -> assertThat(cafe.getLikeCount()).isEqualTo(10)
        );
    }

    @Test
    @Transactional
    @DisplayName("member의 likedCafes에 cafeId에 해당하는 카페가 존재하고, isLiked가 true인 경우 기존의 상태를 유지한다.")
    void updateLikeAlreadySatisfied() {
        //given
        final Member member = memberRepository.save(new Member("memberId", "폴로", "폴로사진"));
        final Cafe cafe = cafeRepository.save(Fixture.getCafe("카페", "카페주소", 10));
        member.updateLikedCafesBy(cafe, true);

        //when
        likedCafeService.updateLike(member.getId(), cafe.getId(), true);
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
        final long cafeId = cafe.getId();
        assertThatThrownBy(() -> likedCafeService.updateLike(member.getId(), cafeId, true))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("카페가 존재하지 않습니다.");
    }
}
