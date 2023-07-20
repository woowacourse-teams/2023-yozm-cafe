package com.project.yozmcafe.service;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.domain.cafe.CafeRepository;
import com.project.yozmcafe.domain.cafe.UnViewedCafe;
import com.project.yozmcafe.domain.cafe.UnViewedCafeRepository;
import com.project.yozmcafe.domain.member.Member;

@Service
@Transactional(readOnly = true)
public class CafeHistoryService {

    private final CafeRepository cafeRepository;
    private final UnViewedCafeRepository unViewedCafeRepository;

    public CafeHistoryService(final CafeRepository cafeRepository,
                              final UnViewedCafeRepository unViewedCafeRepository) {
        this.cafeRepository = cafeRepository;
        this.unViewedCafeRepository = unViewedCafeRepository;
    }

    @Transactional
    public void removeUnViewedCafe(final Member member, final long cafeId) {
        final Cafe cafe = cafeRepository.findById(cafeId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카페입니다."));
        member.removeUnViewedCafe(cafe);

        if (member.isEmptyUnViewedCafe()) {
            updateUnViewedCafes(member);
        }
    }

    private void updateUnViewedCafes(final Member member) {
        final List<Cafe> allCafes = cafeRepository.findAll();
        Collections.shuffle(allCafes);
        final List<UnViewedCafe> allUnViewedCafes = allCafes.stream()
                .map(savedCafe -> new UnViewedCafe(savedCafe, member))
                .toList();
        this.unViewedCafeRepository.saveAll(allUnViewedCafes);
        member.addUnViewedCafes(allUnViewedCafes);
    }
}
