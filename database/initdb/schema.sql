create table user_entity
(
    user_id  varchar(50)  not null
        primary key,
    email    varchar(255) null,
    name     varchar(50)  null,
    password varchar(255) null,
    role     varchar(255) null
);

create table post_entity
(
    id      bigint auto_increment
        primary key,
    body    text        null,
    title   varchar(50) null,
    user_id varchar(50) null,
    constraint FK2jmp42lmrw2f3ljd16f1re3c8
        foreign key (user_id) references user_entity (user_id)
);

create table follow_entity
(
    id        bigint auto_increment
        primary key,
    from_user varchar(50) null,
    to_user   varchar(50) null,
    constraint FK7vpjo51fjlrplmuaucmgvtdi3
        foreign key (to_user) references user_entity (user_id),
    constraint FKh9ygjr1o4n27ne1wp6ix876qw
        foreign key (from_user) references user_entity (user_id)
);

create table comment
(
    id      bigint auto_increment
        primary key,
    comment varchar(255) null,
    post_id bigint       null,
    user_id varchar(50)  null,
    constraint FK1lu40jpbyq7dnu9ajph3f0kci
        foreign key (post_id) references post_entity (id),
    constraint FKl4xlhaqa07wrvf446sjwngh8j
        foreign key (user_id) references user_entity (user_id)
);