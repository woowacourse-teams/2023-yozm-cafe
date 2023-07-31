package com.project.yozmcafe.controller;

import com.project.yozmcafe.controller.dto.LikedCafeResponse;
import com.project.yozmcafe.domain.member.Member;
import com.project.yozmcafe.service.LikedCafeService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LikedCafeController {

    private final LikedCafeService likedCafeService;

    public LikedCafeController(final LikedCafeService likedCafeService) {
        this.likedCafeService = likedCafeService;
    }

    @GetMapping("/members/{memberId}/liked-cafes")
    public ResponseEntity<List<LikedCafeResponse>> getLikedCafes(@PathVariable("memberId") final String memberId,
                                                                 @PageableDefault(size = 15) final Pageable pageable) {
        final List<LikedCafeResponse> likedCafes = likedCafeService.findLikedCafesById(memberId, pageable);
        return ResponseEntity.ok(likedCafes);
    }

    @PostMapping("/cafes/{cafeId}/likes")
    public ResponseEntity<Void> updateLikes(final Member member,
                                            @PathVariable("cafeId") final long cafeId,
                                            @RequestParam("isLiked") final boolean isLiked) {
        likedCafeService.updateLike(member, cafeId, isLiked);
        return ResponseEntity.ok().build();
    }
}
