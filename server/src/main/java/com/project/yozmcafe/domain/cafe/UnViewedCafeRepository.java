package com.project.yozmcafe.domain.cafe;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UnViewedCafeRepository extends JpaRepository<UnViewedCafe, Long> {

    @Query(value = "SELECT c FROM Cafe AS c "
            + "INNER JOIN UnViewedCafe AS u "
            + "ON c.id = u.cafe.id "
            + "WHERE u.member.id = :memberId")
    List<Cafe> findUnViewedCafesByMember(@Param("memberId") final String memberId, final Pageable pageable);

    List<UnViewedCafe> findByMemberIdAndCafeId(final String memberId, final Long cafeId);
}
