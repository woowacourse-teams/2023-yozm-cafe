package com.project.yozmcafe.domain.cafe;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UnViewedCafeRepository extends JpaRepository<UnViewedCafe, Long> {

    List<UnViewedCafe> findByMemberIdAndCafeId(final String memberId, final Long cafeId);
}
