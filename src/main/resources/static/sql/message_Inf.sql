
drop table if exists message_inf;

create table message_inf
(
    message_id int  primary key auto_increment,
    message_content varchar(255),
    message_time datetime,
    user_id int references user_inf(user_id),
    rider_id int references rider_inf(rider_id),
    message_tag char(1),
    constraint user_id_foreign_key foreign key(user_id) references user_inf(user_id) on delete cascade on update cascade,
    constraint rider_id_foreign_key foreign key(rider_id) references rider_inf(rider_id) on delete cascade on update cascade
);