package com.project.yozmcafe.service;

import com.project.yozmcafe.controller.dto.MemberResponse;
import com.project.yozmcafe.domain.member.Member;
import com.project.yozmcafe.domain.member.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;

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
}
