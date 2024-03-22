CREATE SCHEMA IF NOT EXISTS sqlinjection;

SET SEARCH_PATH TO sqlinjection,public;

insert into book (title)
values ('Head First Java'),
       ('Effective Java'),
       ('You Don''t Know JS. Up & Going'),
       ('Some Title 1'),
       ('Some Title 2'),
       ('Some Title 3'),
       ('What if % or _ is present in the title?')
;

insert into book (title)
values (E'\u0027');

select * from book where title = '';

select * from book;

select * from book where title like '%'||E'\u0027'||'%';


SELECT pg_stat_statements_reset();

select * from pg_stat_statements;