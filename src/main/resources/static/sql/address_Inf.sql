
drop table if exists address_inf;

create table address_inf
(
    address_id int primary key auto_increment,
    address_detail varchar(255) not null,
    user_id int,
    constraint address_user_foreign foreign key(user_id) references user_inf(user_id)
);