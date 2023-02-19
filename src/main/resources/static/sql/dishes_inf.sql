
drop table if exists dishes_inf;

create table dishes_inf
(
    dishes_id int primary key auto_increment,
    dishes_name varchar(255),
    dishes_desc varchar(255),
    dishes_price decimal(5,2),
    dishes_category int,
    order_dishes_id int,
    shop_id int references shop_inf(shop_id),
    constraint dishes_inf_order_dishes_id_ref foreign key(order_dishes_id) references order_dishes_inf(order_dishes_id);
);