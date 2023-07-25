package com.project.yozmcafe.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.yozmcafe.controller.dto.cafe.CafeResponse;
import com.project.yozmcafe.domain.member.Member;
import com.project.yozmcafe.service.CafeService;
import com.project.yozmcafe.service.MemberService;

@RestController
@RequestMapping("/cafes")
public class CafeController {

    private final CafeService cafeService;
    private final MemberService memberService;

    public CafeController(final CafeService cafeService, final MemberService memberService) {
        this.cafeService = cafeService;
        this.memberService = memberService;
    }

    @GetMapping
    public ResponseEntity<List<CafeResponse>> getCafesWithMember(final Member member,
                                                                 @PageableDefault(size = 5) final Pageable pageable) {
        List<CafeResponse> cafeResponses = cafeService.getCafesForLoginMember(pageable, member);
        return ResponseEntity.ok(cafeResponses);
    }

    @GetMapping("/guest")
    public ResponseEntity<List<CafeResponse>> getCafes(@PageableDefault(size = 5) final Pageable pageable) {
        List<CafeResponse> cafeResponses = cafeService.getCafesForUnLoginMember(pageable);
        return ResponseEntity.ok(cafeResponses);
    }

    @PostMapping("/{cafeId}/likes")
    public ResponseEntity<Void> updateLikes(Member member, @PathVariable("cafeId") final long cafeId,
                                            @RequestParam("isLiked") final boolean isLiked) {
        memberService.updateLike(member, cafeId, isLiked);
        return ResponseEntity.ok().build();
    }

}
