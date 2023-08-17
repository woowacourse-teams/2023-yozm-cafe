package com.project.yozmcafe.service;

import static com.project.yozmcafe.exception.ErrorCode.NOT_EXISTED_CAFE;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.yozmcafe.controller.dto.cafe.MenuBoardRequest;
import com.project.yozmcafe.controller.dto.cafe.MenuRequest;
import com.project.yozmcafe.controller.dto.menu.MenuResponse;
import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.domain.cafe.CafeRepository;
import com.project.yozmcafe.domain.menu.Menu;
import com.project.yozmcafe.domain.menu.MenuBoard;
import com.project.yozmcafe.domain.menu.MenuBoardRepository;
import com.project.yozmcafe.domain.menu.MenuRepository;
import com.project.yozmcafe.exception.BadRequestException;

@Service
@Transactional(readOnly = true)
public class MenuService {

    private final CafeRepository cafeRepository;
    private final MenuRepository menuRepository;
    private final MenuBoardRepository menuBoardRepository;

    public MenuService(final CafeRepository cafeRepository,
                       final MenuRepository menuRepository,
                       final MenuBoardRepository menuBoardRepository) {
        this.cafeRepository = cafeRepository;
        this.menuRepository = menuRepository;
        this.menuBoardRepository = menuBoardRepository;
    }

    public MenuResponse getMenus(final Long cafeId) {
        final List<Menu> menus = menuRepository.findAllByCafeIdOrderByIsRecommendedDescPriorityAsc(cafeId);
        final List<MenuBoard> menuBoards = menuBoardRepository.findAllByCafeIdOrderByPriorityAsc(cafeId);

        return MenuResponse.of(cafeId, menuBoards, menus);
    }

    @Transactional
    public void saveMenu(final Long cafeId,
                         final MenuRequest menuRequest,
                         final String imageName) {
        final Cafe cafe = cafeRepository.findById(cafeId)
                .orElseThrow(() -> new BadRequestException(NOT_EXISTED_CAFE));

        Menu menu = menuRequest.toMenu(cafe, imageName);
        menuRepository.save(menu);
    }

    @Transactional
    public void saveMenuBoard(final Long cafeId,
                              final MenuBoardRequest menuBoardRequest,
                              final String imageName) {
        final Cafe cafe = cafeRepository.findById(cafeId)
                .orElseThrow(() -> new BadRequestException(NOT_EXISTED_CAFE));

        MenuBoard menuBoard = menuBoardRequest.toMenu(cafe, imageName);
        menuBoardRepository.save(menuBoard);
    }
}
