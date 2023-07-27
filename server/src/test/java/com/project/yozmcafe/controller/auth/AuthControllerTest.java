package com.project.yozmcafe.controller.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import com.project.yozmcafe.domain.member.Member;
import com.project.yozmcafe.domain.member.MemberInfo;
import com.project.yozmcafe.domain.member.MemberRepository;
import com.project.yozmcafe.service.auth.GoogleOAuthClient;
import com.project.yozmcafe.service.auth.JwtTokenProvider;
import com.project.yozmcafe.service.auth.KakaoOAuthClient;
import com.project.yozmcafe.util.AcceptanceContext;

import io.restassured.RestAssured;
import io.restassured.http.Cookie;
import io.restassured.matcher.DetailedCookieMatcher;
import io.restassured.matcher.RestAssuredMatchers;
import io.restassured.response.Response;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthControllerTest {

    @LocalServerPort
    int port;

    @SpyBean
    GoogleOAuthClient googleOAuthClient;
    @SpyBean
    KakaoOAuthClient kakaoOAuthClient;
    @MockBean
    MemberRepository memberRepository;
    @SpyBean
    JwtTokenProvider jwtTokenProvider;

    private AcceptanceContext request;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        request = new AcceptanceContext();
    }

    @Test
    @DisplayName("Provider Google OAuth login을 한다.")
    void loginWithGoogle() {
        //given
        doReturn(new MemberInfo("openId", "오션", "바다.img"))
                .when(googleOAuthClient).getUserInfo(anyString());

        given(memberRepository.findById(anyString()))
                .willReturn(Optional.of(new Member("openId", "오션", "바다.img")));

        //when
        final Response response = RestAssured.given()
                .log().all()
                .queryParam("code", "googleCode")
                .when()
                .post("/auth/{providerName}", "google");

        //then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.jsonPath().getString("token")).isNotNull(),
                () -> assertThat(response.cookie("refreshToken")).isNotNull()
        );
    }

    @Test
    @DisplayName("Provider Kakao OAuth login을 한다.")
    void loginWithKakao() {
        //given
        doReturn(new MemberInfo("openId", "오션", "바다.img"))
                .when(kakaoOAuthClient).getUserInfo(anyString());

        given(memberRepository.findById(anyString()))
                .willReturn(Optional.of(new Member("openId", "오션", "바다.img")));

        //when
        final Response response = RestAssured.given()
                .log().all()
                .queryParam("code", "googleCode")
                .when()
                .post("/auth/{providerName}", "kakao");

        //then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.jsonPath().getString("token")).isNotNull(),
                () -> assertThat(response.cookie("refreshToken")).isNotNull()
        );
    }

    @Test
    @DisplayName("토큰을 갱신한다.")
    void refreshToken() {
        //given
        doReturn("goodOceanAccessToken")
                .when(jwtTokenProvider).refreshAccessToken(anyString(), anyString());

        //when
        final Response response = RestAssured.given().log().all()
                .header("Authorization", "goodOceanAccessToken")
                .cookie("refreshToken", "handSomeOceanRefreshToken")
                .when()
                .log().all()
                .get("/auth");

        //then
        assertAll(
                () -> assertThat(response.jsonPath().getString("token")).isEqualTo("goodOceanAccessToken"),
                () -> assertThat(response.cookie("refreshToken")).isNotNull()
        );
    }

    @ParameterizedTest(name = "{0} 인증 주소 Redirect")
    @EnumSource(value = OAuthProvider.class)
    @DisplayName("Provider 별 인증 주소로 Redirect 한다.")
    void redirectAuthorizationUri(OAuthProvider oAuthProvider) {
        //when
        final Response response = RestAssured.given().log().all()
                .when()
                .log().all()
                .redirects().follow(false)
                .get("/auth/{Provider}", oAuthProvider);

        //then
        assertThat(response.getHeader("Location")).contains("response_type", "redirect_uri", "client_id", "scope");
    }

    @Test
    @DisplayName("로그아웃을 한다")
    void logout() {
        //given
        final Cookie cookie = new Cookie.Builder("refreshToken", "리프레시").build();
        final DetailedCookieMatcher expectedDetail = RestAssuredMatchers.detailedCookie()
                .value("")
                .maxAge(0);

        //when
        request.invokeHttpDeleteWithCookie("/auth", cookie);

        //then
        request.response.then()
                .statusCode(HttpStatus.OK.value())
                .cookie("refreshToken", expectedDetail);
    }
}
