package com.project.yozmcafe.controller.dto.cafe;

import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.domain.menu.MenuBoard;

public record MenuBoardRequest(int priority) {
    public MenuBoard toMenu(final Cafe cafe, final String imageName) {
        return new MenuBoard(cafe, this.priority, imageName);
    }
}
