package com.project.yozmcafe.service;

import com.project.yozmcafe.domain.CafeShuffleStrategy;
import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.domain.cafe.CafeRepository;
import com.project.yozmcafe.domain.member.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class UnViewedCafeService {

    private static final int DEFAULT_SIZE_CONDITION = 20;

    private final CafeRepository cafeRepository;
    private final CafeShuffleStrategy shuffleStrategy;

    public UnViewedCafeService(final CafeRepository cafeRepository,
                               final CafeShuffleStrategy shuffleStrategy) {
        this.cafeRepository = cafeRepository;
        this.shuffleStrategy = shuffleStrategy;
    }

    @Transactional
    public void removeUnViewedCafeByCafeId(final Member member, final long cafeId) {
        member.removeUnViewedCafe(cafeId);
        refillWhenUnViewedCafesSizeUnderTwenty(member);
    }

    @Transactional
    public void refillWhenUnViewedCafesSizeUnderTwenty(final Member member) {
        if (member.isUnViewedCafesSizeUnder(DEFAULT_SIZE_CONDITION)) {
            final List<Cafe> shuffledCafes = shuffleStrategy.shuffle(cafeRepository.findAll());
            member.addUnViewedCafes(shuffledCafes);
        }
    }
}
