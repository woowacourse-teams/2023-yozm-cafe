package com.project.yozmcafe.domain.member;

import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.domain.cafe.LikedCafe;
import com.project.yozmcafe.fixture.Fixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class MemberTest {

    @Test
    @DisplayName("회원에 UnViewedCafe를 추가한다.")
    void addUnViewedCafes() {
        //given
        Member member = new Member("4", "폴로", "폴로사진");
        final Cafe cafe1 = Fixture.getCafe("카페1", "주소1", 3);
        final Cafe cafe2 = Fixture.getCafe("카페2", "주소2", 4);
        final Cafe cafe3 = Fixture.getCafe("카페3", "주소3", 5);

        //when
        member.addUnViewedCafes(List.of(cafe1, cafe2, cafe3));

        //then
        assertThat(member.getUnViewedCafes()).hasSize(3);
    }

    @Test
    @DisplayName("회원의 unViewedCafe를 추가할 때 기존과 중복되면 추가되지 않는다.")
    void addUnViewedCafes2() {
        //given
        Member member = new Member("id", "연어", "image");
        final Cafe cafe1 = Fixture.getCafe("카페1", "주소1", 3);
        final Cafe cafe2 = Fixture.getCafe("카페2", "주소2", 4);
        final Cafe cafe3 = Fixture.getCafe("카페3", "주소3", 5);
        member.addUnViewedCafes(List.of(cafe1, cafe2, cafe3));

        //when
        member.addUnViewedCafes(List.of(cafe1, cafe2, cafe3));
        member.addUnViewedCafes(List.of(cafe1, cafe2, cafe3));
        member.addUnViewedCafes(List.of(cafe1, cafe2, cafe3));

        //then
        assertThat(member.getUnViewedCafes()).hasSize(3);
    }

    @Test
    @DisplayName("회원의 unViewedCafe 기록을 삭제한다.")
    void removeUnViewedCafe() {
        //given
        Member member = new Member("3", "폴로", "폴로사진");
        final Cafe cafe1 = Fixture.getCafe(1L, "카페1", "주소1", 3);
        final Cafe cafe2 = Fixture.getCafe(2L, "카페2", "주소2", 4);
        final Cafe cafe3 = Fixture.getCafe(3L, "카페3", "주소3", 5);
        member.addUnViewedCafes(List.of(cafe1, cafe2, cafe3));

        //when
        member.removeUnViewedCafe(cafe1.getId());

        //then
        assertThat(member.getUnViewedCafes()).hasSize(2);
    }

    @Test
    @DisplayName("회원의 unViewedCafe 기록 삭제 - 삭제할 카페가 UnViewedCafe에 존재하지 않아도 예외가 발생하지 않는다")
    void removeUnViewedCafe2() {
        //given
        Member member = new Member("id", "연어", "image");

        //when, then
        assertDoesNotThrow(() -> member.removeUnViewedCafe(1L));
    }

    @Test
    @DisplayName("멤버의 unViewedCafes가 비어있으면 true를 반환한다.")
    void isEmptyUnViewedCafe() {
        //given
        final Member member = new Member("4", "폴로", "폴로사진");

        //when
        final boolean result = member.isUnViewedCafesSizeUnder(1);

        //then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("멤버의 좋아요 리스트에 포함된 카페면 true를 아니면 false를 반환한다.")
    void isLike() {
        //given
        final Member member = new Member("4", "폴로", "폴로사진");
        final Cafe cafe1 = Fixture.getCafe(1L, "카페1", "주소1", 10);
        member.updateLikedCafesBy(cafe1, true);

        //when
        final boolean result1 = member.isLike(cafe1);

        //then
        assertThat(result1).isTrue();
    }

    @Test
    @DisplayName("멤버의 좋아요 리스트에 포함된 카페가 아니면 false를 반환한다.")
    void isLikeFalse() {
        //given
        final Member member = new Member("4", "폴로", "폴로사진");
        final Cafe cafe = Fixture.getCafe(2L, "카페2", "주소2", 9);

        //when
        final boolean result = member.isLike(cafe);

        //then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("멤버의 likedCafes에 존재하고, isLiked가 false인 경우 likedCafes에서 cafe를 삭제한다.")
    void updateLikedCafesBy() {
        //given
        final Member member = new Member("5", "폴로", "폴로사진");
        final Cafe cafe = Fixture.getCafe(1L, "카페", "카페주소", 10);
        member.updateLikedCafesBy(cafe, true);
        final boolean isLiked = false;

        //when
        member.updateLikedCafesBy(cafe, isLiked);

        //then
        assertAll(
                () -> assertThat(member.getLikedCafes()).hasSize(0),
                () -> assertThat(member.isLike(cafe)).isFalse(),
                () -> assertThat(cafe.getLikeCount()).isEqualTo(10));
    }

    @Test
    @DisplayName("멤버의 likedCafes에 존재하지 않고, isLiked가 true인 경우 likedCafes에 cafe를 추가한다.")
    void updateIsLikeAddCafe() {
        //given
        final Member member = new Member("5", "폴로", "폴로사진");
        final Cafe cafe = Fixture.getCafe(1L, "카페", "카페주소", 10);
        final boolean isLiked = true;

        //when
        member.updateLikedCafesBy(cafe, isLiked);

        //then
        assertAll(
                () -> assertThat(member.getLikedCafes()).hasSize(1),
                () -> assertThat(member.isLike(cafe)).isTrue(),
                () -> assertThat(cafe.getLikeCount()).isEqualTo(11)
        );
    }

    @ParameterizedTest
    @DisplayName("멤버의 likedCafes에 해당카페가 존재하고, isLiked가 true, 멤버의 likedCafes에 해당 카페가 존재하지 않고, isLiked가 false인 경우 아무런 작업을 하지 않는다.")
    @MethodSource("provideMemberAndIsLiked")
    void updateIsLikedDoNothing(Member member, Cafe cafe, boolean isLiked, boolean isLikeExpected) {
        //given
        final int likedCafeCount = member.getLikedCafes().size();

        //when
        member.updateLikedCafesBy(cafe, isLiked);

        //then
        assertAll(
                () -> assertThat(member.getLikedCafes()).hasSize(likedCafeCount),
                () -> assertThat(member.isLike(cafe)).isEqualTo(isLikeExpected),
                () -> assertThat(cafe.getLikeCount()).isEqualTo(11)
        );
    }

    @Test
    @DisplayName("멤버의 남은 UnViewedCafe 갯수가 4개 미만이면 True")
    void isUnViewedCafesSizeUnder_true() {
        //given
        final Member member = new Member("1", "연어", "image");
        final Cafe cafe = Fixture.getCafe(1L, "카페", "카페주소", 10);
        final Cafe cafe2 = Fixture.getCafe(2L, "카페", "카페주소", 10);
        final Cafe cafe3 = Fixture.getCafe(3L, "카페", "카페주소", 10);
        final Cafe cafe4 = Fixture.getCafe(4L, "카페", "카페주소", 10);

        member.addUnViewedCafes(List.of(cafe, cafe2, cafe3, cafe4));

        //when, then
        assertThat(member.isUnViewedCafesSizeUnder(5)).isTrue();
    }

    @Test
    @DisplayName("멤버의 남은 UnViewedCafe 갯수가 4개 이상이면 False")
    void isUnViewedCafesSizeUnder_false() {
        //given
        final Member member = new Member("1", "연어", "image");
        final Cafe cafe = Fixture.getCafe(1L, "카페", "카페주소", 10);
        final Cafe cafe2 = Fixture.getCafe(2L, "카페", "카페주소", 10);
        final Cafe cafe3 = Fixture.getCafe(3L, "카페", "카페주소", 10);
        final Cafe cafe4 = Fixture.getCafe(4L, "카페", "카페주소", 10);

        member.addUnViewedCafes(List.of(cafe, cafe2, cafe3, cafe4));

        //when, then
        assertThat(member.isUnViewedCafesSizeUnder(4)).isFalse();
    }

    public static Stream<Arguments> provideMemberAndIsLiked() {
        final Member member1 = new Member("6", "오션", "오션사진");
        final Member member2 = new Member("7", "연어", "연어사진");
        final Cafe cafe = Fixture.getCafe(1L, "카페", "카페주소", 10);
        member1.updateLikedCafesBy(cafe, true);
        return Stream.of(
                Arguments.of(member1, cafe, true, true),
                Arguments.of(member2, cafe, false, false)
        );
    }

    @Test
    @DisplayName("좋아요한 카페 목록을 페이지 처리한다")
    void getLikedCafesByPaging() {
        //given
        final Member member = new Member("1234", "오션", "오션사진");
        final Cafe cafe1 = Fixture.getCafe(1L, "카페1", "주소1", 3);
        final Cafe cafe2 = Fixture.getCafe(2L, "카페2", "주소2", 3);
        final Cafe cafe3 = Fixture.getCafe(3L, "카페3", "주소2", 3);
        final Cafe cafe4 = Fixture.getCafe(4L, "카페4", "주소2", 3);
        member.updateLikedCafesBy(cafe1, true);
        member.updateLikedCafesBy(cafe2, true);
        member.updateLikedCafesBy(cafe3, true);
        member.updateLikedCafesBy(cafe4, true);

        //when
        final List<LikedCafe> likedCafes = member.getLikedCafesByPaging(1, 2);

        //then
        assertThat(likedCafes).map(LikedCafe::getCafe).containsExactly(cafe4, cafe3);
    }
}
