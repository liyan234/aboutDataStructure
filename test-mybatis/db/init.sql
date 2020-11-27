drop database if exists stu_exam;
create database stu_exam default charset utf8mb4;

use stu_exam;

create table stu(
     id int primary key auto_increment,
     name varchar(20)
);

create table course(
     id int primary key auto_increment,
     name varchar(20)
);

create table record(
    id int primary key auto_increment,
    score decimal(4, 1), -- 150分以下，1位小数（总长度，小数长度）
    stu_id int,
    course_id int
);

insert into stu(name) value ('张三');
insert into course(name) value ('数学');
insert into record(score, stu_id, course_id) values (111, 1, 1);
insert into record(score, stu_id, course_id) values (131, 1, 1);



foreign key(stu_id) references stu(id),
    foreign key(course_id) references course(id)
