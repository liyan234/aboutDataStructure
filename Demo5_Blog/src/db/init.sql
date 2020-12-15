
drop database if exists servlet_blog;
create database servlet_blog character set utf8mb4;

use servlet_blog;

create table user (
   id int primary key  auto_increment,
   username varchar(20) not null unique,
   password varchar(20) not null,
   nickname varchar(20),
   sex bit,
   birthday date ,
   head varchar(20)
);

create table article(
   id int primary key auto_increment,
   title varchar(20) not null ,
   content mediumtext not null,

   create_time timestamp default now(),
   view_count int default 0,
   user_id int,
   foreign key(user_id) references user(id)
);

-- 主外键关联的表，默认创建的表的主外键是restrict严格模式，具有从连关系
-- 如果从表有数据关联到某一行数据，那x不可以删
-- 真实的设计上是不删除物理，在每一个表上设计上一个字段，表示是否有效


insert into user(username, password) values ('a', '1');
insert into user(username, password) values ('b', '2');
insert into user(username, password) values ('c', '3');


insert into article(title, content, user_id) values ('堆排序', 'public****', 2);
insert into article(title, content, user_id) values ('冒泡排序', 'public****', 2);
insert into article(title, content, user_id) values ('选择排序', 'public****', 2);
insert into article(title, content, user_id) values ('插入排序', 'public****', 1);
insert into article(title, content, user_id) values ('归并排序', 'public****', 1);
insert into article(title, content, user_id) values ('快速排序', 'public****', 1);
