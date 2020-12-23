drop database if exists orderfoodsystem;
create database orderfoodsystem;

use orderfoodsystem;
-- 菜品表
drop table if exists veg;
create table veg (
     vegId int primary key auto_increment,
     name varchar(20),
     price int
);
insert into veg values(null, "宫保鸡丁", 2800);
insert into veg values(null, "红烧肉", 4800);
insert into veg values(null, "梅菜扣肉", 4800);
insert into veg values(null, "地三鲜", 2300);
insert into veg values(null, "鱼香茄子", 2200);
insert into veg values(null, "鱼香肉丝", 3800);
insert into veg values(null, "土豆烧肉", 3800);
insert into veg values(null, "大盘鸡", 8800);
insert into veg values(null, "凉拼", 1800);

--分为单位

-- user表
drop table if exists user;
create table user (
      userId int primary key auto_increment,
      name varchar(20) unique,
      password varchar(20),
      isAdmin int
);
insert into user values (null, 123, 123, 1);
 --0表示普通用户 1表示管理员

--订单表 与user表存在外键的联系
drop table if exists orders;
create table orders(
      ordersId int primary key auto_increment,
      userId int,
      ordertime datetime,
      isfinish int,
      foreign key(userId) references user(userId)
);
-- 表示时间
-- 0表示未完成，1表示完成

-- 订单与菜品表存在外键的联系 中间表
drop table if exists orders_veg;
create table orders_veg(
      ordersId int,
      vegId  int,
      foreign key(ordersId) references orders(ordersId),
      foreign key(vegId) references veg(vegId)
);