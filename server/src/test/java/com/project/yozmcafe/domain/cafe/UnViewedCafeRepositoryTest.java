package com.project.yozmcafe.domain.cafe;

import com.project.yozmcafe.BaseTest;
import com.project.yozmcafe.domain.member.Member;
import com.project.yozmcafe.domain.member.MemberRepository;
import com.project.yozmcafe.fixture.Fixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UnViewedCafeRepositoryTest extends BaseTest {

    @Autowired
    private UnViewedCafeRepository unViewedCafeRepository;

    @Autowired
    private CafeRepository cafeRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("unViewedCafe를 batch insert한다.")
    void batchInsertUnViewedCafes() {
        // given
        final List<Cafe> cafes = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            cafes.add(Fixture.getCafe("name", "address", 0));
        }
        final List<Cafe> savedCafes = cafeRepository.saveAll(cafes);
        final Member member = memberRepository.save(new Member("id", "name", "image"));

        //when
        unViewedCafeRepository.saveUnViewedCafes(savedCafes, member);

        // then
        final Member refilledMember = memberRepository.findWithUnViewedCafesById(member.getId()).get();
        assertThat(refilledMember.getUnViewedCafes()).hasSize(200);
    }
}
