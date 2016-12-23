INSERT INTO BATTLEUSERS (id,username, password, enabled) VALUES (1, 'user', 'user',1);
INSERT INTO AUTHORITIES (id, authority, users_id) VALUES (1,'ROLE_USER',1);
INSERT INTO BATTLEUSERS (id,username, password, enabled) VALUES (2, 'sveta', 'sveta',1);
INSERT INTO AUTHORITIES (id, authority, users_id) VALUES (2,'ROLE_USER',2);
INSERT INTO BATTLEUSERS (id,username, password, enabled) VALUES (3, 'andrey', 'user',1);
INSERT INTO AUTHORITIES (id, authority, users_id) VALUES (3,'ROLE_USER',3);
INSERT INTO AUTHORITIES (id, authority, users_id) VALUES (4,'ROLE_ADMIN',3);

INSERT INTO LOG (id, game_num, player, shoot, shoot_res) VALUES (1, 1, 'user', 'a1', 'killed');
INSERT INTO LOG (id, game_num, player, shoot, shoot_res) VALUES (2, 1, 'computer', 'a3', 'empty');
INSERT INTO LOG (id, game_num, player, shoot, shoot_res) VALUES (3, 1, 'user', 'a5', 'got');
INSERT INTO LOG (id, game_num, player, shoot, shoot_res) VALUES (4, 1, 'user', 'a6', 'killed');