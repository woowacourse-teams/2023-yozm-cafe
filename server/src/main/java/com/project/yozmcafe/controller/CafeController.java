package com.project.yozmcafe.controller;

import com.project.yozmcafe.controller.dto.cafe.CafeRequest;
import com.project.yozmcafe.controller.dto.cafe.CafeResponse;
import com.project.yozmcafe.domain.member.Member;
import com.project.yozmcafe.service.CafeService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cafes")
public class CafeController {

    private static final int PAGE_SIZE = 5;
    private static final int IGNORED_PAGE = 0;

    private final CafeService cafeService;

    public CafeController(final CafeService cafeService) {
        this.cafeService = cafeService;
    }

    @GetMapping
    public ResponseEntity<List<CafeResponse>> getCafesWithMember(final Member member) {
        final PageRequest memberDefaultPageRequest = PageRequest.of(IGNORED_PAGE, PAGE_SIZE);

        List<CafeResponse> cafeResponses = cafeService.getCafesForLoginMember(memberDefaultPageRequest, member);
        return ResponseEntity.ok(cafeResponses);
    }

    @GetMapping("/guest")
    public ResponseEntity<List<CafeResponse>> getCafes(@PageableDefault(size = PAGE_SIZE) final Pageable pageable) {
        List<CafeResponse> cafeResponses = cafeService.getCafesForUnLoginMember(pageable);
        return ResponseEntity.ok(cafeResponses);
    }

    @PostMapping
    public ResponseEntity<Void> saveCafe(@RequestBody CafeRequest cafeRequest) {
        cafeService.saveCafe(cafeRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
