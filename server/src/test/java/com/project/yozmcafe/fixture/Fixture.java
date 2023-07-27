package com.project.yozmcafe.fixture;

import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.domain.cafe.Detail;
import com.project.yozmcafe.domain.cafe.Images;
import com.project.yozmcafe.domain.cafe.available.AvailableTime;
import com.project.yozmcafe.domain.cafe.available.Days;

import java.time.LocalTime;
import java.util.List;

public class Fixture {
    public static Detail getDetail() {
        return new Detail(
                List.of(new AvailableTime(Days.MONDAY, LocalTime.now(), LocalTime.now(), true),
                        new AvailableTime(Days.TUESDAY, LocalTime.now(), LocalTime.now(), true),
                        new AvailableTime(Days.WEDNESDAY, LocalTime.now(), LocalTime.now(), true),
                        new AvailableTime(Days.THURSDAY, LocalTime.now(), LocalTime.now(), true),
                        new AvailableTime(Days.FRIDAY, LocalTime.now(), LocalTime.now(), true),
                        new AvailableTime(Days.SATURDAY, LocalTime.now(), LocalTime.now(), true),
                        new AvailableTime(Days.SUNDAY, LocalTime.now(), LocalTime.now(), true))
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
