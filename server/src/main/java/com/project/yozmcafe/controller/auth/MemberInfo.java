package com.project.yozmcafe.controller.auth;

import com.project.yozmcafe.domain.member.Member;

public record MemberInfo(String openId, String name, String image) {

    public Member toMember() {
        return new Member(openId, name, image);
    }
}
