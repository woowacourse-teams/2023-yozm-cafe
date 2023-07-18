package com.project.yozmcafe.service.auth;

import com.project.yozmcafe.controller.auth.MemberInfo;
import com.project.yozmcafe.controller.auth.OAuthProvider;
import com.project.yozmcafe.controller.dto.TokenResponse;
import com.project.yozmcafe.domain.member.Member;
import com.project.yozmcafe.domain.member.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AuthService {
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    public AuthService(final JwtTokenProvider jwtTokenProvider, final MemberRepository memberRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public TokenResponse login(final String code, final String providerName) {
        final OAuthProvider provider = OAuthProvider.from(providerName);
        final MemberInfo memberInfo = provider.getUserInfo(code);

        final Member member = memberRepository.findById(memberInfo.openId())
                .orElseGet(() -> memberRepository.save(memberInfo.toMember()));

        return jwtTokenProvider.createToken(member.getId());
    }
}
