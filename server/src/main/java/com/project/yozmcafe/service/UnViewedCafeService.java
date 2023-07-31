package com.project.yozmcafe.service;

import com.project.yozmcafe.domain.cafe.CafeRepository;
import com.project.yozmcafe.domain.cafe.UnViewedCafe;
import com.project.yozmcafe.domain.cafe.UnViewedCafeRepository;
import com.project.yozmcafe.domain.member.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UnViewedCafeService {

    private final UnViewedCafeRepository unViewedCafeRepository;
    private final CafeRepository cafeRepository;

    public UnViewedCafeService(final UnViewedCafeRepository unViewedCafeRepository, final CafeRepository cafeRepository) {
        this.unViewedCafeRepository = unViewedCafeRepository;
        this.cafeRepository = cafeRepository;
    }

    @Transactional
    public void removeUnViewedCafe(final Member member, final long cafeId) {
        final Optional<UnViewedCafe> unViewedCafe = unViewedCafeRepository.findByMemberIdAndCafeId(member.getId(), cafeId);
        if (unViewedCafe.isEmpty()) {
            return;
        }

        member.removeUnViewedCafe(unViewedCafe.get());
        if (member.isEmptyUnViewedCafe()) {
            member.addUnViewedCafesWithShuffle(cafeRepository.findAll());
        }
    }
}
