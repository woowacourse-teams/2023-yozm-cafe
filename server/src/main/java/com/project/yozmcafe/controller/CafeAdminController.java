package com.project.yozmcafe.controller;

import com.project.yozmcafe.controller.dto.cafe.CafeRequest;
import com.project.yozmcafe.controller.dto.cafe.CafeResponse;
import com.project.yozmcafe.controller.dto.cafe.CafeUpdateRequest;
import com.project.yozmcafe.service.CafeAdminService;
import com.project.yozmcafe.service.ImageService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.net.URI;
import java.util.List;

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
    public ResponseEntity<String> save(@RequestBody final CafeRequest request) {
        final List<String> imageNames = imageService.uploadImagesToS3(request.images());

        cafeAdminService.save(request, imageNames);
        return ResponseEntity.created(URI.create("")).build();
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

    @PostMapping(value = "/test", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> get(final CafeRequest request) throws IOException {
        return ResponseEntity.ok(request.images().get(0).getBytes());
    }
}
