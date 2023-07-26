package com.project.yozmcafe.service;

import com.project.yozmcafe.controller.dto.LikedCafeResponse;
import com.project.yozmcafe.controller.dto.MemberResponse;
import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.domain.cafe.CafeRepository;
import com.project.yozmcafe.domain.member.Member;
import com.project.yozmcafe.domain.member.MemberRepository;
import com.project.yozmcafe.fixture.Fixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
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
    @DisplayName("멤버에 해당하는 좋아요 카페 목록을 조회한다.")
    void findLikedCafesById() {
        //given
        final Cafe savedCafe = cafeRepository.save(Fixture.getCafe("오션의 귀여운 카페", "인천 오션동", 5));
        final Member member = new Member("1234", "오션", "오션.img");
        final PageRequest pageRequest = PageRequest.of(0, 1);
        member.addLikedCafe(savedCafe);
        memberRepository.save(member);

        //when
        final List<LikedCafeResponse> likedCafes = memberService.findLikedCafesById(member.getId(), pageRequest);

        for (LikedCafeResponse likedCafe : likedCafes) {
            assertThat(likedCafe.cafeId()).isEqualTo(savedCafe.getId());
        }
    }

    @Test
    @DisplayName("좋아요 카페 목록을 조회할 멤버가 없을 경우 예외가 발생한다.")
    void findLikedCafesById_fail() {
        //given
        final PageRequest pageRequest = PageRequest.of(0, 1);

        //when
        //then
        assertThatThrownBy(() -> memberService.findLikedCafesById("findLikedCafesById_fail", pageRequest))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
