package com.project.yozmcafe.service.auth;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class JwtTokenProviderTest {

    private final String testKey = "dsfjiaslejfilasjelfijsleifjliesajlif";
    private final JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(
            testKey,
            1,
            10210401);

    @Test
    @DisplayName("리프레시 토큰 생성 테스트")
    void createRefreshToken() {
        //given
        final String token = jwtTokenProvider.createRefresh();

        //when, then
        assertDoesNotThrow(() -> jwtTokenProvider.validate(token));
    }

    @Test
    @DisplayName("액세스 토큰에서 데이터 가져오기")
    void getPayload() {
        //given
        final String token = jwtTokenProvider.createAccess("연어");

        //when
        final String payload = jwtTokenProvider.getMemberId(token);

        //then
        assertThat(payload).isEqualTo("연어");
    }
}
