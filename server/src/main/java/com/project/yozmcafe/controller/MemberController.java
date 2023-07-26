package com.project.yozmcafe.controller;

import com.project.yozmcafe.controller.dto.LikedCafeResponse;
import com.project.yozmcafe.controller.dto.MemberResponse;
import com.project.yozmcafe.service.MemberService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(final MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<MemberResponse> findById(@PathVariable("memberId") final String memberId) {
        final MemberResponse member = memberService.findById(memberId);
        return ResponseEntity.ok(member);
    }

    @GetMapping("/{memberId}/likedCafes")
    public ResponseEntity<List<LikedCafeResponse>> getLikedCafes(@PathVariable("memberId") final String memberId,
                                                                 final Pageable pageable) {
        final List<LikedCafeResponse> likedCafes = memberService.findLikedCafesById(memberId, pageable);
        return ResponseEntity.ok(likedCafes);
    }
}
