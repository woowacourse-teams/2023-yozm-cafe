package com.project.yozmcafe.domain.menu;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    @Query("SELECT m FROM Menu m " +
            "JOIN FETCH m.cafe c " +
            "LEFT JOIN FETCH c.images.urls " +
            "WHERE m.cafe.id = :cafeId " +
            "ORDER BY m.isRecommended DESC, m.priority ASC")
    List<Menu> findAllByCafeIdOrderByIsRecommendedDescPriorityAsc(Long cafeId);

}
