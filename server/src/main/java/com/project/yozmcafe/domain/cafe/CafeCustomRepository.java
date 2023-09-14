package com.project.yozmcafe.domain.cafe;

import java.util.List;

public interface CafeCustomRepository {

    List<Cafe> findAllBy(final String cafeNameWord, final String menuWord, final String addressWord);

    List<Cafe> findAllBy(final String cafeNameWord, final String addressWord);

}
