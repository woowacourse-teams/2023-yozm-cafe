package com.project.yozmcafe.domain.member;

import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.domain.cafe.LikedCafe;
import com.project.yozmcafe.domain.cafe.UnViewedCafe;
import com.project.yozmcafe.exception.BadRequestException;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.project.yozmcafe.exception.ErrorCode.NOT_EXISTED_LIKED_CAFE;
import static com.project.yozmcafe.exception.ErrorCode.NOT_EXISTED_UN_VIEWED_CAFE;

@Entity
public class Member {

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
        unViewedCafes.stream()
                .filter(unViewedCafe::equals)
                .findAny()
                .ifPresentOrElse(
                        unViewedCafes::remove, () -> {
                            throw new BadRequestException(NOT_EXISTED_UN_VIEWED_CAFE);
                        }
                );
    }

    public boolean isEmptyUnViewedCafes() {
        return unViewedCafes.isEmpty();
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

    public void addLikedCafe(final Cafe cafe) {
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

    public List<LikedCafe> getLikedCafesByPaging(int pageNum, int pageSize) {
        List<LikedCafe> reverseLikedCafes = new ArrayList<>(likedCafes);
        Collections.reverse(reverseLikedCafes);

        int startIndex = (pageNum - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, reverseLikedCafes.size());

        return reverseLikedCafes.subList(startIndex, endIndex);
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
