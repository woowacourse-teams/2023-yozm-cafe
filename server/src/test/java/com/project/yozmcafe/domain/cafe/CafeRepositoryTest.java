package com.project.yozmcafe.domain.cafe;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;

import com.project.yozmcafe.domain.member.Member;

@DataJpaTest
@Sql("/cafeInit.sql")
class CafeRepositoryTest {

    @Autowired
    private CafeRepository cafes;

    @Test
    @DisplayName("비회원일 경우 요청한 페이지에 따른 카페정보 5개를 반환한다.")
    void findSliceBy() {
        //given
        PageRequest pageRequest = PageRequest.of(0, 5);

        //when
        final List<Cafe> cafes = this.cafes.findSliceBy(pageRequest).getContent();

        //then
        assertThat(cafes).hasSize(5);
    }

    @Sql("/cafeInit.sql")
    @Sql("/initMember.sql")
    @Test
    @DisplayName("멤버별 조회하지 않은 카페정보를 반환한다.")
    void findUnViewedCafesByMember() {
        //given
        PageRequest pageRequest = PageRequest.of(0, 5);
        Member member = new Member(1L);

        //when
        final List<Cafe> result = cafes.findUnViewedCafesByMember(pageRequest, member.getId()).getContent();

        //then
        assertThat(result).hasSize(5);

    }
}
