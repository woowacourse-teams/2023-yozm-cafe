package com.project.yozmcafe.util;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.yozmcafe.domain.cafe.LikedCafe;

public interface LikeCafeRepository extends JpaRepository<LikedCafe, Long> {
}
