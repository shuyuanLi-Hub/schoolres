
drop table if exists hot_dishes_inf;

create table hot_dishes_inf
(
    hot_dishes_id int primary key auto_increment,
    hot int,
    dishes_id int,
    constraint hot_dishes_foreign_key foreign key(dishes_id) references dishes_inf(dishes_id)
);