package com.project.yozmcafe.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.domain.cafe.CafeRepository;
import com.project.yozmcafe.domain.cafe.UnViewedCafeRepository;
import com.project.yozmcafe.domain.member.Member;
import com.project.yozmcafe.domain.member.MemberRepository;
import com.project.yozmcafe.fixture.Fixture;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class CafeHistoryServiceTest {

    private CafeHistoryService cafeHistoryService;

    @Autowired
    private CafeRepository cafeRepository;

    @Autowired
    private UnViewedCafeRepository unViewedCafeRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TestEntityManager em;

    @BeforeEach
    void setUp() {
        cafeHistoryService = new CafeHistoryService(cafeRepository, unViewedCafeRepository);
    }

    @Test
    @DisplayName("사용자의 unViewedCafe를 삭제한다.")
    void removeUnViewedCafe() {
        //given
        final Member member = memberRepository.save(new Member(null));
        final Cafe cafe1 = cafeRepository.save(Fixture.getCafe("카페1", "주소1", 3));
        final Cafe cafe2 = cafeRepository.save(Fixture.getCafe("카페2", "주소2", 4));
//        final UnViewedCafe unViewedCafe1 = unViewedCafeRepository.save(new UnViewedCafe(cafe1, member));
//        final UnViewedCafe unViewedCafe2 = unViewedCafeRepository.save(new UnViewedCafe(cafe2, member));
        member.addUnViewedCafes(List.of(cafe1, cafe2));
        em.flush();

        //when
        cafeHistoryService.removeUnViewedCafe(member, cafe1.getId());

        //then

        assertThat(member.getUnViewedCafes()).hasSize(1);
        assertThat(unViewedCafeRepository.findAll()).hasSize(1);
    }

    @Test
    @DisplayName("사용자의 unViewedCafe를 삭제하고 unViewedCafe가 비어있다면, 모든 카페를 넣는다.")
    void removeUnViewedCafeWhenEmpty() {
        final Member member = memberRepository.save(new Member(null));
        final Cafe cafe1 = cafeRepository.save(Fixture.getCafe("카페1", "주소1", 3));
        final Cafe cafe2 = cafeRepository.save(Fixture.getCafe("카페2", "주소2", 4));
        final Cafe cafe3 = cafeRepository.save(Fixture.getCafe("카페3", "주소3", 5));
//        final UnViewedCafe unViewedCafe1 = unViewedCafeRepository.save(new UnViewedCafe(cafe1, member));
        member.addUnViewedCafes(List.of(cafe1));
        em.flush();

        //when
        cafeHistoryService.removeUnViewedCafe(member, cafe1.getId());

        //then
        assertThat(member.getUnViewedCafes()).hasSize(3);
        assertThat(unViewedCafeRepository.findAll()).hasSize(3);
    }
}