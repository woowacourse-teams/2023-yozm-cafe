package com.project.yozmcafe.domain.cafe;

import com.project.yozmcafe.domain.member.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.Objects;

@Entity
public class UnViewedCafe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Cafe cafe;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Member member;

    protected UnViewedCafe() {
    }

    public UnViewedCafe(final Cafe cafe, final Member member) {
        this(null, cafe, member);
    }

    public UnViewedCafe(final Long id, final Cafe cafe, final Member member) {
        this.id = id;
        this.cafe = cafe;
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

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final UnViewedCafe that = (UnViewedCafe) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
