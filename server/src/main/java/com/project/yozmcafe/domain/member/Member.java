package com.project.yozmcafe.domain.member;

import java.util.List;

import com.project.yozmcafe.domain.cafe.LikedCafe;
import com.project.yozmcafe.domain.cafe.UnViewedCafe;

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

    @OneToMany(mappedBy = "member")
    private List<UnViewedCafe> unViewedCafes;

    @OneToMany(mappedBy = "member")
    private List<LikedCafe> likedCafes;

    protected Member() {
    }

    public Member(final Long id) {
        this.id = id;
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
