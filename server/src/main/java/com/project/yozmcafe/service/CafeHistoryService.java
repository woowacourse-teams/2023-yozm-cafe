package com.project.yozmcafe.service;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.domain.cafe.CafeRepository;
import com.project.yozmcafe.domain.cafe.UnViewedCafe;
import com.project.yozmcafe.domain.member.Member;

@Service
@Transactional(readOnly = true)
public class CafeHistoryService {

    private final CafeRepository cafeRepository;

    public CafeHistoryService(final CafeRepository cafeRepository) {
        this.cafeRepository = cafeRepository;
    }

    @Transactional
    public void removeUnViewedCafe(final Member member, final long cafeId) {
        final UnViewedCafe unViewedCafe = cafeRepository.findUnViewedCafeByMemberAndCafe(member.getId(), cafeId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 UnViewedCafe가 없습니다."));
        member.removeUnViewedCafe(unViewedCafe);

        if (member.isEmptyUnViewedCafe()) {
            updateUnViewedCafes(member);
        }
    }

    private void updateUnViewedCafes(final Member member) {
        final List<Cafe> allCafes = cafeRepository.findAll();
        Collections.shuffle(allCafes);
        member.addUnViewedCafes(allCafes);
    }
}
