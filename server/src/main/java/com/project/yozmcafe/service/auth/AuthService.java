package com.project.yozmcafe.service.auth;

import com.project.yozmcafe.controller.auth.OAuthProvider;
import com.project.yozmcafe.controller.dto.AuthorizationUrlDto;
import com.project.yozmcafe.controller.dto.TokenResponse;
import com.project.yozmcafe.domain.CafeShuffleStrategy;
import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.domain.cafe.CafeRepository;
import com.project.yozmcafe.domain.member.Member;
import com.project.yozmcafe.domain.member.MemberInfo;
import com.project.yozmcafe.domain.member.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    private final CafeRepository cafeRepository;
    private final CafeShuffleStrategy cafeShuffleStrategy;

    public AuthService(final JwtTokenProvider jwtTokenProvider, final MemberRepository memberRepository,
                       final CafeRepository cafeRepository, final CafeShuffleStrategy cafeShuffleStrategy) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberRepository = memberRepository;
        this.cafeRepository = cafeRepository;
        this.cafeShuffleStrategy = cafeShuffleStrategy;
    }

    @Transactional
    public TokenResponse createAccessToken(final String code, final OAuthProvider provider) {
        final MemberInfo memberInfo = provider.getUserInfo(code);

        final Member member = memberRepository.findById(memberInfo.openId())
                .orElseGet(() -> saveNewMemberWithAllCafes(memberInfo));

        return new TokenResponse(jwtTokenProvider.createAccessFrom(member.getId()));
    }

    private Member saveNewMemberWithAllCafes(final MemberInfo memberInfo) {
        final Member member = memberRepository.save(memberInfo.toMember());
        final List<Cafe> allCafes = cafeShuffleStrategy.shuffle(cafeRepository.findAll());
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

    public List<AuthorizationUrlDto> getAuthorizationUrls() {
        return Arrays.stream(OAuthProvider.values())
                .map(provider -> new AuthorizationUrlDto(provider.getProviderName(), provider.getAuthorizationUrl()))
                .collect(Collectors.toList());
    }
}
