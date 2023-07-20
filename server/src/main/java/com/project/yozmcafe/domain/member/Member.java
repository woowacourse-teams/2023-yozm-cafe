package com.project.yozmcafe.domain.member;

import java.util.ArrayList;
import java.util.List;

import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.domain.cafe.LikedCafe;
import com.project.yozmcafe.domain.cafe.UnViewedCafe;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "member", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<UnViewedCafe> unViewedCafes;

    @OneToMany(mappedBy = "member")
    private List<LikedCafe> likedCafes;

    protected Member() {
    }

    public Member(final Long id) {
        this.id = id;
        this.unViewedCafes = new ArrayList<>();
        this.likedCafes = new ArrayList<>();
    }

    public void addUnViewedCafes(final List<Cafe> cafes) {
        final List<UnViewedCafe> allUnViewedCafes = cafes.stream()
                .map(savedCafe -> new UnViewedCafe(savedCafe, this))
                .toList();
        unViewedCafes.addAll(allUnViewedCafes);
    }

    public void removeUnViewedCafe(final Cafe cafe) {
        final UnViewedCafe foundUnviewedCafe = unViewedCafes.stream()
                .filter(unViewedCafe -> unViewedCafe.isMatch(this, cafe))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 내역입니다."));

        unViewedCafes.remove(foundUnviewedCafe);
    }

    public boolean isEmptyUnViewedCafe() {
        return unViewedCafes.isEmpty();
    }

    public Long getId() {
        return id;
    }

    public List<UnViewedCafe> getUnViewedCafes() {
        return unViewedCafes;
    }

    public List<LikedCafe> getLikedCafes() {
        return likedCafes;
    }
}
