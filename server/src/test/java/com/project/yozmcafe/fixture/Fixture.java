package com.project.yozmcafe.fixture;

import java.time.LocalDateTime;
import java.util.List;

import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.domain.cafe.Detail;
import com.project.yozmcafe.domain.cafe.Images;
import com.project.yozmcafe.domain.cafe.available.AvailableTime;
import com.project.yozmcafe.domain.cafe.available.Days;

public class Fixture {
    public static final Detail DETAIL = new Detail(
            List.of(new AvailableTime(Days.MONDAY, LocalDateTime.now(), LocalDateTime.now(), true),
                    new AvailableTime(Days.TUESDAY, LocalDateTime.now(), LocalDateTime.now(), true),
                    new AvailableTime(Days.WEDNESDAY, LocalDateTime.now(), LocalDateTime.now(), true),
                    new AvailableTime(Days.THURSDAY, LocalDateTime.now(), LocalDateTime.now(), true),
                    new AvailableTime(Days.FRIDAY, LocalDateTime.now(), LocalDateTime.now(), true),
                    new AvailableTime(Days.SATURDAY, LocalDateTime.now(), LocalDateTime.now(), true),
                    new AvailableTime(Days.SUNDAY, LocalDateTime.now(), LocalDateTime.now(), true))
            , "mapUrl", "desc");
    public static final Images IMAGES = new Images(List.of("image1", "image2"));
    public static final Cafe CAFE_1 = new Cafe(null, "카페1", "카페주소1", IMAGES, DETAIL, 10);
    public static final Cafe CAFE_2 = new Cafe(null, "카페2", "카페주소2", IMAGES, DETAIL, 11);
    public static final Cafe CAFE_3 = new Cafe(null, "카페3", "카페주소3", IMAGES, DETAIL, 12);
    public static final Cafe CAFE_4 = new Cafe(null, "카페4", "카페주소4", IMAGES, DETAIL, 13);
    public static final Cafe CAFE_5 = new Cafe(null, "카페5", "카페주소5", IMAGES, DETAIL, 14);
}
