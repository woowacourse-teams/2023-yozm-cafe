package com.project.yozmcafe.controller;

import com.project.yozmcafe.controller.auth.OAuthProvider;
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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.document;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.springframework.restdocs.cookies.CookieDocumentation.cookieWithName;
import static org.springframework.restdocs.cookies.CookieDocumentation.requestCookies;
import static org.springframework.restdocs.cookies.CookieDocumentation.responseCookies;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;

class AuthControllerTest extends BaseControllerTest {

    @SpyBean
    GoogleOAuthClient googleOAuthClient;
    @SpyBean
    KakaoOAuthClient kakaoOAuthClient;
    @SpyBean
    MemberRepository memberRepository;
    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Test
    @DisplayName("Provider Google OAuth login을 한다.")
    void loginWithGoogle() {
        //given
        doReturn(new MemberInfo("openId", "오션", "바다.img"))
                .when(googleOAuthClient).getUserInfo(anyString());

        given(memberRepository.findById(anyString()))
                .willReturn(Optional.of(new Member("openId", "오션", "바다.img")));

        //when
        final Response response = given(spec)
                .log().all()
                .filter(document("OAuth/OAuth 로그인",
                        queryParameters(parameterWithName("code").description("Authorization Code")),
                        pathParameters(parameterWithName("providerName").description("OAuth Provider")),
                        responseFields(fieldWithPath("token").description("Access Token")),
                        responseCookies(cookieWithName("refreshToken").description("Refresh Token"))))
                .when()
                .post("/auth/{providerName}?code=googleCode", "google");

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
        final Response response = given()
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
        final String accessToken = jwtTokenProvider.createAccessFrom("1L");
        final String refreshToken = jwtTokenProvider.createRefresh();

        //when
        final Response response = given(spec).log().all()
                .filter(document("OAuth/토큰 갱신",
                        requestHeaders(headerWithName("Authorization").description("Access Token")),
                        requestCookies(cookieWithName("refreshToken").description("Refresh Token")),
                        responseFields(fieldWithPath("token").description("Access Token")),
                        responseCookies(cookieWithName("refreshToken").description("Refresh Token"))))
                .header("Authorization", accessToken)
                .cookie("refreshToken", refreshToken)
                .when()
                .log().all()
                .get("/auth");

        //then
        assertAll(
                () -> assertThat(response.jsonPath().getString("token")).isNotNull(),
                () -> assertThat(response.cookie("refreshToken")).isNotNull()
        );
    }

    @Test
    @DisplayName("Provider 인증 주소를 반환한다.")
    void authorizationUrls() {
        //when
        final Response response = given(spec).log().all()
                .filter(document("OAuth/OAuth Provider Url",
                        responseFields(fieldWithPath("[].provider").description("OAuth Provider 이름"),
                                fieldWithPath("[].authorizationUrl").description("Provider 인증 Url"))))
                .when()
                .log().all()
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
        final Response response = given(spec).log().all()
                .filter(document("OAuth/OAuth 로그아웃",
                        requestCookies(cookieWithName("refreshToken").description("Refresh Token"))
                ))
                .cookie(cookie)
                .delete("/auth");

        //then
        response.then().log().all()
                .statusCode(HttpStatus.OK.value())
                .cookie("refreshToken", expectedDetail);
    }
}
