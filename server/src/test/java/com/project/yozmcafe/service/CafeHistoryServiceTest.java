package com.project.yozmcafe.service;

import static com.project.yozmcafe.fixture.Fixture.CAFE_1;
import static com.project.yozmcafe.fixture.Fixture.CAFE_2;
import static com.project.yozmcafe.fixture.Fixture.CAFE_3;
import static com.project.yozmcafe.fixture.Fixture.CAFE_4;
import static com.project.yozmcafe.fixture.Fixture.CAFE_5;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.domain.cafe.CafeRepository;
import com.project.yozmcafe.domain.cafe.UnViewedCafe;
import com.project.yozmcafe.domain.cafe.UnViewedCafeRepository;
import com.project.yozmcafe.domain.member.Member;
import com.project.yozmcafe.domain.member.MemberRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class CafeHistoryServiceTest {

    private CafeHistoryService cafeHistoryService;

    @Autowired
    private CafeRepository cafes;

    @Autowired
    private UnViewedCafeRepository unViewedCafes;

    @Autowired
    private MemberRepository members;

    @BeforeEach
    void setUp() {
        cafeHistoryService = new CafeHistoryService(cafes, unViewedCafes);
    }

    @Test
    @DisplayName("사용자의 unViewedCafe를 삭제한다.")
    void removeUnViewedCafe() {
        //given
        final Member member = new Member(null);
        final Cafe cafe1 = cafes.save(CAFE_4);
        final Cafe cafe2 = cafes.save(CAFE_5);
        final UnViewedCafe unViewedCafe1 = unViewedCafes.save(new UnViewedCafe(null, cafe1, member));
        final UnViewedCafe unViewedCafe2 = unViewedCafes.save(new UnViewedCafe(null, cafe2, member));
        member.addUnViewedCafes(List.of(unViewedCafe1, unViewedCafe2));

        //when
        cafeHistoryService.removeUnViewedCafe(member, cafe1.getId());

        //then
        assertThat(member.getUnViewedCafes()).hasSize(1);
    }

    @Test
    @DisplayName("사용자의 unViewedCafe를 삭제하고 unViewedCafe가 비어있다면, 모든 카페를 넣는다.")
    void removeUnViewedCafeWhenEmpty() {
        final Member member = members.save(new Member(null));
        final Cafe cafe1 = cafes.save(CAFE_1);
        final Cafe cafe2 = cafes.save(CAFE_2);
        final Cafe cafe3 = cafes.save(CAFE_3);
        final UnViewedCafe unViewedCafe1 = unViewedCafes.save(new UnViewedCafe(null, cafe1, member));
        member.addUnViewedCafes(List.of(unViewedCafe1));

        //when
        cafeHistoryService.removeUnViewedCafe(member, cafe1.getId());

        //then
        assertThat(member.getUnViewedCafes()).hasSize(3);
    }
}
