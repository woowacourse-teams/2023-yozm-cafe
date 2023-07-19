package com.project.yozmcafe.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.yozmcafe.controller.dto.cafe.CafeDto;
import com.project.yozmcafe.service.CafeService;

@RestController
@RequestMapping("/cafes")
public class CafeController {

    private final CafeService cafeService;

    public CafeController(final CafeService cafeService) {
        this.cafeService = cafeService;
    }

    @GetMapping
    public ResponseEntity<List<CafeDto>> getCafes(final @PageableDefault(size = 5) Pageable pageable) {
        List<CafeDto> cafeDtos = cafeService.getCafesForUnLoginMember(pageable);
        return ResponseEntity.ok(cafeDtos);
    }
}
