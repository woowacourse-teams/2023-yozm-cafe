package com.project.yozmcafe.service.auth;

import static com.project.yozmcafe.exception.ErrorCode.TOKEN_IS_EXPIRED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.project.yozmcafe.exception.TokenException;

class JwtTokenProviderTest {

    private final String testKey = "dsfjiaslejfilasjelfijsleifjliesajlif";
    private final JwtTokenProvider provider = new JwtTokenProvider(
            testKey,
            3600000,
            10210401);

    @Test
    @DisplayName("액세스 토큰 생성 테스트")
    void createAccessToken() {
        //given
        final String token = provider.createAccessFrom("멤버 아이디");

        //when, then
        assertDoesNotThrow(() -> provider.validate(token));
    }

    @Test
    @DisplayName("리프레시 토큰 생성 테스트")
    void createRefreshToken() {
        //given
        final String token = provider.createRefresh();

        //when, then
        assertDoesNotThrow(() -> provider.validate(token));
    }

    @Test
    @DisplayName("액세스 토큰에서 데이터 가져오기")
    void getPayload() {
        //given
        final String token = provider.createAccessFrom("연어");

        //when
        final String payload = provider.getMemberId(token);

        //then
        assertThat(payload).isEqualTo("연어");
    }

    @Test
    @DisplayName("만료된 액세스 토큰에서 데이터 가져오기")
    void getPayload2() {
        //given
        final JwtTokenProvider provider = new JwtTokenProvider(testKey, 1, 10210401);
        final String token = provider.createAccessFrom("연어");

        //when
        final String payload = provider.getMemberId(token);

        //then
        assertSoftly(softAssertions -> {
            assertThat(payload).isEqualTo("연어");
            assertThatThrownBy(() -> provider.validate(token))
                    .isInstanceOf(TokenException.class)
                    .hasMessage(TOKEN_IS_EXPIRED.getMessage());
        });
    }

    @Test
    @DisplayName("만료된 액세스 토큰으로 토큰 재생성")
    void refreshAccessToken() {
        //given
        final JwtTokenProvider provider = new JwtTokenProvider(testKey, 1, 10210401);
        final String access = provider.createAccessFrom("연어");
        final String refresh = provider.createRefresh();

        //when
        final String refreshedAccessToken = provider.refreshAccessToken(access, refresh);

        //then
        assertSoftly(softAssertions -> {
            assertThatThrownBy(() -> provider.validate(access))
                    .isInstanceOf(TokenException.class)
                    .hasMessage(TOKEN_IS_EXPIRED.getMessage());
            assertThat(provider.getMemberId(access))
                    .isEqualTo(provider.getMemberId(refreshedAccessToken));
        });
    }

    @Test
    @DisplayName("만료되지 않은 액세스 토큰으로 토큰 재생성")
    void refreshAccessToken2() {
        //given
        final String access = provider.createAccessFrom("연어");
        final String refresh = provider.createRefresh();

        //when
        final String refreshedAccessToken = provider.refreshAccessToken(access, refresh);

        //then
        assertSoftly(softAssertions -> {
            assertDoesNotThrow(() -> provider.validate(access));
            assertThat(provider.getMemberId(access))
                    .isEqualTo(provider.getMemberId(refreshedAccessToken));
        });
    }
}
