package com.project.yozmcafe.service;

import com.project.yozmcafe.controller.dto.LikedCafeResponse;
import com.project.yozmcafe.controller.dto.MemberResponse;
import com.project.yozmcafe.domain.cafe.LikedCafe;
import com.project.yozmcafe.domain.member.Member;
import com.project.yozmcafe.domain.member.MemberRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(final MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public MemberResponse findById(final String memberId) {
        final Member member = findMemberByIdOrElseThrow(memberId);

        return MemberResponse.from(member);
    }

    public List<LikedCafeResponse> findLikedCafesById(final String memberId, Pageable pageable) {
        final Member member = findMemberByIdOrElseThrow(memberId);

        final Slice<LikedCafe> likedCafes = memberRepository.findLikedCafesByMemberId(member.getId(), pageable);

        return likedCafes.stream()
                .map(LikedCafeResponse::from)
                .toList();
    }

    private Member findMemberByIdOrElseThrow(final String memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 회원이 존재하지 않습니다."));
    }
}
