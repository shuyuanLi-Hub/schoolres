
drop table if exists order_dishes_inf;

create table order_dishes_inf
(
    order_dishes_id int primary key auto_increment,
    dishes_id int,
    order_dishes_count int,
    order_id int,
    constraint order_dishes_inf_dishes_id_ref foreign key(dishes_id) references dishes_inf(dishes_id),
    constraint order_dishes_inf_order_id_ref foreign key(order_id) references order_inf(order_id)
);