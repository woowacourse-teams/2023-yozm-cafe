package com.project.yozmcafe.domain.cafe;

import com.project.yozmcafe.domain.member.Member;
import com.project.yozmcafe.domain.member.MemberRepository;
import com.project.yozmcafe.fixture.Fixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class LikedCafeRepositoryTest {

    @Autowired
    private LikedCafeRepository likedCafeRepository;
    @Autowired
    private CafeRepository cafeRepository;
    @Autowired
    private MemberRepository memberRepository;

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
        final Slice<LikedCafe> likedCafeByMemberId = likedCafeRepository.
                findLikedCafesByMemberIdOrderByIdDesc(savedMember.getId(), pageRequest);

        //then
        assertAll(
                () -> assertThat(likedCafeByMemberId.getContent()).hasSize(2),
                () -> assertThat(likedCafeByMemberId.hasNext()).isFalse(),
                () -> assertThat(likedCafeByMemberId.isFirst()).isTrue(),
                () -> assertThat(likedCafeByMemberId.isLast()).isTrue()
        );
    }
}
