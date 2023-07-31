package com.project.yozmcafe.service;

import static com.project.yozmcafe.exception.ErrorCode.NOT_EXISTED_CAFE;
import static com.project.yozmcafe.exception.ErrorCode.NOT_EXISTED_MEMBER;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.yozmcafe.controller.dto.LikedCafeResponse;
import com.project.yozmcafe.controller.dto.MemberResponse;
import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.domain.cafe.CafeRepository;
import com.project.yozmcafe.domain.cafe.LikedCafe;
import com.project.yozmcafe.domain.member.Member;
import com.project.yozmcafe.domain.member.MemberRepository;
import com.project.yozmcafe.exception.BadRequestException;

@Transactional(readOnly = true)
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final CafeRepository cafeRepository;

    public MemberService(final MemberRepository memberRepository, final CafeRepository cafeRepository) {
        this.memberRepository = memberRepository;
        this.cafeRepository = cafeRepository;
    }

    public MemberResponse findById(final String memberId) {
        final Member member = findMemberByIdOrElseThrow(memberId);

        return MemberResponse.from(member);
    }

    public List<LikedCafeResponse> findLikedCafesById(final String memberId, final Pageable pageable) {
        final Member member = findMemberByIdOrElseThrow(memberId);

        final Slice<LikedCafe> likedCafes = memberRepository.findLikedCafesByMemberId(member.getId(), pageable);

        return likedCafes.stream()
                .map(LikedCafeResponse::from)
                .toList();
    }

    private Member findMemberByIdOrElseThrow(final String memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new BadRequestException(NOT_EXISTED_MEMBER));
    }

    @Transactional
    public void updateLike(final Member member, final long cafeId, final boolean isLiked) {
        final Cafe cafe = cafeRepository.findById(cafeId)
                .orElseThrow(() -> new BadRequestException(NOT_EXISTED_CAFE));
        member.updateLikedCafesBy(cafe, isLiked);
    }
}
