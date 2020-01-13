DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM restaurants;
DELETE FROM menu;
DELETE FROM dishes;
DELETE FROM votes;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
('Admin', 'admin@gmail.com', '{noop}admin'),     -- id=100000
('User1', 'user1@yandex.ru', '{noop}password'),  -- id=100001
('User2', 'user2@yandex.ru', '{noop}password');  -- id=100002

INSERT INTO user_roles (role, user_id) VALUES
('ROLE_ADMIN', 100000),
('ROLE_USER', 100001),
('ROLE_USER', 100002);

INSERT INTO restaurants (name) VALUES
('KFC'),                                        -- id=100003
('Starbucks'),                                  -- id=100004
('Krispy Kreme');                               -- id=100005

INSERT INTO menu (date, restaurant_id) VALUES
('2020-01-01', 100003),                        -- id=100006
('2020-01-01', 100004),                        -- id=100007
('2020-01-01', 100005),                        -- id=100008
(current_date, 100003),                        -- id=100009
(current_date, 100004),                        -- id=100010
(current_date, 100005);                        -- id=100011

INSERT INTO dishes (name, price, menu_id) VALUES
('Chicken', 180, 100006),                      -- id=100012
('Pepsi', 120, 100006),                        -- id=100013
('Маффин', 432, 100007),                       -- id=100014
('Эспрессо', 240, 100007),                     -- id=100015
('Круассан', 245, 100008),                     -- id=100016
('Лимонный сок', 30, 100008),                  -- id=100017
('Basket of Chickens', 500, 100009),           -- id=100018
('Big Pepsi', 250, 100009),                    -- id=100019
('Кекс', 231, 100010),                         -- id=100020
('Капучино', 156, 100010),                     -- id=100021
('Круассан двойнй', 650, 100011),              -- id=100022
('Компот', 100, 100011);                       -- id=100023

INSERT INTO votes (date, user_id, menu_id) VALUES
('2020-01-01', 100000, 100008),               -- id=100024
(current_date, 100000, 100009),               -- id=100025
('2020-01-01', 100001, 100008),               -- id=100026
(current_date, 100001, 100010),               -- id=100027
('2020-01-01', 100002, 100006),               -- id=100028
(current_date, 100002, 100010);                 -- id=100029