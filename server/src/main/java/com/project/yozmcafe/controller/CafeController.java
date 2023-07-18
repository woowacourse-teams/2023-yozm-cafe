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
    public ResponseEntity<List<CafeDto>> getCafes(@PageableDefault(size = 5) Pageable pageable) {
        final List<CafeDto> cafeDtos = cafeService.getCafesForUnLoginMember(pageable);
        // TODO: 2023/07/17 로그인 된 사용자인 경우 분기처리
        return ResponseEntity.ok(cafeDtos);
    }
}
