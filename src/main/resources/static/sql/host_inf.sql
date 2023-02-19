
drop table  if exists host_inf;

create table host_inf
(
    host_id int primary key auto_increment,
    host_name varchar(255) unique,
    host_passwd varchar(255),
    host_date date,
    host_gender char(1),
    host_phone varchar(255),
    host_photo varchar(255)
);