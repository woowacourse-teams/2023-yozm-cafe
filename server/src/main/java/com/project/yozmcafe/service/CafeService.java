package com.project.yozmcafe.service;

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

    private final CafeRepository cafes;

    public CafeService(final CafeRepository cafes) {
        this.cafes = cafes;
    }

    public List<CafeResponse> getCafesForUnLoginMember(final Pageable pageable) {
        final List<Cafe> foundCafes = cafes.findSliceBy(pageable).getContent();
        return foundCafes.stream()
                .map(CafeResponse::of)
                .toList();
    }

    public List<CafeResponse> getCafesForLoginMember(final Pageable pageable, final Member member) {
        final List<Cafe> unViewedCafes = cafes.findUnViewedCafesByMember(pageable, member.getId()).getContent();
        return unViewedCafes.stream()
                .map(CafeResponse::of)
                .toList();
    }
}
