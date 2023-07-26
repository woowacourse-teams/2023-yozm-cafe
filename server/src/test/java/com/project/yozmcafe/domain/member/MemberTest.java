package com.project.yozmcafe.domain.member;

import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.domain.cafe.UnViewedCafe;
import com.project.yozmcafe.fixture.Fixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
    @DisplayName("회원의 unViewedCafe 기록을 삭제한다.")
    void removeUnViewedCafe() {
        //given
        Member member = new Member("3", "폴로", "폴로사진");
        final Cafe cafe1 = Fixture.getCafe("카페1", "주소1", 3);
        final Cafe cafe2 = Fixture.getCafe("카페2", "주소2", 4);
        final Cafe cafe3 = Fixture.getCafe("카페3", "주소3", 5);
        member.addUnViewedCafes(List.of(cafe1, cafe2, cafe3));
        final UnViewedCafe unViewedCafe = member.getUnViewedCafes().get(0);

        //when
        member.removeUnViewedCafe(unViewedCafe);

        //then
        assertThat(member.getUnViewedCafes()).hasSize(2);
    }

    @Test
    @DisplayName("회원의 unViewedCafe 기록을 삭제한다.")
    void removeUnViewedCafeFail() {
        //given
        Member member = new Member("3", "폴로", "폴로사진");
        Member member2 = new Member("4", "폴로2", "폴로사진2");
        final Cafe cafe1 = Fixture.getCafe("카페1", "주소1", 3);
        final Cafe cafe2 = Fixture.getCafe("카페2", "주소2", 4);
        final Cafe cafe3 = Fixture.getCafe("카페3", "주소3", 5);
        final Cafe cafe4 = Fixture.getCafe("카페4", "주소4", 6);
        final UnViewedCafe inValidUnViewedCafe = new UnViewedCafe(cafe4, member2);

        //when & then
        assertThatThrownBy(() -> member.removeUnViewedCafe(inValidUnViewedCafe))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 내역입니다.");
    }

    @Test
    @DisplayName("멤버의 unViewedCafes가 비어있으면 true를 반환한다.")
    void isEmptyUnViewedCafe() {
        //given
        final Member member = new Member("4", "폴로", "폴로사진");

        //when
        final boolean result = member.isEmptyUnViewedCafe();

        //then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("멤버의 좋아요 리스트에 포함된 카페면 true를 아니면 false를 반환한다.")
    void isLike() {
        //given
        final Member member = new Member("4", "폴로", "폴로사진");
        final Cafe cafe1 = Fixture.getCafe(1L, "카페1", "주소1", 10);
        member.addLikedCafe(cafe1);

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
}
