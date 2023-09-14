package com.project.yozmcafe.controller;

import com.project.yozmcafe.controller.dto.cafe.CafeRankResponse;
import com.project.yozmcafe.controller.dto.cafe.CafeResponse;
import com.project.yozmcafe.controller.dto.cafe.CafeSearchResponse;
import com.project.yozmcafe.domain.member.Member;
import com.project.yozmcafe.service.CafeService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cafes")
public class CafeController {

    private static final int PAGE_SIZE = 5;
    private static final int RANKING_PAGE_SIZE = 10;

    private final CafeService cafeService;

    public CafeController(final CafeService cafeService) {
        this.cafeService = cafeService;
    }

    @GetMapping
    public ResponseEntity<List<CafeResponse>> getCafesForLoggedInMember(final Member member) {
        final List<CafeResponse> cafeResponses = cafeService.getCafesForLoginMember(member, PAGE_SIZE);
        return ResponseEntity.ok(cafeResponses);
    }

    @GetMapping("/guest")
    public ResponseEntity<List<CafeResponse>> getCafesForUnLoggedInMember(@PageableDefault(size = PAGE_SIZE) final Pageable pageable) {
        final List<CafeResponse> cafeResponses = cafeService.getCafesForUnLoginMember(pageable);
        return ResponseEntity.ok(cafeResponses);
    }

    @GetMapping("/ranks")
    public ResponseEntity<List<CafeRankResponse>> getCafesOrderByLikeCount(@PageableDefault(size = RANKING_PAGE_SIZE) final Pageable pageable) {
        final List<CafeRankResponse> cafeRankResponses = cafeService.getCafesOrderByLikeCount(pageable);
        return ResponseEntity.ok(cafeRankResponses);
    }

    @GetMapping("/{cafeId}")
    public ResponseEntity<CafeResponse> getCafeById(@PathVariable("cafeId") final long cafeId) {
        final CafeResponse cafeResponse = cafeService.getCafeById(cafeId);
        return ResponseEntity.ok(cafeResponse);
    }

    @GetMapping("/search")
    public ResponseEntity<List<CafeSearchResponse>> getCafeBySearch(@RequestParam("query") final String searchWord,
                                                      @RequestParam(value = "isCafeName", required = false, defaultValue = "false") final boolean isCafeName,
                                                      @RequestParam(value = "isMenu", required = false, defaultValue = "false") final boolean isMenu,
                                                      @RequestParam(value = "isAddress", required = false, defaultValue = "false") final boolean isAddress) {
        final List<CafeSearchResponse> cafeSearchResponses = cafeService.searchCafesByWord(searchWord, isCafeName, isMenu, isAddress);
        return ResponseEntity.ok(cafeSearchResponses);
    }
}


