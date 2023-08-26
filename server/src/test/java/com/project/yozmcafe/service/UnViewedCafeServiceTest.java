package com.project.yozmcafe.service;

import com.project.yozmcafe.domain.RandomCafeShuffleStrategy;
import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.domain.cafe.CafeRepository;
import com.project.yozmcafe.domain.member.Member;
import com.project.yozmcafe.domain.member.MemberRepository;
import com.project.yozmcafe.fixture.Fixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class UnViewedCafeServiceTest {

    private UnViewedCafeService unViewedCafeService;

    @Autowired
    private CafeRepository cafeRepository;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        unViewedCafeService = new UnViewedCafeService(cafeRepository, new RandomCafeShuffleStrategy());
    }

    @Test
    @DisplayName("사용자의 unViewedCafe를 삭제할 때 남은 갯수가 20개 미만이면 새로 넣는다")
    void removeUnViewedCafe() {
        //given
        final Member member = memberRepository.save(new Member("1", "폴로", "폴로사진"));
        final Cafe cafe1 = cafeRepository.save(Fixture.getCafe("카페1", "주소1", 3));
        final Cafe cafe2 = cafeRepository.save(Fixture.getCafe("카페2", "주소2", 4));
        member.addUnViewedCafes(List.of(cafe1, cafe2));

        //when
        unViewedCafeService.removeUnViewedCafeByCafeId(member, cafe1.getId());

        //then
        assertThat(member.getUnViewedCafes()).hasSize(3);
    }

    @Test
    @DisplayName("사용자의 unViewedCafe를 삭제하고 unViewedCafe가 비어있다면, 모든 카페를 넣는다.")
    void removeUnViewedCafeWhenEmpty() {
        final Member member = memberRepository.save(new Member("2", "도치", "도치사진"));
        final Cafe cafe1 = cafeRepository.save(Fixture.getCafe("카페1", "주소1", 3));
        final Cafe cafe2 = cafeRepository.save(Fixture.getCafe("카페2", "주소2", 4));
        final Cafe cafe3 = cafeRepository.save(Fixture.getCafe("카페3", "주소3", 5));
        member.addUnViewedCafes(List.of(cafe1));

        //when
        unViewedCafeService.removeUnViewedCafeByCafeId(member, cafe1.getId());

        //then
        assertThat(member.getUnViewedCafes()).hasSize(3);
    }

    @Test
    @DisplayName("사용자의 unViewedCafe를 삭제하고 남은 unViewedCafe가 20개 이상이라면 삭제만 한다")
    void removeUnViewedCafe_over20() {
        final Member member = memberRepository.save(new Member("2", "도치", "도치사진"));

        final List<Cafe> allCafes = new ArrayList<>();
        for (int i = 0; i < 21; i++) {
            allCafes.add(Fixture.getCafe((long) i, "카페", "주소", 3));
        }
        member.addUnViewedCafes(allCafes);

        //when
        unViewedCafeService.removeUnViewedCafeByCafeId(member, 1L);

        //then
        assertThat(member.getUnViewedCafes()).hasSize(20);
    }

    @Test
    @DisplayName("사용자의 UnViewedCafe가 20개 미만일 때 모든 카페를 다시 채운다")
    void refillWhenUnViewedCafesSizeUnderTwenty() {
        //given
        final Member member = memberRepository.save(new Member("id", "연어", "image"));
        cafeRepository.save(Fixture.getCafe("cafe1", "address", 5));
        cafeRepository.save(Fixture.getCafe("cafe2", "address", 5));
        cafeRepository.save(Fixture.getCafe("cafe3", "address", 5));

        //when
        unViewedCafeService.refillWhenUnViewedCafesSizeUnderTwenty(member);

        //then
        assertThat(member.getUnViewedCafes()).hasSize(3);
    }

    @Test
    @DisplayName("사용자의 UnViewedCafe가 20개 이상이면 다시 채우지 않는다")
    void refillWhenUnViewedCafesSizeUnderTwenty2() {
        //given
        final Member member = memberRepository.save(new Member("id", "연어", "image"));

        final int initialUnViewedCafesSize = 20;
        final List<Cafe> allCafes = new ArrayList<>();
        for (int i = 0; i < initialUnViewedCafesSize; i++) {
            allCafes.add(Fixture.getCafe((long) i, "카페", "주소", 3));
        }
        member.addUnViewedCafes(allCafes);

        //when
        unViewedCafeService.refillWhenUnViewedCafesSizeUnderTwenty(member);

        //then
        assertThat(member.getUnViewedCafes()).hasSize(initialUnViewedCafesSize);
    }
}
