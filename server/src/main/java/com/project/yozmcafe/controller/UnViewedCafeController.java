package com.project.yozmcafe.controller;

import com.project.yozmcafe.domain.member.Member;
import com.project.yozmcafe.service.UnViewedCafeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UnViewedCafeController {

    private final UnViewedCafeService unViewedCafeService;

    public UnViewedCafeController(final UnViewedCafeService unViewedCafeService) {
        this.unViewedCafeService = unViewedCafeService;
    }

    @PostMapping("cafes/{cafeId}/history")
    public ResponseEntity<Void> removeUnViewedCafe(final Member member,
                                                   @PathVariable("cafeId") final long cafeId) {
        unViewedCafeService.removeUnViewedCafe(member, cafeId);
        return ResponseEntity.ok().build();
    }
}
