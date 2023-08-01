package com.project.yozmcafe.controller;

import static com.project.yozmcafe.exception.ErrorCode.TOKEN_IS_EXPIRED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;

import com.project.yozmcafe.util.AcceptanceContext;

import io.restassured.RestAssured;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class LoginArgumentResolverTest {

    @LocalServerPort
    private int port;
    @Autowired
    private AcceptanceContext context;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("만료된 토큰으로 요청을 보내면, ErrorResponse를 응답한다.")
    void invalidTokenFail() {
        //given
        context.accessToken = "eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2OTA4NjQ5NzQsImV4cCI6MTY5MDg2NTMzNCwic3ViIjoiMjkzOTU1Mzk2NyJ9.m8s0N30wn8g9eccIXRneOdhCsB_EZbR5etceyw2RaO4";

        //when
        context.invokeHttpGetWithToken("/cafes?page=1");
        final String errorCode = context.response.jsonPath().getString("code");
        final String errorMessage = context.response.jsonPath().getString("message");

        //then
        assertSoftly(softAssertions -> {
                    assertThat(errorCode).isEqualTo(TOKEN_IS_EXPIRED.getCode());
                    assertThat(errorMessage).isEqualTo(TOKEN_IS_EXPIRED.getMessage());
                }
        );
    }
}
