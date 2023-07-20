package com.project.yozmcafe.controller;

import com.project.yozmcafe.controller.auth.OAuthProvider;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

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
        Assertions.assertThatThrownBy(() -> converter.convert(nothing))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("잘못된 OAuth 로그인입니다.");
    }
}
