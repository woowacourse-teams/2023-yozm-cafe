package com.project.yozmcafe.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {

    @Query("""
            SELECT m
            FROM Member m
            JOIN FETCH m.unViewedCafes uvc
            JOIN FETCH uvc.cafe
            WHERE m.id = :id
            """)
    Optional<Member> findWithUnViewedCafesById(@Param("id") String id);
}
