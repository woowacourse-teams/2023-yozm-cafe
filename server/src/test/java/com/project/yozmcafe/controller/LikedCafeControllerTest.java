package com.project.yozmcafe.controller;

import com.project.yozmcafe.controller.dto.LikedCafeResponse;
import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.domain.cafe.CafeRepository;
import com.project.yozmcafe.domain.member.Member;
import com.project.yozmcafe.domain.member.MemberInfo;
import com.project.yozmcafe.domain.member.MemberRepository;
import com.project.yozmcafe.fixture.Fixture;
import com.project.yozmcafe.service.auth.JwtTokenProvider;
import com.project.yozmcafe.service.auth.KakaoOAuthClient;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;

import java.util.List;

import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.document;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;

class LikedCafeControllerTest extends BaseControllerTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private CafeRepository cafeRepository;
    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @SpyBean
    private KakaoOAuthClient kakaoOAuthClient;

    @Test
    @DisplayName("사용자의 좋아요 한 카페 목록을 조회한다.")
    void getLikedCafes() {
        //given
        final Cafe savedCafe1 = cafeRepository.save(Fixture.getCafe("오션의 귀여운 카페", "인천 오션동", 5));
        final Cafe savedCafe2 = cafeRepository.save(Fixture.getCafe("오션의 귀여운 카페", "인천 오션동", 5));
        final Member member = new Member("1234", "오션", "오션.img");
        member.updateLikedCafesBy(savedCafe1, true);
        member.updateLikedCafesBy(savedCafe2, true);
        memberRepository.save(member);

        //when
        final Response response = given(spec).log().all()
                .filter(document("likedCafe/좋아요 카페 목록 조회",
                        queryParameters(parameterWithName("page").description("좋아요 목록 페이지 번호")),
                        pathParameters(parameterWithName("memberId").description("멤버 ID")),
                        responseFields(fieldWithPath("[].cafeId").description("카페 ID"),
                                fieldWithPath("[].imageUrl").description("카페 대표 사진"))))
                .when()
                .get("/members/{memberId}/liked-cafes?page=1", member.getId());

        //then
        assertAll(
                () -> assertThat(response.jsonPath().getLong("[0].cafeId")).isEqualTo(savedCafe2.getId()),
                () -> assertThat(response.jsonPath().getLong("[1].cafeId")).isEqualTo(savedCafe1.getId())
        );
    }

    @Test
    @DisplayName("멤버의 빈 좋아요 목록을 조회한다.")
    void getLikedCafes_empty() {
        //given
        final Member member = new Member("1", "오션", "오션.img");
        memberRepository.save(member);

        //when
        final Response response = given().log().all()
                .when().get("/members/{memberId}/liked-cafes?page=1", member.getId());

        //then
        assertThat(response.jsonPath().getList("")).isEmpty();
    }

    @Test
    @DisplayName("재로그인 시 좋아요 목록이 남아있는다.")
    void reLoginLikedListTest() {
        //given
        final Cafe cafe = cafeRepository.save(Fixture.getCafe("카페1", "주소1", 10));
        final Member member = memberRepository.save(
                new Member("memberId", "폴로", "폴로사진"));
        given(jwtTokenProvider.getMemberId(anyString())).willReturn(member.getId());
        doReturn(new MemberInfo(member.getId(), member.getName(), member.getImage()))
                .when(kakaoOAuthClient).getUserInfo(anyString());

        //when
        given().log().all()
                .auth().oauth2("accessToken")
                .post("/cafes/" + cafe.getId() + "/likes?isLiked=true");

        reLogin();

        final Response response = given().log().all()
                .when().get("/members/{memberId}/liked-cafes?size=1&page=1", member.getId());

        final List<LikedCafeResponse> result = response.jsonPath().getList("");

        //then
        assertThat(result).hasSize(1);
    }

    private void reLogin() {
        given()
                .log().all()
                .queryParam("code", "googleCode")
                .when()
                .post("/auth/{providerName}", "kakao");
    }

    @Test
    @DisplayName("사용자가 카페에 대해 좋아요를 할 수 있다.")
    void updateLikes() {
        //given
        final Member member = memberRepository.save(
                new Member("123", "오션", "오션사진"));
        final Cafe cafe = cafeRepository.save(Fixture.getCafe(1L, "카페1", "주소1", 10));
        given(jwtTokenProvider.getMemberId(anyString())).willReturn(member.getId());

        //when
        final Response response = given(spec).log().all()
                .filter(document("likedCafe/좋아요",
                        queryParameters(parameterWithName("isLiked").description("true 일 경우 좋아요 추가, false 일 경우 좋아요 취소")),
                        pathParameters(parameterWithName("cafeId").description("카페 ID"))))
                .auth().oauth2("accessToken")
                .post("/cafes/{cafeId}/likes?isLiked=true", cafe.getId());

        response.then()
                .statusCode(HttpStatus.OK.value());
    }
}
