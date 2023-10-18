package com.project.yozmcafe.service;

import com.project.yozmcafe.controller.dto.cafe.CafeThumbnailResponse;
import com.project.yozmcafe.controller.dto.cafe.LikedCafeResponse;
import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.domain.cafe.CafeRepository;
import com.project.yozmcafe.domain.cafe.LikedCafe;
import com.project.yozmcafe.domain.member.Member;
import com.project.yozmcafe.exception.BadRequestException;
import com.project.yozmcafe.exception.ErrorCode;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class LikedCafeService {

    private final CafeRepository cafeRepository;
    private final MemberService memberService;

    public LikedCafeService(final CafeRepository cafeRepository, final MemberService memberService) {
        this.cafeRepository = cafeRepository;
        this.memberService = memberService;
    }

    public List<CafeThumbnailResponse> findLikedCafeThumbnailsByMemberId(final String memberId, final Pageable pageable) {
        final Member member = memberService.findMemberByIdOrElseThrow(memberId);

        final List<Cafe> likedCafes = member.getLikedCafes((int) pageable.getOffset(), pageable.getPageSize());

        return likedCafes.stream()
                .map(CafeThumbnailResponse::from)
                .toList();
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
    public void updateLike(final String memberId, final long cafeId, final boolean isLiked) {
        final Member member = memberService.findMemberByIdOrElseThrow(memberId);

        final Cafe cafe = cafeRepository.findById(cafeId)
                .orElseThrow(() -> new BadRequestException(ErrorCode.NOT_EXISTED_CAFE));

        member.updateLikedCafesBy(cafe, isLiked);
    }
}
