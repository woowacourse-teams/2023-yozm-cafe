package com.project.yozmcafe.controller;

import static com.project.yozmcafe.exception.ErrorCode.NOT_EXISTED_OAUTH_PROVIDER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.project.yozmcafe.controller.auth.OAuthProvider;
import com.project.yozmcafe.exception.BadRequestException;

class StringToOAuthProviderConverterTest {

    private final StringToOAuthProviderConverter converter = new StringToOAuthProviderConverter();

    @Test
    @DisplayName("String에서 OAuthProvider로 변환")
    void convert() {
        //given
        final String kakao = "kakao";

        //when
        final OAuthProvider provider = converter.convert(kakao);

        //then
        assertThat(provider).isEqualTo(OAuthProvider.KAKAO);
    }

    @Test
    @DisplayName("존재하지 않는 OAuthProvider로 변환하면 예외가 발생한다")
    void convert_fail() {
        //given
        final String nothing = "nothing";

        //when, then
        assertThatThrownBy(() -> converter.convert(nothing))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(NOT_EXISTED_OAUTH_PROVIDER.getMessage());
    }
}
