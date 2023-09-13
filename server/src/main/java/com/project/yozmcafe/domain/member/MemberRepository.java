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
            select m
            from Member m
            left join fetch m.unViewedCafes uvc
            left join fetch uvc.cafe
            where m.id = :id
            """)
    Optional<Member> findById(@Param("id") String id);

    @Override
    @Query("""
            select m
            from Member m
            left join fetch m.unViewedCafes uvc
            left join fetch uvc.cafe
            """)
    List<Member> findAll();
}
