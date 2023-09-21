package com.project.yozmcafe.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {

    @Override
    @Query("""
            SELECT m
            FROM Member m
            LEFT JOIN FETCH m.unViewedCafes uvc
            JOIN FETCH uvc.cafe
            WHERE m.id = :id
            """)
    Optional<Member> findById(@Param("id") String id);

    @Override
    @Query("""
            SELECT m
            FROM Member m
            LEFT JOIN FETCH m.unViewedCafes uvc
            JOIN FETCH uvc.cafe
            """)
    List<Member> findAll();
}
