drop database if exists blogdemo;
create database blogdemo;

use blogdemo;

drop table if exists user;
create table user(
    userId int primary key auto_increment,
    name varchar(50) unique,
    password varchar(50)
);

drop table if exists article;
create table article(
     articleId int primary key auto_increment,
     title varchar(255),
     content text,
     userId int,
     foreign key(userId) references user(userId)
);