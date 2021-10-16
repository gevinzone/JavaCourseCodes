create table school
(
    id int auto_increment primary key,
    name varchar(128) not null
);

create table Klass
(
    id int auto_increment primary key,
    name varchar(128) null,
    school int null,
    create_time datetime default CURRENT_TIMESTAMP not null,
    update_time datetime default CURRENT_TIMESTAMP not null,
    constraint Klass_ibfk_1 foreign key (school) references school (id)
);

create index school on Klass (school);

create table student
(
    id int auto_increment primary key,
    name varchar(64) not null,
    klass int null,
    create_time datetime default CURRENT_TIMESTAMP not null,
    update_time datetime default CURRENT_TIMESTAMP not null,
    constraint student_ibfk_1 foreign key (klass) references Klass (id)
);

create index klass on student (klass);





