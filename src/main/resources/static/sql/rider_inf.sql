
drop table if exists rider_inf;

create table rider_inf
(
    rider_id int primary key auto_increment,
    rider_session_id varchar(255)
);