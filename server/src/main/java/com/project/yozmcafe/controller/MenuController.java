package com.project.yozmcafe.controller;

import com.project.yozmcafe.controller.dto.menu.MenuResponse;
import com.project.yozmcafe.service.MenuService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MenuController {

    private final MenuService menuService;

    public MenuController(final MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping("/cafes/{cafeId}/menus")
    public ResponseEntity<MenuResponse> getMenus(@PathVariable("cafeId") final Long cafeId) {
        final MenuResponse menuResponse = menuService.getMenus(cafeId);

        return ResponseEntity.ok(menuResponse);
    }
}
