package com.project.yozmcafe.domain.cafe;

import java.util.List;

public interface CafeCustomRepository {

    List<Cafe> findAllBy(final String cafeName, final String menu, final String address);

    List<Cafe> findAllBy(final String cafeName, final String address);
}
