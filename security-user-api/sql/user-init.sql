drop table if exists t_user;
create table t_user(
    id bigint unsigned not null auto_increment,
    name varchar(255) not null default '',
    primary key (id)
);

insert into t_user(name) values ('Alice'), ('Bob');

insert into t_user(name) values ('Cindy');

select * from t_user;

select id, name from t_user where name = 'alice';