package com.project.yozmcafe.domain.menu;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuBoardRepository extends JpaRepository<MenuBoard, Long> {

    List<MenuBoard> findAllByCafeIdOrderByPriorityAsc(Long cafeId);
}
