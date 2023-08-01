package com.project.yozmcafe.controller;

import com.project.yozmcafe.controller.dto.MemberResponse;
import com.project.yozmcafe.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
