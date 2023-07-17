package com.project.yozmcafe.domain.cafe;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Cafes {
    private static final int CARD_LIMIT_SIZE = 3;
    private final List<Cafe> cafes;

    public Cafes(final List<Cafe> cafes) {
        this.cafes = cafes;
    }

    public List<Cafe> pickRandomCafe() {
        Set<Cafe> randomCafes = new HashSet<>();
        while (randomCafes.size() < CARD_LIMIT_SIZE) {
            final int randomIdx = (int) Math.floor(Math.random() * (cafes.size() - 1));
            randomCafes.add(cafes.get(randomIdx));
        }
        return new ArrayList<>(randomCafes);
    }
}
