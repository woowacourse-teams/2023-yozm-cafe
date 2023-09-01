create table cafe
(
    like_count  integer      not null,
    id          bigint       not null auto_increment,
    address     varchar(255) not null,
    description text,
    map_url     varchar(255) not null,
    name        varchar(255) not null,
    phone       varchar(255),
    primary key (id)
) engine = InnoDB;

create table cafe_available_times
(
    close     time(6),
    is_opened bit                                                                           not null,
    open      time(6),
    cafe_id   bigint                                                                        not null,
    day       enum ('FRIDAY','MONDAY','SATURDAY','SUNDAY','THURSDAY','TUESDAY','WEDNESDAY') not null
) engine = InnoDB;

create table image
(
    cafe_id bigint not null,
    urls    varchar(255)
) engine = InnoDB;

create table liked_cafe
(
    cafe_id    bigint       not null,
    created_at datetime(6)  not null,
    id         bigint       not null auto_increment,
    member_id  varchar(255) not null,
    primary key (id)
) engine = InnoDB;

create table member
(
    id    varchar(255) not null,
    image varchar(255) not null,
    name  varchar(255) not null,
    primary key (id)
) engine = InnoDB;

create table menu
(
    is_recommended bit          not null,
    priority       integer      not null,
    cafe_id        bigint,
    id             bigint       not null auto_increment,
    description    varchar(255),
    image_url      varchar(255),
    name           varchar(255) not null,
    price          varchar(255) not null,
    primary key (id)
) engine = InnoDB;

create table menu_board
(
    priority  integer      not null,
    cafe_id   bigint,
    id        bigint       not null auto_increment,
    image_url varchar(255) not null,
    primary key (id)
) engine = InnoDB;

create table un_viewed_cafe
(
    cafe_id   bigint       not null,
    id        bigint       not null auto_increment,
    member_id varchar(255) not null,
    primary key (id)
) engine = InnoDB;

alter table cafe_available_times
    add constraint FK1eebpj2bed3mal340d8nou5xi
        foreign key (cafe_id)
            references cafe (id);

alter table image
    add constraint FKkthb3su6c118fakq82y94jjjr
        foreign key (cafe_id)
            references cafe (id);

alter table liked_cafe
    add constraint FKfncygvj9jkg45uh27u8pkc52s
        foreign key (cafe_id)
            references cafe (id);

alter table liked_cafe
    add constraint FK9dt8t5y60j1st5u92796ok6bi
        foreign key (member_id)
            references member (id);

alter table menu
    add constraint FKjjv0yqktkrnpmwrn9blohdt8f
        foreign key (cafe_id)
            references cafe (id);

alter table menu_board
    add constraint FK405r7r8rox1bg3uedcglecghd
        foreign key (cafe_id)
            references cafe (id);

alter table un_viewed_cafe
    add constraint FK3uxrbq5jgdug89g997qrhkjfx
        foreign key (cafe_id)
            references cafe (id);

alter table un_viewed_cafe
    add constraint FK6uiso4q9dkagnkqbpeyc3s200
        foreign key (member_id)
            references member (id)
