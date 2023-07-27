package com.project.yozmcafe.util;

import com.project.yozmcafe.domain.cafe.UnViewedCafe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnViewedCafeRepository extends JpaRepository<UnViewedCafe, Long> {
}
