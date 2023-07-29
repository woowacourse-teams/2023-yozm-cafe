package com.project.yozmcafe.domain.cafe;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UnViewedCafeRepository extends JpaRepository<UnViewedCafe, Long> {

    Long countByMemberId(String memberId);

    @Query(value = "SELECT c FROM Cafe AS c "
            + "INNER JOIN UnViewedCafe AS u "
            + "ON c.id = u.cafe.id "
            + "WHERE u.member.id = :memberId")
    Page<Cafe> findUnViewedCafesByMember(@Param("memberId") final String memberId, final Pageable pageable);

    Optional<UnViewedCafe> findByMemberIdAndCafeId(final String memberId, final Long cafeId);
}
