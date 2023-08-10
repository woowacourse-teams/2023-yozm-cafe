package com.project.yozmcafe.controller;

import com.project.yozmcafe.controller.dto.cafe.LikedCafeDetailResponse;
import com.project.yozmcafe.controller.dto.cafe.LikedCafeResponse;
import com.project.yozmcafe.domain.member.Member;
import com.project.yozmcafe.service.LikedCafeService;
import jakarta.websocket.server.PathParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LikedCafeController {

    private static final int PAGE_SIZE = 15;

    private final LikedCafeService likedCafeService;

    public LikedCafeController(final LikedCafeService likedCafeService) {
        this.likedCafeService = likedCafeService;
    }

    @GetMapping("/members/{memberId}/liked-cafes")
    public ResponseEntity<List<LikedCafeResponse>> getLikedCafes(@PathVariable("memberId") final String memberId,
                                                                 @PathParam("page") final int page) {
        final List<LikedCafeResponse> likedCafes = likedCafeService.findLikedCafesById(memberId, page, PAGE_SIZE);
        return ResponseEntity.ok(likedCafes);
    }

    @GetMapping("/members/{memberId}/liked-cafes/details")
    public ResponseEntity<List<LikedCafeDetailResponse>> getLikedCafeDetails(@PathVariable("memberId") final String memberId) {
        final List<LikedCafeDetailResponse> likedCafeDetails = likedCafeService.findLikedCafeDetailsById(memberId);
        return ResponseEntity.ok(likedCafeDetails);
    }

    @PostMapping("/cafes/{cafeId}/likes")
    public ResponseEntity<Void> updateLikes(final Member member,
                                            @PathVariable("cafeId") final long cafeId,
                                            @RequestParam("isLiked") final boolean isLiked) {
        likedCafeService.updateLike(member, cafeId, isLiked);
        return ResponseEntity.ok().build();
    }
}
