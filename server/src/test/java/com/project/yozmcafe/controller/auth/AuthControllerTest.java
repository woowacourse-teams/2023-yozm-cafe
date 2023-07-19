package com.project.yozmcafe.controller.auth;

import com.project.yozmcafe.domain.member.Member;
import com.project.yozmcafe.domain.member.MemberRepository;
import com.project.yozmcafe.service.auth.GoogleOAuthClient;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthControllerTest {

    @LocalServerPort
    int port;

    @MockBean
    GoogleOAuthClient googleOAuthClient;
    @MockBean
    MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("구글 OAuth 로그인을 한다.")
    void login() {
        //given
        given(googleOAuthClient.getUserInfo(anyString()))
                .willReturn(new MemberInfo("openId", "오션", "바다.img"));
        given(memberRepository.findById(anyString()))
                .willReturn(Optional.of(new Member("openId", "오션", "바다.img")));

        //when
        final Response response = RestAssured.given()
                .log().all()
                .queryParam("code", "googleCode")
                .pathParam("providerName", "google")
                .when()
                .get("/auth/{providerName}");

        //then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.cookie("refreshToken")).isNotNull()
        );
    }
}
