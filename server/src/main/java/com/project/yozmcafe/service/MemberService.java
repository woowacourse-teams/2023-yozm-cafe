package com.project.yozmcafe.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.yozmcafe.controller.dto.MemberResponse;
import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.domain.cafe.CafeRepository;
import com.project.yozmcafe.domain.member.Member;
import com.project.yozmcafe.domain.member.MemberRepository;

@Transactional(readOnly = true)
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final CafeRepository cafeRepository;

    public MemberService(final MemberRepository memberRepository, final CafeRepository cafeRepository) {
        this.memberRepository = memberRepository;
        this.cafeRepository = cafeRepository;
    }

    public MemberResponse findById(final String id) {
        final Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 회원이 존재하지 않습니다."));

        return MemberResponse.from(member);
    }

    @Transactional
    public void updateLike(final Member member, final long cafeId, final boolean isLiked) {
        final Cafe cafe = cafeRepository.findById(cafeId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 카페가 존재하지 않습니다."));
        member.updateLikedCafesBy(cafe, isLiked);
    }
}
