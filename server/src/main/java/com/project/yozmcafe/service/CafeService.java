package com.project.yozmcafe.service;

import com.project.yozmcafe.controller.dto.cafe.CafeRankResponse;
import com.project.yozmcafe.controller.dto.cafe.CafeResponse;
import com.project.yozmcafe.domain.CafeRankGenerator;
import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.domain.cafe.CafeRepository;
import com.project.yozmcafe.domain.cafe.UnViewedCafe;
import com.project.yozmcafe.domain.member.Member;
import com.project.yozmcafe.exception.BadRequestException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.project.yozmcafe.exception.ErrorCode.NOT_EXISTED_CAFE;

@Service
@Transactional(readOnly = true)
public class CafeService {

    private final CafeRepository cafeRepository;
    private final MemberService memberService;
    private final UnViewedCafeService unViewedCafeService;
    private final CafeRankGenerator cafeRankGenerator;

    public CafeService(final CafeRepository cafeRepository, final MemberService memberService, final UnViewedCafeService unViewedCafeService, final CafeRankGenerator cafeRankGenerator) {
        this.cafeRepository = cafeRepository;
        this.memberService = memberService;
        this.unViewedCafeService = unViewedCafeService;
        this.cafeRankGenerator = cafeRankGenerator;
    }

    public List<CafeResponse> getCafesForUnLoginMember(final Pageable pageable) {
        final List<Cafe> foundCafes = cafeRepository.findSliceBy(pageable).getContent();

        return foundCafes.stream()
                .map(CafeResponse::fromUnLoggedInUser)
                .toList();
    }

    public List<CafeRankResponse> getCafesOrderByLikeCount(final Pageable pageable) {
        cafeRankGenerator.validatePage(pageable);

        final List<Long> ids = cafeRepository.findCafeIdsOrderByLikeCount(pageable);
        final List<Cafe> foundCafes = cafeRepository.findCafesByIdsOrderByLikeCount(ids);

        return foundCafes.stream().map(cafe -> CafeRankResponse.of(cafeRankGenerator.makeRank(foundCafes.indexOf(cafe), pageable), cafe)).toList();
    }

    @Transactional
    public List<CafeResponse> getCafesForLoginMember(final String memberId, final int size) {
        final Member member = memberService.findMemberByIdOrElseThrow(memberId);
        final List<UnViewedCafe> cafes = member.getNextUnViewedCafes(size);
        unViewedCafeService.refillWhenUnViewedCafesSizeUnderTwenty(member);

        return cafes.stream()
                .map(UnViewedCafe::getCafe)
                .map(cafe -> CafeResponse.fromLoggedInUser(cafe, member.isLike(cafe)))
                .toList();
    }

    public CafeResponse getCafeById(final long cafeId) {
        final Cafe foundCafe = cafeRepository.findById(cafeId)
                .orElseThrow(() -> new BadRequestException(NOT_EXISTED_CAFE));

        return CafeResponse.fromUnLoggedInUser(foundCafe);
    }
}
