package com.project.yozmcafe.domain;

import java.util.List;

import com.project.yozmcafe.domain.cafe.LikedCafe;
import com.project.yozmcafe.domain.cafe.UnViewedCafe;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Member {
    @Id
    private Long id;

    @OneToMany(mappedBy = "member")
    private List<UnViewedCafe> unViewedCafes;

    @OneToMany(mappedBy = "member")
    private List<LikedCafe> likedCafes;
}
