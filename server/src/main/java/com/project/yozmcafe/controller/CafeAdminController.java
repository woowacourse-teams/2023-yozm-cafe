package com.project.yozmcafe.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.project.yozmcafe.controller.dto.cafe.CafeRequest;
import com.project.yozmcafe.controller.dto.cafe.CafeResponse;
import com.project.yozmcafe.controller.dto.cafe.CafeUpdateRequest;
import com.project.yozmcafe.service.CafeAdminService;
import com.project.yozmcafe.service.ImageService;

@Controller
@RequestMapping("/admin/cafes")
public class CafeAdminController {

    private final CafeAdminService cafeAdminService;
    private final ImageService imageService;

    public CafeAdminController(final CafeAdminService cafeAdminService, final ImageService imageService) {
        this.cafeAdminService = cafeAdminService;
        this.imageService = imageService;
    }

    @PostMapping
    public ResponseEntity<String> save(@RequestPart final CafeRequest request,
                                       @RequestPart final List<MultipartFile> images) {
        final List<String> uploadedFileNames = imageService.upload(images);
        final Long savedId = cafeAdminService.save(request, uploadedFileNames);
        return ResponseEntity.created(URI.create("/admin/cafes/" + savedId)).build();
    }

    @PutMapping("/{cafeId}")
    public ResponseEntity<Void> update(@PathVariable("cafeId") final Long cafeId,
                                       @RequestBody final CafeUpdateRequest request) {
        cafeAdminService.update(cafeId, request);
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
        cafeAdminService.delete(cafeId);
        return ResponseEntity.noContent().build();
    }
}
