package com.project.yozmcafe.controller.like;

import static com.project.yozmcafe.controller.like.LikeCafeDocuments.findLikeCafesDocument;
import static com.project.yozmcafe.controller.like.LikeCafeDocuments.likedCafeOfMemberDocument;
import static com.project.yozmcafe.controller.like.LikeCafeDocuments.memberUpdateLikeDocument;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.springframework.http.HttpStatus.OK;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.project.yozmcafe.controller.BaseControllerTest;
import com.project.yozmcafe.controller.dto.cafe.LikedCafeResponse;
import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.domain.cafe.CafeRepository;
import com.project.yozmcafe.domain.member.Member;
import com.project.yozmcafe.domain.member.MemberRepository;
import com.project.yozmcafe.fixture.Fixture;

import io.restassured.response.Response;

class LikedCafeControllerTest extends BaseControllerTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private CafeRepository cafeRepository;

    private Cafe cafe1, cafe2;

    @BeforeEach
    void setUp() {
        cafe1 = cafeRepository.save(Fixture.getCafe("오션의 귀여운 카페", "인천 오션동", 5));
        cafe2 = cafeRepository.save(Fixture.getCafe("오션의 귀여운 카페", "인천 오션동", 5));
    }

    @Test
    @DisplayName("사용자가 좋아요 한 카페들의 대표 이미지들을 조회한다.")
    void getLikedCafes() {
        //given
        final Member member = new Member("1234", "오션", "오션.img");
        member.updateLikedCafesBy(cafe1, true);
        member.updateLikedCafesBy(cafe2, true);
        memberRepository.save(member);

        //when
        final Response response = customGivenWithDocs(findLikeCafesDocument())
                .queryParam("page", 1)
                .get("/members/{memberId}/liked-cafes", member.getId());

        //then
        assertSoftly(softAssertions -> {
            assertThat(response.jsonPath().getLong("[0].cafeId")).isEqualTo(cafe2.getId());
            assertThat(response.jsonPath().getLong("[1].cafeId")).isEqualTo(cafe1.getId());
        });
    }

    @Test
    @DisplayName("멤버의 빈 좋아요한 카페 대표 이미지들을 조회한다.")
    void getLikedCafes_empty() {
        //given
        final Member member = memberRepository.save(new Member("1", "오션", "오션.img"));

        //when
        final Response response = customGiven()
                .queryParam("page", 1)
                .get("/members/{memberId}/liked-cafes", member.getId());

        //then
        assertThat(response.jsonPath().getList("")).isEmpty();
    }

    @Test
    @DisplayName("사용자가 카페에 대해 좋아요를 할 수 있다.")
    void updateLikes() {
        //given
        final Member member = memberRepository.save(new Member("123", "오션", "오션사진"));
        doReturn(member.getId()).when(jwtTokenProvider).getMemberId(anyString());
        doNothing().when(jwtTokenProvider).validate(anyString());

        //when
        final Response response = customGivenWithDocs(memberUpdateLikeDocument())
                .queryParam("isLiked", true)
                .auth().oauth2("accessToken")
                .post("/cafes/{cafeId}/likes", cafe1.getId());

        //then
        assertThat(response.statusCode()).isEqualTo(OK.value());
    }

    @Test
    @DisplayName("사용자의 좋아요 카페 목록의 카페들을 조회한다.")
    void getLikedCafeDetails() {
        //given
        final Member member = new Member("1234", "도치", "도치.img");
        member.updateLikedCafesBy(cafe1, true);
        member.updateLikedCafesBy(cafe2, true);
        Member savedMember = memberRepository.save(member);

        //when
        final Response response = customGivenWithDocs(likedCafeOfMemberDocument())
                .get("/members/{memberId}/liked-cafes/details", savedMember.getId());

        //then
        final List<LikedCafeResponse> responses = response.jsonPath().getList(".", LikedCafeResponse.class);

        assertSoftly(softAssertions -> {
            assertThat(response.statusCode()).isEqualTo(OK.value());
            assertThat(responses).extracting("id")
                    .containsExactly(cafe2.getId(), cafe1.getId());
        });
    }

    @Test
    @DisplayName("사용자의 빈 좋아요 카페 목록의 카페들을 조회한다.")
    void getLikedCafeDetailsWhenEmpty() {
        //given
        final Member savedMember = memberRepository.save(new Member("1234", "도치", "도치.img"));

        //when
        final Response response = customGiven()
                .get("/members/{memberId}/liked-cafes/details", savedMember.getId());

        //then
        assertThat(response.jsonPath().getList("")).isEmpty();
    }
}
