package com.project.yozmcafe.domain.cafe;

import com.project.yozmcafe.domain.member.Member;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class LikedCafe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Cafe cafe;

    @ManyToOne
    private Member member;

    public LikedCafe() {
    }

    public LikedCafe(final Long id, final Cafe cafe, final Member member) {
        this.id = id;
        this.cafe = cafe;
        this.member = member;
    }

    public LikedCafe(final Cafe cafe, final Member member) {
        this(null, cafe, member);
    }

    public boolean isSameCafe(final Cafe other) {
        return cafe.equals(other);
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
