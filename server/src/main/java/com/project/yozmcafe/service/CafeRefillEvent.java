package com.project.yozmcafe.service;

import com.project.yozmcafe.domain.member.Member;
import org.springframework.context.ApplicationEvent;

public class CafeRefillEvent extends ApplicationEvent {

    private final Member member;

    public CafeRefillEvent(final Object source, final Member member) {
        super(source);
        this.member = member;
    }

    public Member getMember() {
        return member;
    }
}
