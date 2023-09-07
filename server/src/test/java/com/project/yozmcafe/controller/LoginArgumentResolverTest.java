package com.project.yozmcafe.controller;

import com.project.yozmcafe.BaseTest;
import com.project.yozmcafe.util.AcceptanceContext;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import static com.project.yozmcafe.exception.ErrorCode.TOKEN_IS_EXPIRED;
import static com.project.yozmcafe.exception.ErrorCode.TOKEN_NOT_EXIST;
import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class LoginArgumentResolverTest extends BaseTest {

    @LocalServerPort
    private int port;
    @Autowired
    private AcceptanceContext context;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("만료된 토큰으로 요청을 보내면, 401을 응답한다.")
    void expiredToken() {
        //given
        context.accessToken = "eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2OTA4NjQ5NzQsImV4cCI6MTY5MDg2NTMzNCwic3ViIjoiMjkzOTU1Mzk2NyJ9.m8s0N30wn8g9eccIXRneOdhCsB_EZbR5etceyw2RaO4";

        //when
        context.invokeHttpGetWithToken("/cafes?page=1");

        //then
        context.response.then()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .body("code", is(TOKEN_IS_EXPIRED.getCode()))
                .body("message", is(TOKEN_IS_EXPIRED.getMessage()));
    }

    @Test
    @DisplayName("잘못된 토큰으로 요청을 보내면 500을 응답한다.")
    void invalidToken() {
        //given
        context.accessToken = "ahahah";

        //when
        context.invokeHttpGetWithToken("/cafes?page=1");

        //then
        context.response.then()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    @Test
    @DisplayName("토큰이 존재하지 않으면 401을 응답한다.")
    void notExistToken() {
        //when
        context.invokeHttpGet("/cafes?page=1");

        //then
        context.response.then()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .body("code", is(TOKEN_NOT_EXIST.getCode()))
                .body("message", is(TOKEN_NOT_EXIST.getMessage()));
    }
}
