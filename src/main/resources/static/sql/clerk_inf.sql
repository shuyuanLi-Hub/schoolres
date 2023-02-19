
drop table if exists clerk_inf;

create table clerk_inf
(
    clerk_id int primary key auto_increment,
    clerk_name varchar(255),
    clerk_date date,
    clerk_phone varchar(255),
    clerk_gender char(1),
    clerk_photo varchar(255),
    shop_id int references shop_inf(shop_id)
);