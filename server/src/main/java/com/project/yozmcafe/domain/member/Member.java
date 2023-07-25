package com.project.yozmcafe.domain.member;

import java.util.ArrayList;
import java.util.List;

import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.domain.cafe.LikedCafe;
import com.project.yozmcafe.domain.cafe.UnViewedCafe;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Member {

    private static final String UN_EXISTED_UNVIEWED_CAFE = "존재하지 않는 내역입니다.";
    public static final String UN_EXISTED_LIKED_CAFE = "존재하지 않는 좋아요 내역입니다.";

    @Id
    private String id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String image;

    @OneToMany(mappedBy = "member", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<UnViewedCafe> unViewedCafes = new ArrayList<>();

    @OneToMany(mappedBy = "member", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<LikedCafe> likedCafes = new ArrayList<>();

    protected Member() {
    }

    public Member(final String id, final String name, final String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public void addUnViewedCafes(final List<Cafe> cafes) {
        final List<UnViewedCafe> allUnViewedCafes = cafes.stream()
                .map(savedCafe -> new UnViewedCafe(savedCafe, this))
                .toList();
        unViewedCafes.addAll(allUnViewedCafes);
    }

    public void removeUnViewedCafe(final UnViewedCafe unViewedCafe) {
        final UnViewedCafe foundUnviewedCafe = unViewedCafes.stream()
                .filter(unViewedCafe::equals)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(UN_EXISTED_UNVIEWED_CAFE));
        unViewedCafes.remove(foundUnviewedCafe);
    }

    public boolean isEmptyUnViewedCafe() {
        return unViewedCafes.isEmpty();
    }

    public void updateIsLike(final Cafe cafe, final boolean isLiked) {
        final boolean currentIsLike = isLike(cafe);
        if (currentIsLike != isLiked && isLiked) {
            addLikedCafe(cafe);
        }

        if (currentIsLike != isLiked && !isLiked) {
            removeLikedCafe(cafe);
        }
    }

    private void removeLikedCafe(final Cafe cafe) {
        final LikedCafe targetLikedCafe = likedCafes.stream()
                .filter(likedCafe -> likedCafe.isSameCafe(cafe))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(UN_EXISTED_LIKED_CAFE));
        likedCafes.remove(targetLikedCafe);
    }


    public void addLikedCafe(Cafe cafe) {
        likedCafes.add(new LikedCafe(cafe, this));
    }

    public boolean isLike(final Cafe cafe) {
        return likedCafes.stream()
                .anyMatch(likedCafe -> likedCafe.isSameCafe(cafe));
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public List<UnViewedCafe> getUnViewedCafes() {
        return unViewedCafes;
    }

    public List<LikedCafe> getLikedCafes() {
        return likedCafes;
    }
}
