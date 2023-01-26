alter sequence hibernate_sequence start with 1;

insert into test.public.users (id, login, password, role, status) values
(1,'User','$2a$10$16NaobZtzUFNpE9ib7jPB.AD1v5JNsgwglP7grdjFGY0SizydVyHO','USER','ACTIVE'),
(2,'User2','$2a$10$16NaobZtzUFNpE9ib7jPB.AD1v5JNsgwglP7grdjFGY0SizydVyHO','USER','ACTIVE');

insert into test.public.notes (id, content, date, topic, user_id) VALUES
(1,'Test1','2023-01-20 12:43:01.000000','TopicTest',1),
(2,'Test2','2023-01-21 12:43:01.000000','TopicTest',1),
(3,'Test3','2023-01-23 12:43:01.000000','TopicTest',1),
(8,'Test8','2023-01-21 12:43:01.000000','TopicTest8',2);
