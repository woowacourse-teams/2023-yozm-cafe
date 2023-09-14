package com.project.yozmcafe.controller;

import com.project.yozmcafe.controller.dto.cafe.LikedCafeResponse;
import com.project.yozmcafe.controller.dto.cafe.CafeThumbnailResponse;
import com.project.yozmcafe.domain.member.Member;
import com.project.yozmcafe.service.LikedCafeService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LikedCafeController {

    private static final int PAGE_SIZE = 15;

    private final LikedCafeService likedCafeService;

    public LikedCafeController(final LikedCafeService likedCafeService) {
        this.likedCafeService = likedCafeService;
    }

    @GetMapping("/members/{memberId}/liked-cafes")
    public ResponseEntity<List<CafeThumbnailResponse>> getLikedCafeThumbnails(@PathVariable("memberId") final String memberId,
                                                                              @PageableDefault(size = PAGE_SIZE) final Pageable pageable) {
        final List<CafeThumbnailResponse> likedCafes = likedCafeService.findLikedCafeThumbnailsByMemberId(memberId, pageable);

        return ResponseEntity.ok(likedCafes);
    }

    @GetMapping("/members/{memberId}/liked-cafes/details")
    public ResponseEntity<List<LikedCafeResponse>> getLikedCafes(@PathVariable("memberId") final String memberId) {
        final List<LikedCafeResponse> likedCafes = likedCafeService.findLikedCafesByMemberId(memberId);
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
