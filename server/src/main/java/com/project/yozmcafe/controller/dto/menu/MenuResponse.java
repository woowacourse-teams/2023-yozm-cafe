package com.project.yozmcafe.controller.dto.menu;

import com.project.yozmcafe.domain.menu.Menu;
import com.project.yozmcafe.domain.menu.MenuBoard;

import java.util.List;

public record MenuResponse(Long cafeId, List<MenuBoardResponse> menuBoards, List<MenuItemResponse> menus) {
    public static MenuResponse of(final Long cafeId, final List<MenuBoard> menuBoards, final List<Menu> menus) {
        final List<MenuBoardResponse> menuBoardResponses = menuBoards.stream()
                .map(MenuBoardResponse::from)
                .toList();

        final List<MenuItemResponse> menuItemResponses = menus.stream()
                .map(MenuItemResponse::from)
                .toList();
        return new MenuResponse(cafeId, menuBoardResponses, menuItemResponses);
    }
}
