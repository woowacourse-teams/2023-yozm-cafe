package com.project.yozmcafe.controller.auth;

import static com.project.yozmcafe.controller.auth.AuthDocuments.logoutDocument;
import static com.project.yozmcafe.controller.auth.AuthDocuments.oAuthLoginDocument;
import static com.project.yozmcafe.controller.auth.AuthDocuments.oAuthUrlDocument;
import static com.project.yozmcafe.controller.auth.AuthDocuments.updateTokenDocument;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.springframework.http.HttpStatus.OK;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import com.project.yozmcafe.controller.BaseControllerTest;
import com.project.yozmcafe.domain.member.Member;
import com.project.yozmcafe.domain.member.MemberInfo;
import com.project.yozmcafe.domain.member.MemberRepository;
import com.project.yozmcafe.service.auth.GoogleOAuthClient;
import com.project.yozmcafe.service.auth.JwtTokenProvider;
import com.project.yozmcafe.service.auth.KakaoOAuthClient;

import io.restassured.http.Cookie;
import io.restassured.matcher.DetailedCookieMatcher;
import io.restassured.matcher.RestAssuredMatchers;
import io.restassured.response.Response;

class AuthControllerTest extends BaseControllerTest {

    @SpyBean
    protected GoogleOAuthClient googleOAuthClient;
    @SpyBean
    protected KakaoOAuthClient kakaoOAuthClient;
    @SpyBean
    protected MemberRepository memberRepository;
    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Test
    @DisplayName("Provider Google OAuth login을 한다.")
    void loginWithGoogle() {
        //given
        doReturn(new MemberInfo("openId", "오션", "바다.img"))
                .when(googleOAuthClient).getUserInfo(anyString());
        doReturn(Optional.of(new Member("openId", "오션", "바다.img")))
                .when(memberRepository).findById(anyString());

        //when
        final Response response = customGivenWithDocs(oAuthLoginDocument())
                .queryParam("code", "googleCode")
                .post("/auth/{providerName}?code=googleCode", "google");

        //then
        assertSoftly(softAssertions -> {
            assertThat(response.statusCode()).isEqualTo(OK.value());
            assertThat(response.jsonPath().getString("token")).isNotNull();
            assertThat(response.cookie("refreshToken")).isNotNull();
        });
    }

    @Test
    @DisplayName("Provider Kakao OAuth login을 한다.")
    void loginWithKakao() {
        //given
        doReturn(new MemberInfo("openId", "오션", "바다.img"))
                .when(kakaoOAuthClient).getUserInfo(anyString());
        doReturn(Optional.of(new Member("openId", "오션", "바다.img")))
                .when(memberRepository).findById(anyString());

        //when
        final Response response = customGiven()
                .queryParam("code", "kakaoCode")
                .post("/auth/{providerName}", "kakao");

        //then
        assertSoftly(softAssertions -> {
            assertThat(response.statusCode()).isEqualTo(OK.value());
            assertThat(response.jsonPath().getString("token")).isNotNull();
            assertThat(response.cookie("refreshToken")).isNotNull();
        });
    }

    @Test
    @DisplayName("토큰을 갱신한다.")
    void refreshToken() {
        //given
        final String accessToken = jwtTokenProvider.createAccessFrom("1L");
        final String refreshToken = jwtTokenProvider.createRefresh();

        //when
        final Response response = customGivenWithDocs(updateTokenDocument())
                .header("Authorization", accessToken)
                .cookie("refreshToken", refreshToken)
                .get("/auth");

        //then
        assertSoftly(softAssertions -> {
            assertThat(response.jsonPath().getString("token")).isNotNull();
            assertThat(response.cookie("refreshToken")).isNotNull();
        });
    }

    @Test
    @DisplayName("Provider 인증 주소를 반환한다.")
    void authorizationUrls() {
        //when
        final Response response = customGivenWithDocs(oAuthUrlDocument())
                .get("/auth/urls");

        //then
        for (OAuthProvider provider : OAuthProvider.values()) {
            String providerPath = "[" + provider.ordinal() + "]" + ".provider";
            String urlPath = "[" + provider.ordinal() + "]" + ".authorizationUrl";

            assertThat(response.getBody().jsonPath().getString(providerPath)).isEqualTo(provider.getProviderName());
            assertThat(response.getBody().jsonPath().getString(urlPath))
                    .contains("response_type", "redirect_uri", "client_id", "scope");
        }
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
        final Response response = customGivenWithDocs(logoutDocument())
                .cookie(cookie)
                .delete("/auth");

        //then
        assertSoftly(softAssertions -> {
            assertThat(response.statusCode()).isEqualTo(OK.value());
            assertThat(expectedDetail.matches(response.detailedCookie("refreshToken"))).isTrue();
        });
    }

    @Test
    @DisplayName("새로운 유저가 로그인한다.")
    void register() {
        //given
        doReturn(new MemberInfo("openId", "오션", "바다.img"))
                .when(googleOAuthClient).getUserInfo(anyString());
        doReturn(Optional.empty())
                .when(memberRepository).findById(anyString());

        //when
        final Response response = customGiven()
                .post("/auth/{providerName}?code=googleCode", "google");

        //then
        assertSoftly(softAssertions -> {
            assertThat(response.statusCode()).isEqualTo(OK.value());
            assertThat(response.jsonPath().getString("token")).isNotNull();
            assertThat(response.cookie("refreshToken")).isNotNull();
        });
    }
}
