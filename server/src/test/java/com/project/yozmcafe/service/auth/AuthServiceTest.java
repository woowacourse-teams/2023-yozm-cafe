package com.project.yozmcafe.service.auth;

import com.project.yozmcafe.controller.auth.MemberInfo;
import com.project.yozmcafe.controller.auth.OAuthProvider;
import com.project.yozmcafe.controller.dto.AuthorizationUrlDto;
import com.project.yozmcafe.domain.member.Member;
import com.project.yozmcafe.domain.member.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
@Transactional
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

    @Test
    @DisplayName("Provider 인증 Url을 받는다.")
    void getAuthorizationUri() {
        //when
        final List<AuthorizationUrlDto> authorizationUrls = authService.getAuthorizationUrls();

        //then
        for (AuthorizationUrlDto provider : authorizationUrls) {
            assertThat(provider.authorizationUrl())
                    .contains("response_type", "redirect_uri", "client_id", "scope");
        }
    }
}
