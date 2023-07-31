package com.project.yozmcafe.service;

import com.project.yozmcafe.domain.RandomCafeShuffleStrategy;
import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.domain.cafe.CafeRepository;
import com.project.yozmcafe.domain.cafe.UnViewedCafeRepository;
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

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class UnViewedCafeServiceTest {

    private UnViewedCafeService unViewedCafeService;

    @Autowired
    private CafeRepository cafeRepository;

    @Autowired
    private UnViewedCafeRepository unViewedCafeRepository;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        unViewedCafeService = new UnViewedCafeService(unViewedCafeRepository, cafeRepository, new RandomCafeShuffleStrategy());
    }

    @Test
    @DisplayName("사용자의 unViewedCafe를 삭제한다.")
    void removeUnViewedCafe() {
        //given
        final Member member = memberRepository.save(new Member("1", "폴로", "폴로사진"));
        final Cafe cafe1 = cafeRepository.save(Fixture.getCafe("카페1", "주소1", 3));
        final Cafe cafe2 = cafeRepository.save(Fixture.getCafe("카페2", "주소2", 4));
        member.addUnViewedCafesWithShuffle(Arrays.asList(cafe1, cafe2));

        //when
        unViewedCafeService.removeUnViewedCafe(member, cafe1.getId());

        //then
        assertThat(member.getUnViewedCafes()).hasSize(1);
        assertThat(unViewedCafeRepository.findAll()).hasSize(1);
    }

    @Test
    @DisplayName("사용자의 unViewedCafe를 삭제하고 unViewedCafe가 비어있다면, 모든 카페를 넣는다.")
    void removeUnViewedCafeWhenEmpty() {
        final Member member = memberRepository.save(new Member("2", "도치", "도치사진"));
        final Cafe cafe1 = cafeRepository.save(Fixture.getCafe("카페1", "주소1", 3));
        final Cafe cafe2 = cafeRepository.save(Fixture.getCafe("카페2", "주소2", 4));
        final Cafe cafe3 = cafeRepository.save(Fixture.getCafe("카페3", "주소3", 5));
        member.addUnViewedCafesWithShuffle(List.of(cafe1));

        //when
        unViewedCafeService.removeUnViewedCafe(member, cafe1.getId());

        //then
        assertThat(member.getUnViewedCafes()).hasSize(3);
        assertThat(unViewedCafeRepository.findAll()).hasSize(3);
    }
}
