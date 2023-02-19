drop table if exists user_inf;

create table user_inf
(
    user_id int primary key auto_increment,
    user_name varchar(255),
    user_date date,
    user_passwd varchar(255),
    user_gender char(1),
    user_phone char(11),
    user_email varchar(255)
);