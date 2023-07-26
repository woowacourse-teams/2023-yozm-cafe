package com.project.yozmcafe.domain.member;

import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.domain.cafe.CafeRepository;
import com.project.yozmcafe.domain.cafe.LikedCafe;
import com.project.yozmcafe.fixture.Fixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private CafeRepository cafeRepository;

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

    @Test
    @DisplayName("멤버의 좋아요 목록을 반환한다.")
    void findLikedCafesById() {
        //given
        PageRequest pageRequest = PageRequest.of(0, 2);
        final Cafe save1 = cafeRepository.save(Fixture.getCafe("오션의 귀여운 카페", "인천 오션동", 5));
        final Cafe save2 = cafeRepository.save(Fixture.getCafe("오션의 멋진 카페", "서울 오션동", 5));
        final Member member = new Member("1234", "오션", "오션.img");
        member.addLikedCafe(save1);
        member.addLikedCafe(save2);
        final Member savedMember = memberRepository.save(member);

        //when
        final Slice<LikedCafe> likedCafeByMemberId = memberRepository.findLikedCafeByMemberId(savedMember.getId(), pageRequest);

        //then
        assertAll(
                () -> assertThat(likedCafeByMemberId.getContent()).hasSize(2),
                () -> assertThat(likedCafeByMemberId.hasNext()).isFalse(),
                () -> assertThat(likedCafeByMemberId.isFirst()).isTrue(),
                () -> assertThat(likedCafeByMemberId.isLast()).isTrue()
        );
    }
}
