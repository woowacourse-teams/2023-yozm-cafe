package com.project.yozmcafe.controller;

import com.project.yozmcafe.controller.dto.cafe.CafeResponse;
import com.project.yozmcafe.domain.member.Member;
import com.project.yozmcafe.service.CafeService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CafeController {

    private final CafeService cafeService;

    public CafeController(final CafeService cafeService) {
        this.cafeService = cafeService;
    }

    @GetMapping("/cafes")
    public ResponseEntity<List<CafeResponse>> getCafesWithMember(final Member member, @PageableDefault(size = 5) final Pageable pageable) {
        List<CafeResponse> cafeResponses = cafeService.getCafesForLoginMember(pageable, member);
        return ResponseEntity.ok(cafeResponses);
    }

    @GetMapping("/guest/cafes")
    public ResponseEntity<List<CafeResponse>> getCafes(@PageableDefault(size = 5) final Pageable pageable) {
        List<CafeResponse> cafeResponses = cafeService.getCafesForUnLoginMember(pageable);
        return ResponseEntity.ok(cafeResponses);
    }
}
