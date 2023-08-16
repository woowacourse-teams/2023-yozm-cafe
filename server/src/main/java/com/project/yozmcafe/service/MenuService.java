package com.project.yozmcafe.service;

import com.project.yozmcafe.controller.dto.menu.MenuResponse;
import com.project.yozmcafe.domain.menu.Menu;
import com.project.yozmcafe.domain.menu.MenuBoard;
import com.project.yozmcafe.domain.menu.MenuBoardRepository;
import com.project.yozmcafe.domain.menu.MenuRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class MenuService {

    private final MenuRepository menuRepository;
    private final MenuBoardRepository menuBoardRepository;

    public MenuService(final MenuRepository menuRepository, final MenuBoardRepository menuBoardRepository) {
        this.menuRepository = menuRepository;
        this.menuBoardRepository = menuBoardRepository;
    }

    public MenuResponse getMenus(final Long cafeId) {
        final List<Menu> menus = menuRepository.findAllByCafeIdOrderByIsRecommendedDescPriorityAsc(cafeId);
        final List<MenuBoard> menuBoards = menuBoardRepository.findAllByCafeIdOrderByPriorityAsc(cafeId);

        return MenuResponse.of(cafeId, menuBoards, menus);
    }
}
