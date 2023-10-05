package com.project.yozmcafe.service;

import com.project.yozmcafe.controller.dto.cafe.CafeRankResponse;
import com.project.yozmcafe.controller.dto.cafe.CafeResponse;
import com.project.yozmcafe.controller.dto.cafe.CafeSearchRequest;
import com.project.yozmcafe.controller.dto.cafe.CafeSearchResponse;
import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.domain.cafe.CafeRepository;
import com.project.yozmcafe.domain.cafe.UnViewedCafe;
import com.project.yozmcafe.domain.member.Member;
import com.project.yozmcafe.domain.member.MemberRepository;
import com.project.yozmcafe.exception.BadRequestException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.project.yozmcafe.exception.ErrorCode.NOT_EXISTED_CAFE;
import static com.project.yozmcafe.exception.ErrorCode.NOT_EXISTED_MEMBER;
import static io.micrometer.common.util.StringUtils.isBlank;

@Service
@Transactional(readOnly = true)
public class CafeService {

    private final CafeRepository cafeRepository;
    private final MemberRepository memberRepository;
    private final UnViewedCafeService unViewedCafeService;

    public CafeService(final CafeRepository cafeRepository, final MemberRepository memberRepository,
                       final UnViewedCafeService unViewedCafeService) {
        this.cafeRepository = cafeRepository;
        this.memberRepository = memberRepository;
        this.unViewedCafeService = unViewedCafeService;
    }

    public List<CafeResponse> getCafesForUnLoginMember(final Pageable pageable) {
        final List<Cafe> foundCafes = cafeRepository.findSliceBy(pageable).getContent();

        return foundCafes.stream()
                .map(CafeResponse::fromUnLoggedInUser)
                .toList();
    }

    public List<CafeRankResponse> getCafesOrderByLikeCount(final Pageable pageable) {
        final List<Long> ids = cafeRepository.findCafeIdsOrderByLikeCount(pageable);
        final List<Cafe> cafes = cafeRepository.findCafesByIdsOrderByLikeCount(ids);

        final List<CafeRankResponse> response = new ArrayList<>();

        int rank = (int) pageable.getOffset();
        for (final Cafe cafe : cafes) {
            response.add(CafeRankResponse.of(++rank, cafe));
        }

        return response;
    }

    @Transactional
    public List<CafeResponse> getCafesForLoginMember(final String memberId, final int size) {
        final Member member = getMemberByIdOrThrow(memberId);
        final List<UnViewedCafe> cafes = member.getNextUnViewedCafes(size);
        unViewedCafeService.refillWhenUnViewedCafesSizeUnderTwenty(member);

        return cafes.stream()
                .map(UnViewedCafe::getCafe)
                .map(cafe -> CafeResponse.fromLoggedInUser(cafe, member.isLike(cafe)))
                .toList();
    }

    private Member getMemberByIdOrThrow(final String memberId) {
        return memberRepository.findWithUnViewedCafesById(memberId)
                .orElseThrow(() -> new BadRequestException(NOT_EXISTED_MEMBER));
    }

    public CafeResponse getCafeByIdOrThrow(final long cafeId) {
        final Cafe foundCafe = cafeRepository.findById(cafeId)
                .orElseThrow(() -> new BadRequestException(NOT_EXISTED_CAFE));

        return CafeResponse.fromUnLoggedInUser(foundCafe);
    }

    public List<CafeSearchResponse> getCafesBySearch(final CafeSearchRequest searchRequest) {
        final List<Cafe> cafes = searchWith(searchRequest);

        return cafes.stream()
                .map(CafeSearchResponse::from)
                .toList();
    }

    private List<Cafe> searchWith(final CafeSearchRequest cafeSearchRequest) {
        if (isBlank(cafeSearchRequest.menu())) {
            return cafeRepository.findAllBy(cafeSearchRequest.cafeName(), cafeSearchRequest.address());
        }
        return cafeRepository.findAllBy(cafeSearchRequest.cafeName(), cafeSearchRequest.menu(), cafeSearchRequest.address());
    }
}
