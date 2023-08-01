package com.project.yozmcafe.domain;

import com.project.yozmcafe.domain.cafe.Cafe;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class RandomCafeShuffleStrategy implements CafeShuffleStrategy {
    @Override
    public List<Cafe> shuffle(final List<Cafe> cafes) {
        final List<Cafe> result = new ArrayList<>(cafes);
        Collections.shuffle(result);

        return result;
    }
}
