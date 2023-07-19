package com.project.yozmcafe.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.yozmcafe.controller.dto.cafe.CafeDto;
import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.domain.cafe.CafeRepository;
import com.project.yozmcafe.domain.member.Member;

@Service
@Transactional
public class CafeService {

    private final CafeRepository cafes;

    public CafeService(final CafeRepository cafes) {
        this.cafes = cafes;
    }

    public List<CafeDto> getCafesForUnLoginMember(final Pageable pageable) {
        final List<Cafe> cafes = this.cafes.findSliceBy(pageable).getContent();
        return cafes.stream()
                .map(CafeDto::of)
                .toList();
    }

    // TODO: 2023/07/18 인증 기능 개발 후 적용
    public List<CafeDto> getCafesForLoginMember(final Pageable pageable, final Member member) {
        final List<Cafe> cafes = this.cafes.findUnViewedCafesByMember(pageable, member.getId()).getContent();
        return cafes.stream()
                .map(CafeDto::of)
                .toList();
    }
}
