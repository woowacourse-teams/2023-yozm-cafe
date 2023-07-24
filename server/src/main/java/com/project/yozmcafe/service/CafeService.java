package com.project.yozmcafe.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.yozmcafe.controller.dto.cafe.CafeResponse;
import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.domain.cafe.CafeRepository;
import com.project.yozmcafe.domain.member.Member;

@Service
@Transactional(readOnly = true)
public class CafeService {

    public static final boolean UN_LOGIN_USER_IS_LIKED = false;
    private final CafeRepository cafeRepository;

    public CafeService(final CafeRepository cafeRepository) {
        this.cafeRepository = cafeRepository;
    }

    public List<CafeResponse> getCafesForUnLoginMember(final Pageable pageable) {
        final List<Cafe> foundCafes = cafeRepository.findSliceBy(pageable).getContent();
        return foundCafes.stream()
                .map(cafe -> CafeResponse.of(cafe, UN_LOGIN_USER_IS_LIKED))
                .toList();
    }

    public List<CafeResponse> getCafesForLoginMember(final Pageable pageable, final Member member) {
        final List<Cafe> unViewedCafes = cafeRepository.findUnViewedCafesByMember(pageable, member.getId())
                .getContent();
        final List<CafeResponse> cafeResponses = new ArrayList<>();
        for (final Cafe cafe : unViewedCafes) {
            final boolean isLike = member.isLike(cafe);
            cafeResponses.add(CafeResponse.of(cafe, isLike));
        }
        return cafeResponses;
    }
}
