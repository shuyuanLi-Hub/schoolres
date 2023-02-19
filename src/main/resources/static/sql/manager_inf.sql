
drop table if exists manager_inf;

create table manager_inf
(
    manager_id int primary key auto_increment,
    manager_name varchar(255),
    manager_passwd varchar(255)
);