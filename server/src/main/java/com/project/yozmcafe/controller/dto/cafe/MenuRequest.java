package com.project.yozmcafe.controller.dto.cafe;

import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.domain.menu.Menu;

public record MenuRequest(int priority, String name, String description, String price, boolean recommended) {
    public Menu toMenu(final Cafe cafe, final String imageName) {
        return new Menu(cafe,
                this.priority,
                this.name,
                imageName,
                this.description,
                this.price,
                this.recommended);
    }
}
