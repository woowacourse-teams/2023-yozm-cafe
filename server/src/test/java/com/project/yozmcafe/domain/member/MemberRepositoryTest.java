package com.project.yozmcafe.domain.member;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("회원 ID로 조회")
    void findById() {
        //given
        final Member member = new Member("123214", "연어", "http://salmon");
        memberRepository.save(member);

        //when
        final Optional<Member> persisted = memberRepository.findById("123214");

        //then
        assertThat(persisted).isPresent();
    }

    @Test
    @DisplayName("없는 ID로 조회하면 실패")
    void findById_fail() {
        //given
        final Member member = new Member("123214", "연어", "http://salmon");
        memberRepository.save(member);

        //when
        final Optional<Member> persisted = memberRepository.findById("1");

        //then
        assertThat(persisted).isEmpty();
    }
}
