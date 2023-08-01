package com.project.yozmcafe.service;

import com.project.yozmcafe.domain.CafeShuffleStrategy;
import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.domain.cafe.CafeRepository;
import com.project.yozmcafe.domain.cafe.UnViewedCafeRepository;
import com.project.yozmcafe.domain.member.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class UnViewedCafeService {

    private final UnViewedCafeRepository unViewedCafeRepository;
    private final CafeRepository cafeRepository;
    private final CafeShuffleStrategy shuffleStrategy;

    public UnViewedCafeService(final UnViewedCafeRepository unViewedCafeRepository,
                               final CafeRepository cafeRepository,
                               final CafeShuffleStrategy shuffleStrategy) {
        this.unViewedCafeRepository = unViewedCafeRepository;
        this.cafeRepository = cafeRepository;
        this.shuffleStrategy = shuffleStrategy;
    }

    @Transactional
    public void removeUnViewedCafe(final Member member, final long cafeId) {
        unViewedCafeRepository.findByMemberIdAndCafeId(member.getId(), cafeId)
                .ifPresent(member::removeUnViewedCafe);

        refillWhenUnViewedCafesSizeUnderTen(member);
    }

    @Transactional
    public void refillWhenUnViewedCafesSizeUnderTen(final Member member) {
        if (member.isUnViewedCafesSizeUnderTen()) {
            final List<Cafe> shuffledCafes = shuffleStrategy.shuffle(cafeRepository.findAll());
            member.addUnViewedCafes(shuffledCafes);
        }
    }
}
