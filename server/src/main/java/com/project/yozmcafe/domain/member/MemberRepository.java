package com.project.yozmcafe.domain.member;

import com.project.yozmcafe.domain.cafe.LikedCafe;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {
    Optional<Member> findById(String id);

    @Query("SELECT likedCafes FROM Member m JOIN m.likedCafes likedCafes WHERE m.id = :memberId")
    Slice<LikedCafe> findLikedCafesByMemberId(@Param("memberId") final String memberId, final Pageable pageable);
}
