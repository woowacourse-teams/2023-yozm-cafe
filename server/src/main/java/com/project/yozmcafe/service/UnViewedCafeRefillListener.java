package com.project.yozmcafe.service;

import com.project.yozmcafe.domain.member.Member;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class UnViewedCafeRefillListener {

    private final UnViewedCafeService unViewedCafeService;

    public UnViewedCafeRefillListener(final UnViewedCafeService unViewedCafeService) {
        this.unViewedCafeService = unViewedCafeService;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void listen(final CafeRefillEvent event) {
        final Member member = event.getMember();
        unViewedCafeService.refillUnViewedCafes(member);
    }
}
