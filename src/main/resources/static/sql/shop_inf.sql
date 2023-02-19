
drop table if exists shop_inf;

create table shop_inf
(
    shop_id int primary key auto_increment,
    shop_name varchar(255),
    shop_category int,
    shop_owner int references host_inf(host_id)
);