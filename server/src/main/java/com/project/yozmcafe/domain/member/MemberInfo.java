package com.project.yozmcafe.domain.member;

public record MemberInfo(String openId, String name, String image) {

    public Member toMember() {
        return new Member(openId, name, image);
    }
}
