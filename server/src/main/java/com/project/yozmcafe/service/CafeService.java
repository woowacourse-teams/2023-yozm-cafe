package com.project.yozmcafe.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.yozmcafe.controller.dto.cafe.CafeDto;
import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.domain.cafe.CafeRepository;
import com.project.yozmcafe.domain.cafe.Cafes;

@Service
@Transactional
public class CafeService {

    private final CafeRepository cafes;

    public CafeService(final CafeRepository cafes) {
        this.cafes = cafes;
    }

    public List<CafeDto> pickRandomCafesForUnLoginMember() {
        final Cafes allCafes = new Cafes(cafes.findAll());
        final List<Cafe> randomCafes = allCafes.pickRandomCafe();
        return randomCafes.stream()
                .map(CafeDto::of)
                .toList();
    }
}
