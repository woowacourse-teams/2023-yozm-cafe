package com.project.yozmcafe.service;

import com.project.yozmcafe.domain.RandomCafeShuffleStrategy;
import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.domain.cafe.CafeRepository;
import com.project.yozmcafe.domain.cafe.UnViewedCafe;
import com.project.yozmcafe.domain.cafe.UnViewedCafeRepository;
import com.project.yozmcafe.domain.member.Member;
import com.project.yozmcafe.domain.member.MemberRepository;
import com.project.yozmcafe.fixture.Fixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UnViewedCafeServiceTest extends BaseServiceTest {

    private UnViewedCafeService unViewedCafeService;

    @Autowired
    private CafeRepository cafeRepository;

    @Autowired
    UnViewedCafeRepository unViewedCafeRepository;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        unViewedCafeService = new UnViewedCafeService(cafeRepository, unViewedCafeRepository,
                new RandomCafeShuffleStrategy());
    }

    @Test
    @DisplayName("사용자의 UnViewedCafe에 모든 카페를 다시 채운다")
    void refillWhenUnViewedCafesSizeUnderTwenty() {
        //given
        final Member member = memberRepository.save(new Member("id", "연어", "image"));
        cafeRepository.save(Fixture.getCafe("cafe1", "address", 5));
        cafeRepository.save(Fixture.getCafe("cafe2", "address", 5));
        cafeRepository.save(Fixture.getCafe("cafe3", "address", 5));

        //when
        unViewedCafeService.refillUnViewedCafes(member);

        //then
        final Member refilledMember = memberRepository.findWithUnViewedCafesById(member.getId()).get();
        assertThat(refilledMember.getUnViewedCafes()).hasSize(3);
    }

    @Test
    @DisplayName("사용자의 UnViewedCafe에 카페를 채울 때, 이미 가진 unViewedCafe는 제외하고 채운다.")
    void refillNotDuplicatedUnViewedCafesWhenUnViewedCafesSizeUnderTwenty() {
        //given
        final Member member = memberRepository.save(new Member("id", "연어", "image"));
        final Cafe saved1 = cafeRepository.save(Fixture.getCafe("cafe1", "address", 5));
        cafeRepository.save(Fixture.getCafe("cafe2", "address", 5));
        cafeRepository.save(Fixture.getCafe("cafe3", "address", 5));
        member.addUnViewedCafes(List.of(saved1));
        memberRepository.save(member);

        //when
        unViewedCafeService.refillUnViewedCafes(member);

        //then
        final Member refilledMember = memberRepository.findWithUnViewedCafesById(member.getId()).get();
        final List<Cafe> unViewedCafes = refilledMember.getUnViewedCafes().stream()
                .map(UnViewedCafe::getCafe)
                .toList();
        assertThat(unViewedCafes).extracting("name")
                .containsOnly("cafe1", "cafe2", "cafe3");
    }
}
