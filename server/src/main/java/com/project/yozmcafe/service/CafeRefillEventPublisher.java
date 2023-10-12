package com.project.yozmcafe.service;

import com.project.yozmcafe.domain.member.Member;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class CafeRefillEventPublisher {

    private final ApplicationEventPublisher eventPublisher;

    public CafeRefillEventPublisher(final ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void publishCafeRefillEvent(final Member member) {
        eventPublisher.publishEvent(new CafeRefillEvent(this, member));
    }
}
