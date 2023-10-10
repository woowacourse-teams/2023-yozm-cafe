package com.project.yozmcafe.controller;

import static com.project.yozmcafe.exception.ErrorCode.TOKEN_IS_EXPIRED;
import static com.project.yozmcafe.exception.ErrorCode.TOKEN_NOT_EXIST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

class LoginArgumentResolverTest extends BaseControllerTest {

    @Test
    @DisplayName("만료된 토큰으로 요청을 보내면, 401을 응답한다.")
    void expiredToken() {
        //given
        String expiredToken = "eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2OTA4NjQ5NzQsImV4cCI6MTY5MDg2NTMzNCwic3ViIjoiMjkzOTU1Mzk2NyJ9.m8s0N30wn8g9eccIXRneOdhCsB_EZbR5etceyw2RaO4";

        //when
        final ExtractableResponse<Response> response = customGiven()
                .header("Authorization", expiredToken)
                .when()
                .get("/cafes")
                .then()
                .extract();

        //then
        assertSoftly(softAssertions -> {
            assertThat(response.statusCode()).isEqualTo(UNAUTHORIZED.value());
            assertThat(response.jsonPath().getString("code")).isEqualTo(TOKEN_IS_EXPIRED.getCode());
            assertThat(response.jsonPath().getString("message")).isEqualTo(TOKEN_IS_EXPIRED.getMessage());
        });
    }

    @Test
    @DisplayName("잘못된 토큰으로 요청을 보내면 500을 응답한다.")
    void invalidToken() {
        //given
        String expiredToken = "invalidToken";

        //when
        final ExtractableResponse<Response> response = customGiven()
                .header("Authorization", expiredToken)
                .when()
                .get("/cafes")
                .then()
                .extract();

        //then
        assertThat(response.statusCode()).isEqualTo(INTERNAL_SERVER_ERROR.value());
    }

    @Test
    @DisplayName("토큰이 존재하지 않으면 401을 응답한다.")
    void notExistToken() {
        //when
        final ExtractableResponse<Response> response = customGiven()
                .when()
                .get("/cafes")
                .then()
                .extract();

        //then
        assertSoftly(softAssertions -> {
            assertThat(response.statusCode()).isEqualTo(UNAUTHORIZED.value());
            assertThat(response.jsonPath().getString("code")).isEqualTo(TOKEN_NOT_EXIST.getCode());
            assertThat(response.jsonPath().getString("message")).isEqualTo(TOKEN_NOT_EXIST.getMessage());
        });
    }
}
