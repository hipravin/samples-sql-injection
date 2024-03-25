CREATE SCHEMA IF NOT EXISTS sqlinjection;

SET SEARCH_PATH TO sqlinjection,public;

insert into book (title)
values ('Head First Java'),
       ('Another Some Title 4'),
       ('You Don''t Know JS. Up & Going'),
       ('Some Title 1'),
       ('Some Title 3'),
       ('Some Title 2'),
       ('What if % or _ is present in the title?')

;

truncate book;

select * from book where title like 'Some Title%';

select * from book where title like 'What if \% or \_ is present in the title?' escape '\';
select * from book where title like 'What if |% or |_ is present in the title?' escape '|';

select * from book where title ~ '^S{1,3}.+\d+$';

select * from book where title = '';

select * from book;

select * from book where title like '%'||E'\u0027'||'%';

select * from book where title like 'What if \% or \_ is present in the title?%' escape '\';

SELECT pg_stat_statements_reset();

select * from pg_stat_statements where query like '%book%';