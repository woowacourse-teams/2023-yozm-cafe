package com.project.yozmcafe.domain;

import com.project.yozmcafe.domain.cafe.Cafe;

import java.util.List;

public interface CafeShuffleStrategy {
    List<Cafe> shuffle(List<Cafe> cafes);
}
