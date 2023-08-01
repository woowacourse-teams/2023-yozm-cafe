package com.project.yozmcafe.domain.cafe;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikedCafeRepository extends JpaRepository<LikedCafe, Long> {
    Slice<LikedCafe> findLikedCafesByMemberIdOrderByIdDesc(final String memberId, final Pageable pageable);
}
