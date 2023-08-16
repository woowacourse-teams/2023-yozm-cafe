create table if not exists `yozm-cafe`.cafe
(
    like_count  int default 0 not null,
    id          bigint auto_increment primary key,
    address     varchar(50)   not null,
    description text          null,
    map_url     varchar(512)  not null,
    name        varchar(20)   not null,
    phone       varchar(20)   null
);

create table if not exists `yozm-cafe`.cafe_available_times
(
    is_opened bit                                                                                 not null,
    cafe_id   bigint                                                                              not null,
    close     time(6)                                                                             null,
    open      time(6)                                                                             null,
    day       enum ('FRIDAY', 'MONDAY', 'SATURDAY', 'SUNDAY', 'THURSDAY', 'TUESDAY', 'WEDNESDAY') not null,
    constraint cafe_available_times_CAFE_ID
        foreign key (cafe_id) references cafe (id)
);

create table if not exists `yozm-cafe`.image
(
    cafe_id bigint       not null,
    urls    varchar(512) null,
    constraint image_CAFE_ID
        foreign key (cafe_id) references cafe (id)
);

create table if not exists `yozm-cafe`.member
(
    id    varchar(30)  not null primary key,
    image varchar(512) not null,
    name  varchar(30)  not null
);

create table if not exists `yozm-cafe`.liked_cafe
(
    id        bigint auto_increment primary key,
    cafe_id   bigint      not null,
    member_id varchar(30) not null,
    created_at datetime(6) not null,
    constraint liked_cafe_MEMBER_ID
        foreign key (member_id) references member (id),
    constraint liked_cafe_CAFE_ID
        foreign key (cafe_id) references cafe (id)
);

create table if not exists `yozm-cafe`.un_viewed_cafe
(
    id        bigint auto_increment primary key,
    cafe_id   bigint      not null,
    member_id varchar(30) not null,
    constraint un_viewed_cafe_CAFE_ID
        foreign key (cafe_id) references cafe (id),
    constraint un_viewed_cafe_MEMBER_ID
        foreign key (member_id) references member (id)
);
