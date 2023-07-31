package com.project.yozmcafe.service;

import com.project.yozmcafe.controller.dto.LikedCafeResponse;
import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.domain.cafe.CafeRepository;
import com.project.yozmcafe.domain.cafe.LikedCafe;
import com.project.yozmcafe.domain.cafe.LikedCafeRepository;
import com.project.yozmcafe.domain.member.Member;
import com.project.yozmcafe.domain.member.MemberRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class LikedCafeService {

    private final LikedCafeRepository likedCafeRepository;
    private final MemberRepository memberRepository;
    private final CafeRepository cafeRepository;

    public LikedCafeService(final LikedCafeRepository likedCafeRepository, final MemberRepository memberRepository,
                            final CafeRepository cafeRepository) {
        this.likedCafeRepository = likedCafeRepository;
        this.memberRepository = memberRepository;
        this.cafeRepository = cafeRepository;
    }

    public List<LikedCafeResponse> findLikedCafesById(final String memberId, final Pageable pageable) {
        final Member member = findMemberByIdOrElseThrow(memberId);

        final Slice<LikedCafe> likedCafes = likedCafeRepository.findLikedCafesByMemberIdOrderByIdDesc(member.getId(), pageable);

        return likedCafes.stream()
                .map(LikedCafeResponse::from)
                .toList();
    }

    private Member findMemberByIdOrElseThrow(final String memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 회원이 존재하지 않습니다."));
    }

    @Transactional
    public void updateLike(final Member member, final long cafeId, final boolean isLiked) {
        final Cafe cafe = cafeRepository.findById(cafeId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 카페가 존재하지 않습니다."));

        member.updateLikedCafesBy(cafe, isLiked);
    }
}
