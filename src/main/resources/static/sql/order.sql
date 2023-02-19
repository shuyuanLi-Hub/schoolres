drop table if exists order_inf;

create table order_inf
(
    order_id int primary key auto_increment,
    status char(1),
    user_id int,
    shop_id int,
    rider_id int,
    order_dishes_id int,
    dishes_count int,
    order_date date,
    constraint order_inf_user_id_foreign foreign key(user_id) references user_inf(user_id),
    constraint order_inf_shop_id_foreign foreign key(shop_id) references shop_inf(shop_id),
    constraint order_inf_rider_id_foreign foreign key(rider_id) references rider_inf(rider_id),
    constraint order_inf_order_dishes_id_foreign foreign key(order_dishes_id) references order_dishes_inf(order_dishes_id)
);

drop procedure if exists save_order_and_order_dishes;
delimiter //
create procedure save_order_and_order_dishes(in dishes_id int, in count int, in user_id int, in shop_id int, in date date)
begin
    declare order_dishes_inf_last_insert_id int;
    declare order_inf_last_insert_id int;
    insert into order_dishes_inf value(null, dishes_id, count, null);
    select last_insert_id() into order_dishes_inf_last_insert_id;
    insert into order_inf value(null, user_id, shop_id, null, order_dishes_inf_last_insert_id, date, null);
    select last_insert_id() into order_inf_last_insert_id;
    update order_dishes_inf set order_id = order_inf_last_insert_id where order_dishes_id = order_dishes_inf_last_insert_id;
end//

delimiter ;


select * from order_inf order inner join order_dishes_inf dishes on order.order_id = dishes.order_id;

select * from user where name in
    (
        select A.name from
        (
            (
                select name from user group by name having count(name) > 1) as A
            )
    )
    ORDER BY name;
