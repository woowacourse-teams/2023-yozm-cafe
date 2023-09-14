package com.project.yozmcafe.service;

import com.project.yozmcafe.controller.dto.cafe.LikedCafeResponse;
import com.project.yozmcafe.controller.dto.cafe.CafeThumbnailResponse;
import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.domain.cafe.CafeRepository;
import com.project.yozmcafe.domain.cafe.LikedCafe;
import com.project.yozmcafe.domain.member.Member;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class LikedCafeService {

    private final CafeRepository cafeRepository;
    private final MemberService memberService;

    public LikedCafeService(final CafeRepository cafeRepository,
                            final MemberService memberService) {
        this.cafeRepository = cafeRepository;
        this.memberService = memberService;
    }

    public List<CafeThumbnailResponse> findLikedCafeThumbnailsByMemberId(final String memberId, final Pageable pageable) {
        final Member member = memberService.findMemberByIdOrElseThrow(memberId);

        final List<LikedCafe> likedCafes = getLikedCafes(pageable, member);

        return likedCafes.stream()
                .map(CafeThumbnailResponse::from)
                .toList();
    }

    private List<LikedCafe> getLikedCafes(final Pageable pageable, final Member member) {
        final int startIndex = pageable.getPageNumber() * pageable.getPageSize();
        final int endIndex = startIndex + pageable.getPageSize();

        return member.getLikedCafesSection(startIndex, endIndex);
    }

    public List<LikedCafeResponse> findLikedCafesByMemberId(final String memberId) {
        final Member member = memberService.findMemberByIdOrElseThrow(memberId);

        final List<LikedCafe> likedCafes = member.getLikedCafes();

        return likedCafes.stream()
                .map(LikedCafe::getCafe)
                .map(LikedCafeResponse::from)
                .toList();
    }

    @Transactional
    public void updateLike(final Member member, final long cafeId, final boolean isLiked) {
        final Cafe cafe = cafeRepository.findById(cafeId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 카페가 존재하지 않습니다."));

        member.updateLikedCafesBy(cafe, isLiked);
    }
}
