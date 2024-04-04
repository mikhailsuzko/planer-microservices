-- liquibase formatted sql

-- changeset msuzko:init_data

insert into public.category (id, title, user_id)
values (100, 'Sport', 'userId'),
       (101, 'Home', 'userId'),
       (102, 'Travelling', 'userId'),
       (103, 'Hobby', 'userId');

insert into priority (id, title, color, user_id)
values (100, 'High', '#FBBABA', 'userId'),
       (101, 'Medium', '#CFF4CF', 'userId'),
       (102, 'Low', '#CCE7FF', 'userId');

insert into stat (id, completed_total, uncompleted_total, user_id)
values (100, 0, 0, 'userId');

insert into task (id, title, completed, task_date, category_id, priority_id, user_id)
values (100, 'Read book', 0, null, 100, 100, 'userId'),
       (101, 'Walk', 0, null, 102, 101, 'userId'),
       (102, 'Swim', 1, null, 100, 102, 'userId'),
       (103, 'Report', 0, null, 101, 100, 'userId'),
       (104, 'Fishing', 1, null, 103, 101, 'userId');

-- userId2

insert into public.category (id, title, user_id, completed_count, uncompleted_count)
values (200, 'Sport', 'userId2', 0, 0),
       (201, 'Home', 'userId2', 0, 0),
       (202, 'Travelling', 'userId2', 0, 0),
       (203, 'Hobby', 'userId2', 0, 0);

insert into priority (id, title, color, user_id)
values (200, 'High', '#FBBABA', 'userId2'),
       (201, 'Medium', '#CFF4CF', 'userId2'),
       (202, 'Low', '#CCE7FF', 'userId2');

insert into stat (id, completed_total, uncompleted_total, user_id)
values (200, 0, 0, 'userId2');

insert into task (id, title, completed, task_date, category_id, priority_id, user_id)
values (200, 'Read book', 0, null, null, null, 'userId2'),
       (201, 'Walk', 0, null, null, null, 'userId2'),
       (202, 'Swim', 0, null, null, null, 'userId2'),
       (203, 'Report', 0, null, null, null, 'userId2'),
       (204, 'Fishing', 0, null, null, null, 'userId2');

-- userId3

insert into public.category (id, title, user_id, completed_count, uncompleted_count)
values (300, 'Sport', 'userId3', 0, 0),
       (301, 'Home', 'userId3', 0, 0),
       (302, 'Travelling', 'userId3', 0, 0),
       (303, 'Hobby', 'userId3', 0, 0);

insert into priority (id, title, color, user_id)
values (300, 'High', '#FBBABA', 'userId3'),
       (301, 'Medium', '#CFF4CF', 'userId3'),
       (302, 'Low', '#CCE7FF', 'userId3');

insert into stat (id, completed_total, uncompleted_total, user_id)
values (300, 0, 0, 'userId3');

insert into task (id, title, completed, task_date, category_id, priority_id, user_id)
values (300, 'Read book', 0, null, null, null, 'userId3'),
       (301, 'Walk', 0, null, null, null, 'userId3'),
       (302, 'Swim', 0, null, null, null, 'userId3'),
       (303, 'Report', 0, null, null, null, 'userId3'),
       (304, 'Fishing', 0, null, null, null, 'userId3');

SELECT setval('category_id_seq', (select max(id) from category), true);
SELECT setval('priority_id_seq', (select max(id) from priority), true);
SELECT setval('stat_id_seq', (select max(id) from stat), true);
SELECT setval('task_id_seq', (select max(id) from task), true);
