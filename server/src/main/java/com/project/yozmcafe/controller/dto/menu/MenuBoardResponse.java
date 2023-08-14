package com.project.yozmcafe.controller.dto.menu;

import com.project.yozmcafe.domain.menu.MenuBoard;

public record MenuBoardResponse(Long id, Long priority, String imageUrl) {
    public static MenuBoardResponse from(final MenuBoard menuBoards) {
        return new MenuBoardResponse(menuBoards.getId(), menuBoards.getPriority(), menuBoards.getImageUrl());
    }
}
