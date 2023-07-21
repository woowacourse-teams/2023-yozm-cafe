package com.project.yozmcafe.service.auth;

import com.project.yozmcafe.controller.auth.MemberInfo;
import com.project.yozmcafe.controller.auth.OAuthProvider;
import com.project.yozmcafe.domain.member.Member;
import com.project.yozmcafe.domain.member.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
class AuthServiceTest {

    @SpyBean
    private GoogleOAuthClient googleOAuthClient;
    @SpyBean
    private KakaoOAuthClient kakaoOAuthClient;
    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private AuthService authService;

    @Test
    @DisplayName("회원가입된 상태의 유저로 createAccessToken 호출하면 멤버를 새로 저장하지 않는다")
    void createAccessToken() {
        //given
        doReturn(new MemberInfo("1234", "", ""))
                .when(googleOAuthClient).getUserInfo(anyString());
        given(jwtTokenProvider.createAccessFrom(anyString())).willReturn("토큰");
        memberRepository.save(new Member("1234", "연어", "image"));

        //when
        authService.createAccessToken("135", OAuthProvider.GOOGLE);

        //then
        assertThat(memberRepository.findAll()).hasSize(1);
    }

    @Test
    @DisplayName("회원가입된 상태의 유저가 아닐 때 createAccessToken 호출하면 멤버를 새로 저장한다")
    void createAccessToken2() {
        //given
        doReturn(new MemberInfo("1234", "", ""))
                .when(googleOAuthClient).getUserInfo(anyString());
        given(jwtTokenProvider.createAccessFrom(anyString())).willReturn("토큰");

        //when
        authService.createAccessToken("135", OAuthProvider.GOOGLE);

        //then
        assertThat(memberRepository.findAll()).hasSize(1);
    }

    @ParameterizedTest(name = "{0} 인증 URI 조회")
    @ValueSource(strings = {"google", "kakao"})
    @DisplayName("Provider 별 인증 Uri를 받는다.")
    void getAuthorizationUri(String provider) {
        //given
        final OAuthProvider oAuthProvider = OAuthProvider.from(provider);
        //when
        final String authorizationUri = authService.getAuthorizationUri(oAuthProvider);
        //then
        assertThat(authorizationUri).contains("response_type", "redirect_uri", "client_id", "scope");
    }
}
