package com.project.yozmcafe.domain.member;

import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.domain.cafe.LikedCafe;
import com.project.yozmcafe.domain.cafe.UnViewedCafe;
import com.project.yozmcafe.exception.BadRequestException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.project.yozmcafe.exception.ErrorCode.NOT_EXISTED_LIKED_CAFE;
import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.CascadeType.PERSIST;
import static java.lang.Math.min;

@Entity
public class Member {

    @Id
    private String id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String image;

    @OneToMany(mappedBy = "member", orphanRemoval = true, cascade = {PERSIST, MERGE})
    private List<UnViewedCafe> unViewedCafes = new ArrayList<>();

    @OrderBy("createdAt Desc")
    @OneToMany(mappedBy = "member", orphanRemoval = true, cascade = {PERSIST, MERGE})
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
                .map(cafe -> new UnViewedCafe(cafe, this))
                .filter(unViewedCafe -> !unViewedCafes.contains(unViewedCafe))
                .toList();

        unViewedCafes.addAll(allUnViewedCafes);
    }

    public boolean isUnViewedCafesSizeUnder(final int sizeExclusive) {
        return unViewedCafes.size() < sizeExclusive;
    }

    public void updateLikedCafesBy(final Cafe cafe, final boolean isLiked) {
        if (isLike(cafe) == isLiked) {
            return;
        }

        if (isLiked) {
            addLikedCafe(cafe);
        }

        if (!isLiked) {
            removeLikedCafe(cafe);
        }
    }

    public boolean isLike(final Cafe cafe) {
        return likedCafes.stream()
                .anyMatch(likedCafe -> likedCafe.isSameCafe(cafe));
    }

    private void addLikedCafe(final Cafe cafe) {
        likedCafes.add(new LikedCafe(cafe, this));
        cafe.addLikeCount();
    }

    private void removeLikedCafe(final Cafe cafe) {
        likedCafes.stream()
                .filter(likedCafe -> likedCafe.isSameCafe(cafe))
                .findAny()
                .ifPresentOrElse(likedCafes::remove, () -> {
                    throw new BadRequestException(NOT_EXISTED_LIKED_CAFE);
                });
        cafe.subtractLikeCount();
    }

    public List<UnViewedCafe> getNextUnViewedCafes(final int size) {
        final int availableSize = min(unViewedCafes.size(), size);
        final List<UnViewedCafe> result = new ArrayList<>(unViewedCafes.subList(0, availableSize));
        unViewedCafes.removeAll(result);

        return result;
    }

    public List<LikedCafe> getLikedCafesSection(final int startIndex, final int endIndex) {
        if (startIndex >= likedCafes.size()) {
            return Collections.emptyList();
        }

        final List<LikedCafe> reverseLikedCafes = new ArrayList<>(likedCafes);

        return reverseLikedCafes.subList(startIndex, min(endIndex, likedCafes.size()));
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
