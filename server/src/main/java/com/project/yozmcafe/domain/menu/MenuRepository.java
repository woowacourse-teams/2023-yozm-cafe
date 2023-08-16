package com.project.yozmcafe.domain.menu;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findAllByCafeIdOrderByIsRecommendedDescPriorityAsc(Long cafeId);
}
