drop database if exists musicserver;
create database if not exists musicserver;

use musicserver;

drop table if exists music;
create table music (
    id int primary key auto_increment,
    title varchar(50) not null,
    singer varchar(50) not null,
    time varchar(13) not null,
    url varchar(100) not null,
    userId int(11) not null
);

drop table if exists user;
create table user(
    id int primary key auto_increment,
    username varchar(20) not null,
    password varchar(32) not null,
    age int not null,
    gender varchar(2) not null,
    email varchar(50) not null
);

drop table if exists mv;
create table mv(
    id int primary key auto_increment,
    mvname varchar(20) not null,
    time varchar(20) not null,
    url varchar(20) not null,
    userId int(11) not null
);


drop table if exists lovermusic;
create table lovermusic(
    id int primary key auto_increment,
    user_id int(11) not null,
    music_id int(11) not null
);

drop table if exists lovermv;
create table lovermv(
    id int primary key auto_increment,
    user_id int(11) not null,
    mv_id int(11) not null
);

