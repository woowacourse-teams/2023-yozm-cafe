package com.project.yozmcafe.service;

import com.project.yozmcafe.controller.dto.cafe.LikedCafeResponse;
import com.project.yozmcafe.controller.dto.cafe.LikedCafeThumbnailResponse;
import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.domain.cafe.CafeRepository;
import com.project.yozmcafe.domain.cafe.LikedCafe;
import com.project.yozmcafe.domain.member.Member;
import com.project.yozmcafe.domain.member.MemberRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class LikedCafeService {

    private final CafeRepository cafeRepository;
    private final MemberRepository memberRepository;
    private final MemberService memberService;

    public LikedCafeService(final CafeRepository cafeRepository, final MemberRepository memberRepository,
                            final MemberService memberService) {
        this.cafeRepository = cafeRepository;
        this.memberRepository = memberRepository;
        this.memberService = memberService;
    }

    public List<LikedCafeThumbnailResponse> findLikedCafeThumbnailsByMemberId(final String memberId, final Pageable pageable) {
        final Member member = memberService.findMemberByIdOrElseThrow(memberId);

        final List<LikedCafe> likedCafes = getLikedCafes(pageable, member);

        return likedCafes.stream()
                .map(LikedCafeThumbnailResponse::from)
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
                .map(cafe -> {
                    cafe.getImages().getUrls().size();
                    return LikedCafeResponse.from(cafe);
                })
                .toList();
    }

    @Transactional
    public void updateLike(final String memberId, final long cafeId, final boolean isLiked) {
        final Member member = memberService.findMemberByIdOrElseThrow(memberId);

        final Cafe cafe = cafeRepository.findById(cafeId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 카페가 존재하지 않습니다."));

        member.updateLikedCafesBy(cafe, isLiked);
    }
}
