package com.project.yozmcafe.domain.cafe;

import com.project.yozmcafe.domain.member.Member;

import java.util.List;

public interface CafeCustomRepository {

    List<Cafe> findAllBy(final String cafeName, final String menu, final String address);

    List<Cafe> findAllBy(final String cafeName, final String address);

    void saveUnViewedCafes(final List<Cafe> cafes, final Member member);
}
