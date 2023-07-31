package com.project.yozmcafe.domain.member;

import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.domain.cafe.LikedCafe;
import com.project.yozmcafe.domain.cafe.UnViewedCafe;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
public class Member {

    private static final String NOT_EXISTED_UNVIEWED_CAFE = "존재하지 않는 내역입니다.";
    private static final String NOT_EXISTED_LIKED_CAFE = "존재하지 않는 좋아요 내역입니다.";

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

    public void addUnViewedCafesWithShuffle(final List<Cafe> cafes) {
        Collections.shuffle(cafes);

        final List<UnViewedCafe> allUnViewedCafes = cafes.stream()
                .map(savedCafe -> new UnViewedCafe(savedCafe, this))
                .toList();
        unViewedCafes.addAll(allUnViewedCafes);
    }

    public void removeUnViewedCafe(final UnViewedCafe unViewedCafe) {
        unViewedCafes.stream()
                .filter(unViewedCafe::equals)
                .findAny()
                .ifPresentOrElse(
                        unViewedCafes::remove, () -> {
                            throw new IllegalArgumentException(NOT_EXISTED_UNVIEWED_CAFE);
                        }
                );
    }

    public boolean isEmptyUnViewedCafe() {
        return unViewedCafes.isEmpty();
    }

    public void updateLikedCafesBy(final Cafe cafe, final boolean isLiked) {
        if (alreadySatisfiedBy(cafe, isLiked)) {
            return;
        }

        if (isLiked) {
            addLikedCafe(cafe);
        }

        if (!isLiked) {
            removeLikedCafe(cafe);
        }
    }

    private boolean alreadySatisfiedBy(final Cafe cafe, final boolean isLiked) {
        return isLike(cafe) == isLiked;
    }

    private void removeLikedCafe(final Cafe cafe) {
        likedCafes.stream()
                .filter(likedCafe -> likedCafe.isSameCafe(cafe))
                .findAny()
                .ifPresentOrElse(likedCafes::remove, () -> {
                    throw new IllegalArgumentException(NOT_EXISTED_LIKED_CAFE);
                });
        cafe.subtractLikeCount();
    }


    public void addLikedCafe(Cafe cafe) {
        likedCafes.add(new LikedCafe(cafe, this));
        cafe.addLikeCount();
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
