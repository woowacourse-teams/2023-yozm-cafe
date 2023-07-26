package com.project.yozmcafe.service.auth;

import com.project.yozmcafe.controller.auth.MemberInfo;
import com.project.yozmcafe.controller.auth.OAuthProvider;
import com.project.yozmcafe.controller.dto.TokenResponse;
import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.domain.cafe.CafeRepository;
import com.project.yozmcafe.domain.member.Member;
import com.project.yozmcafe.domain.member.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    private final CafeRepository cafeRepository;

    public AuthService(final JwtTokenProvider jwtTokenProvider, final MemberRepository memberRepository, final CafeRepository cafeRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberRepository = memberRepository;
        this.cafeRepository = cafeRepository;
    }

    @Transactional
    public TokenResponse createAccessToken(final String code, final OAuthProvider provider) {
        final MemberInfo memberInfo = provider.getUserInfo(code);

        final Member member = memberRepository.findById(memberInfo.openId())
                .orElse(saveNewMember(memberInfo));

        return new TokenResponse(jwtTokenProvider.createAccessFrom(member.getId()));
    }

    private Member saveNewMember(final MemberInfo memberInfo) {
        final Member member = memberRepository.save(memberInfo.toMember());
        final List<Cafe> allCafes = cafeRepository.findAll();
        member.addUnViewedCafes(allCafes);
        return member;
    }

    public TokenResponse createRefreshToken() {
        return new TokenResponse(jwtTokenProvider.createRefresh());
    }

    public TokenResponse refreshAccessToken(final String access, final String refresh) {
        final String token = jwtTokenProvider.refreshAccessToken(access, refresh);
        return new TokenResponse(token);
    }

    public String getAuthorizationUri(final OAuthProvider provider) {
        return provider.getAuthorizationUrl();
    }
}
