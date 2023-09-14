package com.project.yozmcafe.controller;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.project.yozmcafe.controller.dto.cafe.CafeRequest;
import com.project.yozmcafe.controller.dto.cafe.CafeResponse;
import com.project.yozmcafe.controller.dto.cafe.CafeUpdateRequest;
import com.project.yozmcafe.controller.dto.cafe.MenuBoardRequest;
import com.project.yozmcafe.controller.dto.cafe.MenuRequest;
import com.project.yozmcafe.service.CafeAdminService;
import com.project.yozmcafe.service.ImageService;
import com.project.yozmcafe.service.MenuService;

@Controller
@RequestMapping("/admin/cafes")
public class CafeAdminController {

    private final CafeAdminService cafeAdminService;
    private final ImageService imageService;
    private final MenuService menuService;

    public CafeAdminController(final CafeAdminService cafeAdminService, final ImageService imageService,
                               final MenuService menuService) {
        this.cafeAdminService = cafeAdminService;
        this.imageService = imageService;
        this.menuService = menuService;
    }

    @PostMapping
    public ResponseEntity<String> save(@RequestPart final CafeRequest request,
                                       @RequestPart final List<MultipartFile> images) {
        final List<String> uploadedFileNames = imageService.resizeToAllSizesAndUpload(images);
        final Long savedId = cafeAdminService.save(request, uploadedFileNames);

        return ResponseEntity.created(URI.create("/admin/cafes/" + savedId)).build();
    }

    @PutMapping("/{cafeId}")
    public ResponseEntity<Void> update(@PathVariable("cafeId") final Long cafeId,
                                       @RequestPart final CafeUpdateRequest request,
                                       @RequestPart final List<MultipartFile> images) {
        final List<String> originalImages = cafeAdminService.findImagesByCafeId(cafeId);
        imageService.deleteAll(originalImages);

        final List<String> savedImages = imageService.resizeToAllSizesAndUpload(images);
        cafeAdminService.update(cafeId, request, savedImages);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<CafeResponse>> findAll() {
        final List<CafeResponse> cafes = cafeAdminService.findAll();
        return ResponseEntity.ok(cafes);
    }

    @GetMapping("/{cafeId}")
    public ResponseEntity<CafeResponse> findById(@PathVariable("cafeId") final Long cafeId) {
        final CafeResponse cafe = cafeAdminService.findById(cafeId);
        return ResponseEntity.ok(cafe);
    }

    @DeleteMapping("/{cafeId}")
    public ResponseEntity<Void> delete(@PathVariable("cafeId") final Long cafeId) {
        final List<String> originalImages = cafeAdminService.findImagesByCafeId(cafeId);
        imageService.deleteAll(originalImages);
        cafeAdminService.delete(cafeId);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{cafeId}/menus")
    public ResponseEntity<String> saveMenus(@PathVariable("cafeId") final Long cafeId,
                                            @RequestPart final MenuRequest menuRequest,
                                            @RequestPart(required = false) final MultipartFile image) {
        if (isNull(image)) {
            menuService.saveMenuWithoutImage(cafeId, menuRequest);
        }
        if (nonNull(image)) {
            final String uploadedFileName = imageService.resizeToThumbnailSizeAndUpload(image);
            menuService.saveMenu(cafeId, menuRequest, uploadedFileName);
        }

        return ResponseEntity.created(URI.create("/admin/cafes/" + cafeId)).build();
    }

    @PostMapping("/{cafeId}/menuBoards")
    public ResponseEntity<String> saveMenuBoards(@PathVariable("cafeId") final Long cafeId,
                                                 @RequestPart final MenuBoardRequest menuBoardRequest,
                                                 @RequestPart final MultipartFile image) {
        final String uploadedFileName = imageService.resizeToMobileSizeAndUpload(image);
        menuService.saveMenuBoard(cafeId, menuBoardRequest, uploadedFileName);

        return ResponseEntity.created(URI.create("/admin/cafes/" + cafeId)).build();
    }
}
