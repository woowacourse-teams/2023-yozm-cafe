package com.project.yozmcafe.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;

import com.project.yozmcafe.util.AcceptanceContext;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

@Sql("/cafeInit.sql")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CafeAcceptanceTest {

    @LocalServerPort
    int port;

    @Autowired
    private AcceptanceContext context;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("로그인 되지 않은 사용자가 /cafes 에 GET요청을 보내면 랜덤한 카페 정보들을 응답한다.")
    void getCafesSuccessByUnLoginUser() {
        //given

        //when
        context.invokeHttpGet("/cafes");
        final ExtractableResponse<Response> response = context
                .response
                .then()
                .extract();

        //then
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.jsonPath().getList("$")).hasSize(3);
    }
}
