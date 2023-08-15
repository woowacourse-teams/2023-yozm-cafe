package com.project.yozmcafe.service;

import com.project.yozmcafe.controller.dto.cafe.CafeRankResponse;
import com.project.yozmcafe.controller.dto.cafe.CafeResponse;
import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.domain.cafe.CafeRepository;
import com.project.yozmcafe.domain.cafe.UnViewedCafe;
import com.project.yozmcafe.domain.member.Member;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CafeService {

    private final CafeRepository cafeRepository;
    private final UnViewedCafeService unViewedCafeService;
    private final CafeRankGenerator cafeRankGenerator;

    public CafeService(final CafeRepository cafeRepository, final UnViewedCafeService unViewedCafeService, final CafeRankGenerator cafeRankGenerator) {
        this.cafeRepository = cafeRepository;
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
        final List<Cafe> foundCafes = cafeRepository.findCafesByLikeCount(pageable);

        return foundCafes.stream()
                .map(cafe -> CafeRankResponse.of(cafeRankGenerator.makeRank(foundCafes.indexOf(cafe), pageable), cafe))
                .toList();
    }

    @Transactional
    public List<CafeResponse> getCafesForLoginMember(final Member member, final int size) {
        final List<UnViewedCafe> cafes = member.getNextUnViewedCafes(size);
        unViewedCafeService.refillWhenUnViewedCafesSizeUnderTwenty(member);

        return cafes.stream()
                .map(UnViewedCafe::getCafe)
                .map(cafe -> CafeResponse.fromLoggedInUser(cafe, member.isLike(cafe)))
                .toList();
    }
}
