package com.project.yozmcafe.controller.dto.menu;

import com.project.yozmcafe.domain.menu.Menu;

public record MenuItemResponse( Long id, String name, Long priority, String imageUrl, String description, String price,
                               boolean isRecommended) {
    public static MenuItemResponse from(final Menu menu) {
        return new MenuItemResponse(menu.getId(), menu.getName(), menu.getPriority(), menu.getImageUrl(),
                menu.getDescription(), menu.getPrice(), menu.isRecommended());
    }
}
