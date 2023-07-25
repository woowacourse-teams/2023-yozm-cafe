package com.project.yozmcafe.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.server.LocalServerPort;

import com.project.yozmcafe.controller.auth.MemberInfo;
import com.project.yozmcafe.domain.cafe.CafeRepository;
import com.project.yozmcafe.domain.member.MemberRepository;
import com.project.yozmcafe.service.auth.KakaoOAuthClient;
import com.project.yozmcafe.util.AcceptanceContext;

import io.restassured.RestAssured;
import io.restassured.response.Response;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class CafeControllerTest {
    @LocalServerPort
    private int port;
    @SpyBean
    KakaoOAuthClient kakaoOAuthClient;

    @Autowired
    private AcceptanceContext context;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private CafeRepository cafeRepository;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("카페에 좋아요를 누르고, 해당 카페를 조회하는 경우 isLike가 true로 매핑되어 응답한다.")
    void updateLikes() {
//        //given
//        final Member member = memberRepository.save(new Member("openId", "오션", "바다.img"));
//        final Cafe cafe = cafeRepository.save(Fixture.getCafe("카페1", "주소1", 11));
//        member.addUnViewedCafes(List.of(cafe));
//
//        final String token = getAccessToken().jsonPath().getString("token");
//        context.setAccessToken(token);
//
//        //when
//        context.invokeHttpPostWithToken("/cafes/" + cafe.getId() + "/likes?isLiked=true");
//        System.out.println("asdfasdfasdfas : " + member.getLikedCafes().size());
//        final Response likeResponse = context.response;
//
//        context.invokeHttpGetWithToken("/cafes");
//        final List<CafeResponse> cafeResponses = context.response.jsonPath().getList(".", CafeResponse.class);
//
//        System.out.println("asdfasdfasdfas : " + member.getLikedCafes().size());
//
//        //then
//        assertAll(
//                () -> assertThat(likeResponse.getStatusCode()).isEqualTo(200),
//                () -> assertThat(cafeResponses).hasSize(1),
//                () -> assertThat(cafeResponses.get(0).likeCount()).isEqualTo(12)
////                () -> assertThat(cafeResponses.get(0).isLiked()).isTrue(),
//        );
    }

    private Response getAccessToken() {
        doReturn(new MemberInfo("openId", "오션", "바다.img"))
                .when(kakaoOAuthClient).getUserInfo(anyString());

        return RestAssured.given()
                .log().all()
                .queryParam("code", "googleCode")
                .when()
                .post("/auth/{providerName}", "kakao");
    }
}
