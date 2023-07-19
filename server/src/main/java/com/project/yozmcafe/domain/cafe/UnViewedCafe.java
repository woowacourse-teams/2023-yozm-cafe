package com.project.yozmcafe.domain.cafe;

import com.project.yozmcafe.domain.member.Member;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class UnViewedCafe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Cafe cafe;

    @ManyToOne
    private Member member;

    protected UnViewedCafe() {
    }

    public UnViewedCafe(final Long id, final Cafe cafe, final Member member) {
        this.id = id;
        this.cafe = cafe;
        this.member = member;
    }

    public void remove() {
        this.member = null;
        this.cafe = null;
    }

    public boolean isMatch(final Member member, final Cafe cafe) {
        return this.member.equals(member) && this.cafe.equals(cafe);
    }

    public void setMember(final Member member) {
        this.member = member;
    }

    public Long getId() {
        return id;
    }

    public Cafe getCafe() {
        return cafe;
    }

    public Member getMember() {
        return member;
    }
}
