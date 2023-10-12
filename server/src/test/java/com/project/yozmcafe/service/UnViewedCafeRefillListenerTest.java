package com.project.yozmcafe.service;

import com.project.yozmcafe.controller.auth.OAuthProvider;
import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.domain.cafe.CafeRepository;
import com.project.yozmcafe.domain.cafe.UnViewedCafe;
import com.project.yozmcafe.domain.member.Member;
import com.project.yozmcafe.domain.member.MemberInfo;
import com.project.yozmcafe.domain.member.MemberRepository;
import com.project.yozmcafe.fixture.Fixture;
import com.project.yozmcafe.service.auth.AuthService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

@RecordApplicationEvents
class UnViewedCafeRefillListenerTest extends BaseServiceTest {

    @Autowired
    AuthService authService;

    @Autowired
    CafeService cafeService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CafeRepository cafeRepository;

    @Autowired
    ApplicationEvents events;

    @Test
    @DisplayName("새로운 유저 가입 시 cafeRefillEvent를 발행해 회원의 unviewedCafe가 채운다.")
    void whenCreateAccessTokenListenEvent() {
        //given
        final String memberId = "12345";
        doReturn(new MemberInfo(memberId, "name", "image"))
                .when(googleOAuthClient).getUserInfo(anyString());
        cafeRepository.save(Fixture.getCafe("cafe1", "address1", 0));
        cafeRepository.save(Fixture.getCafe("cafe2", "address2", 0));

        //when
        authService.createAccessToken("code", OAuthProvider.GOOGLE);

        //then
        final Member refilledMember = memberRepository.findWithUnViewedCafesById(memberId).get();
        final List<Cafe> unViewedCafes = refilledMember.getUnViewedCafes().stream()
                .map(UnViewedCafe::getCafe)
                .toList();

        assertThat(events.stream(CafeRefillEvent.class).count()).isEqualTo(1);
        assertThat(unViewedCafes).extracting("name", "address")
                .containsOnly(tuple("cafe1", "address1"), tuple("cafe2", "address2"));
    }

    @Test
    @DisplayName("회원의 카페 조회 시 남은 카페가 20개 미만이면 cafeRefillEvent를 발행해 회원의 unviewedCafe가 채운다.")
    void whenGetCafesForLoginMemberListenEvent() {
        //given
        final Cafe saved1 = cafeRepository.save(Fixture.getCafe("cafe1", "address1", 0));
        final Cafe saved2 = cafeRepository.save(Fixture.getCafe("cafe2", "address2", 0));
        final Member member = memberRepository.save(new Member("12345", "name", "image"));
        member.addUnViewedCafes(List.of(saved1, saved2));
        memberRepository.save(member);

        //when
        cafeService.getCafesForLoginMember(member.getId(), 2);

        //then
        final Member refilledMember = memberRepository.findWithUnViewedCafesById(member.getId()).get();
        final List<Cafe> unViewedCafes = refilledMember.getUnViewedCafes().stream()
                .map(UnViewedCafe::getCafe)
                .toList();

        assertThat(events.stream(CafeRefillEvent.class).count()).isEqualTo(1);
        assertThat(unViewedCafes).extracting("name", "address")
                .containsOnly(tuple("cafe1", "address1"), tuple("cafe2", "address2"));
    }
}
