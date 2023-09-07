package com.project.yozmcafe.domain.menu;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MenuBoardRepository extends JpaRepository<MenuBoard, Long> {

    @Query("""
            SELECT m FROM MenuBoard m
            JOIN FETCH m.cafe c
            LEFT JOIN FETCH c.images.urls
            WHERE m.cafe.id = :cafeId
            ORDER BY m.priority ASC
            """)
    List<MenuBoard> findAllByCafeIdOrderByPriorityAsc(Long cafeId);
}
