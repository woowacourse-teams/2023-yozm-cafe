package com.project.yozmcafe.controller.dto;

import com.project.yozmcafe.domain.member.Member;

public record MemberResponse(String id, String name, String imageUrl) {

    public static MemberResponse from(final Member member) {
        return new MemberResponse(member.getId(), member.getName(), member.getImage());
    }
}
