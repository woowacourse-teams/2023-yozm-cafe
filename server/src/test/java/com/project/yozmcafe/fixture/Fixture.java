package com.project.yozmcafe.fixture;

import java.time.LocalDateTime;
import java.util.List;

import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.domain.cafe.Detail;
import com.project.yozmcafe.domain.cafe.Images;
import com.project.yozmcafe.domain.cafe.available.AvailableTime;
import com.project.yozmcafe.domain.cafe.available.Days;

public class Fixture {
    public static final Detail DETAIL_1 = new Detail(
            List.of(new AvailableTime(Days.MONDAY, LocalDateTime.now(), LocalDateTime.now(), true),
                    new AvailableTime(Days.TUESDAY, LocalDateTime.now(), LocalDateTime.now(), true),
                    new AvailableTime(Days.WEDNESDAY, LocalDateTime.now(), LocalDateTime.now(), true),
                    new AvailableTime(Days.THURSDAY, LocalDateTime.now(), LocalDateTime.now(), true),
                    new AvailableTime(Days.FRIDAY, LocalDateTime.now(), LocalDateTime.now(), true),
                    new AvailableTime(Days.SATURDAY, LocalDateTime.now(), LocalDateTime.now(), true),
                    new AvailableTime(Days.SUNDAY, LocalDateTime.now(), LocalDateTime.now(), true))
            , "mapUrl", "desc");
    public static final Detail DETAIL_2 = new Detail(
            List.of(new AvailableTime(Days.MONDAY, LocalDateTime.now(), LocalDateTime.now(), true),
                    new AvailableTime(Days.TUESDAY, LocalDateTime.now(), LocalDateTime.now(), true),
                    new AvailableTime(Days.WEDNESDAY, LocalDateTime.now(), LocalDateTime.now(), true),
                    new AvailableTime(Days.THURSDAY, LocalDateTime.now(), LocalDateTime.now(), true),
                    new AvailableTime(Days.FRIDAY, LocalDateTime.now(), LocalDateTime.now(), true),
                    new AvailableTime(Days.SATURDAY, LocalDateTime.now(), LocalDateTime.now(), true),
                    new AvailableTime(Days.SUNDAY, LocalDateTime.now(), LocalDateTime.now(), true))
            , "mapUrl", "desc");
    public static final Detail DETAIL_3 = new Detail(
            List.of(new AvailableTime(Days.MONDAY, LocalDateTime.now(), LocalDateTime.now(), true),
                    new AvailableTime(Days.TUESDAY, LocalDateTime.now(), LocalDateTime.now(), true),
                    new AvailableTime(Days.WEDNESDAY, LocalDateTime.now(), LocalDateTime.now(), true),
                    new AvailableTime(Days.THURSDAY, LocalDateTime.now(), LocalDateTime.now(), true),
                    new AvailableTime(Days.FRIDAY, LocalDateTime.now(), LocalDateTime.now(), true),
                    new AvailableTime(Days.SATURDAY, LocalDateTime.now(), LocalDateTime.now(), true),
                    new AvailableTime(Days.SUNDAY, LocalDateTime.now(), LocalDateTime.now(), true))
            , "mapUrl", "desc");
    public static final Detail DETAIL_4 = new Detail(
            List.of(new AvailableTime(Days.MONDAY, LocalDateTime.now(), LocalDateTime.now(), true),
                    new AvailableTime(Days.TUESDAY, LocalDateTime.now(), LocalDateTime.now(), true),
                    new AvailableTime(Days.WEDNESDAY, LocalDateTime.now(), LocalDateTime.now(), true),
                    new AvailableTime(Days.THURSDAY, LocalDateTime.now(), LocalDateTime.now(), true),
                    new AvailableTime(Days.FRIDAY, LocalDateTime.now(), LocalDateTime.now(), true),
                    new AvailableTime(Days.SATURDAY, LocalDateTime.now(), LocalDateTime.now(), true),
                    new AvailableTime(Days.SUNDAY, LocalDateTime.now(), LocalDateTime.now(), true))
            , "mapUrl", "desc");
    public static final Detail DETAIL_5 = new Detail(
            List.of(new AvailableTime(Days.MONDAY, LocalDateTime.now(), LocalDateTime.now(), true),
                    new AvailableTime(Days.TUESDAY, LocalDateTime.now(), LocalDateTime.now(), true),
                    new AvailableTime(Days.WEDNESDAY, LocalDateTime.now(), LocalDateTime.now(), true),
                    new AvailableTime(Days.THURSDAY, LocalDateTime.now(), LocalDateTime.now(), true),
                    new AvailableTime(Days.FRIDAY, LocalDateTime.now(), LocalDateTime.now(), true),
                    new AvailableTime(Days.SATURDAY, LocalDateTime.now(), LocalDateTime.now(), true),
                    new AvailableTime(Days.SUNDAY, LocalDateTime.now(), LocalDateTime.now(), true))
            , "mapUrl", "desc");
    public static final Images IMAGES_1 = new Images(List.of("image1", "image2"));
    public static final Images IMAGES_2 = new Images(List.of("image1", "image2"));
    public static final Images IMAGES_3 = new Images(List.of("image1", "image2"));
    public static final Images IMAGES_4 = new Images(List.of("image1", "image2"));
    public static final Images IMAGES_5 = new Images(List.of("image1", "image2"));
    public static final Cafe CAFE_1 = new Cafe(null, "카페1", "카페주소1", IMAGES_1, DETAIL_1, 10);
    public static final Cafe CAFE_2 = new Cafe(null, "카페2", "카페주소2", IMAGES_2, DETAIL_2, 11);
    public static final Cafe CAFE_3 = new Cafe(null, "카페3", "카페주소3", IMAGES_3, DETAIL_3, 12);
    public static final Cafe CAFE_4 = new Cafe(null, "카페4", "카페주소4", IMAGES_4, DETAIL_4, 13);
    public static final Cafe CAFE_5 = new Cafe(null, "카페5", "카페주소5", IMAGES_5, DETAIL_5, 14);
}
