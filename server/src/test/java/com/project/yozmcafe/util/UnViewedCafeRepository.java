package com.project.yozmcafe.util;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.yozmcafe.domain.cafe.UnViewedCafe;

public interface UnViewedCafeRepository extends JpaRepository<UnViewedCafe, Long> {
}
