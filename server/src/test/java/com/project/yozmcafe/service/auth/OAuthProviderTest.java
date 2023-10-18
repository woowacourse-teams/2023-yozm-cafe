package com.project.yozmcafe.service.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import com.project.yozmcafe.controller.auth.OAuthProvider;
import com.project.yozmcafe.service.BaseServiceTest;

class OAuthProviderTest extends BaseServiceTest {

    @Test
    @DisplayName("OAuthProvider와 OAuthClient가 매핑되지 않으면 실패")
    void setUpProvider() {
        assertDoesNotThrow(() -> OAuthProvider.GOOGLE);
    }

    @ParameterizedTest(name = "{0} 인증 Uri를 받아올 수 있다.")
    @EnumSource(value = OAuthProvider.class)
    @DisplayName("인증 Uri 를 받아올 수 있다.")
    void getAuthorizationUrl(OAuthProvider oAuthProvider) {
        assertThat(oAuthProvider.getAuthorizationUrl()).contains("response_type", "redirect_uri", "client_id", "scope");
    }

    @ParameterizedTest
    @EnumSource(value = OAuthProvider.class)
    @DisplayName("providerName을 소문자로 반환한다.")
    void getProviderName(final OAuthProvider oAuthProvider) {
        //when
        final String providerName = oAuthProvider.getProviderName();

        //then
        assertSoftly(softAssertions -> {
            assertThat(providerName).isLowerCase();
            assertThat(providerName).isEqualToIgnoringCase(oAuthProvider.name());
        });
    }
}
