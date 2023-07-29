package com.project.yozmcafe.service;

import com.project.yozmcafe.controller.dto.cafe.CafeRequest;
import com.project.yozmcafe.controller.dto.cafe.CafeResponse;
import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.domain.cafe.CafeRepository;
import com.project.yozmcafe.domain.cafe.UnViewedCafeRepository;
import com.project.yozmcafe.domain.member.Member;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class CafeService {

    private final CafeRepository cafeRepository;
    private final UnViewedCafeRepository unViewedCafeRepository;

    public CafeService(final CafeRepository cafeRepository, final UnViewedCafeRepository unViewedCafeRepository) {
        this.cafeRepository = cafeRepository;
        this.unViewedCafeRepository = unViewedCafeRepository;
    }

    public List<CafeResponse> getCafesForUnLoginMember(final Pageable pageable) {
        final List<Cafe> foundCafes = cafeRepository.findSliceBy(pageable).getContent();

        return foundCafes.stream()
                .map(CafeResponse::fromUnLoggedInUser)
                .toList();
    }

    public List<CafeResponse> getCafesForLoginMember(final Pageable pageable, final Member member) {
        final PageRequest randomPageRequest = getRandomPageRequestByMember(member, pageable.getPageSize());
        final List<Cafe> randomCafes = unViewedCafeRepository.findUnViewedCafesByMember(member.getId(), randomPageRequest);

        return randomCafes.stream()
                .map(cafe -> CafeResponse.fromLoggedInUser(cafe, member.isLike(cafe)))
                .toList();
    }

    private PageRequest getRandomPageRequestByMember(final Member member, final int pageSize) {
        Long unViewedCafeCount = unViewedCafeRepository.countByMemberId(member.getId());

        final int pageIdx = new SecureRandom().nextInt((int) Math.ceil((double) unViewedCafeCount / pageSize));
        return PageRequest.of(pageIdx, pageSize);
    }

    @Transactional
    public Cafe saveCafe(CafeRequest cafeRequest) {
        return cafeRepository.save(cafeRequest.toCafe());
    }
}
