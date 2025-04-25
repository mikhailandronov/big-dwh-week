--создание таблицы
create table student_grades (
    name varchar,
    subject varchar,
    grade int
);

-- наполнение таблицы данными
insert into student_grades (
values
    ('Петя', 'русский', 4),
    ('Петя', 'физика', 5),
    ('Петя', 'история', 4),
    ('Маша', 'математика', 4),
    ('Маша', 'русский', 3),
    ('Маша', 'физика', 5),
    ('Маша', 'история', 3)
);

--запрос всех данных из таблицы
select * from student_grades;

select
    name, subject, grade, row_number() over student
from
    student_grades
window
    student as (partition by (name) order by (grade))