package com.project.yozmcafe.service;

import com.project.yozmcafe.controller.dto.MemberResponse;
import com.project.yozmcafe.domain.member.Member;
import com.project.yozmcafe.domain.member.MemberRepository;
import com.project.yozmcafe.exception.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.project.yozmcafe.exception.ErrorCode.NOT_EXISTED_MEMBER;

@Transactional(readOnly = true)
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(final MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public MemberResponse findById(final String memberId) {
        final Member member = findMemberByIdOrElseThrow(memberId);

        return MemberResponse.from(member);
    }

    public Member findMemberByIdOrElseThrow(final String memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new BadRequestException(NOT_EXISTED_MEMBER));
    }
}
