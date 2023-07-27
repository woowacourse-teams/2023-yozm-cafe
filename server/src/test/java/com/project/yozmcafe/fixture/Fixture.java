package com.project.yozmcafe.fixture;

import java.time.LocalDateTime;
import java.util.List;

import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.domain.cafe.Detail;
import com.project.yozmcafe.domain.cafe.Images;
import com.project.yozmcafe.domain.cafe.available.AvailableTime;
import com.project.yozmcafe.domain.cafe.available.Days;

public class Fixture {
    public static Detail getDetail() {
        return new Detail(
                List.of(new AvailableTime(Days.MONDAY, LocalDateTime.now(), LocalDateTime.now(), true),
                        new AvailableTime(Days.TUESDAY, LocalDateTime.now(), LocalDateTime.now(), true),
                        new AvailableTime(Days.WEDNESDAY, LocalDateTime.now(), LocalDateTime.now(), true),
                        new AvailableTime(Days.THURSDAY, LocalDateTime.now(), LocalDateTime.now(), true),
                        new AvailableTime(Days.FRIDAY, LocalDateTime.now(), LocalDateTime.now(), true),
                        new AvailableTime(Days.SATURDAY, LocalDateTime.now(), LocalDateTime.now(), true),
                        new AvailableTime(Days.SUNDAY, LocalDateTime.now(), LocalDateTime.now(), true))
                , "mapUrl", "desc", "010-1234-5678");
    }

    public static Images getImages() {
        return new Images(List.of("image1", "image2"));
    }

    public static Cafe getCafe(String name, String address, int likeCount) {
        return getCafe(null, name, address, likeCount);
    }

    public static Cafe getCafe(Long id, String name, String address, int likeCount) {
        return new Cafe(id, name, address, getImages(), getDetail(), likeCount);
    }
}
