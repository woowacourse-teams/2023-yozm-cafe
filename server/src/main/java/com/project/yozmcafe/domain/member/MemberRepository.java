package com.project.yozmcafe.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {

    @Query(value = "select distinct m from Member m left join fetch m.unViewedCafes uvc left join fetch uvc.cafe where m.id = :id")
    Optional<Member> findById(String id);

    @Query(value = "select distinct m from Member m left join fetch m.unViewedCafes uvc left join fetch uvc.cafe")
    List<Member> findAll();
}
