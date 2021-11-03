INSERT INTO state (date_create, date_update, initials, name) VALUES (current_timestamp, current_timestamp, 'AC', 'Acre');
INSERT INTO state (date_create, date_update, initials, name) VALUES (current_timestamp, current_timestamp, 'AL', 'Alagoas');
INSERT INTO state (date_create, date_update, initials, name) VALUES (current_timestamp, current_timestamp, 'AP', 'Amapá');
INSERT INTO state (date_create, date_update, initials, name) VALUES (current_timestamp, current_timestamp, 'AM', 'Amazonas');
INSERT INTO state (date_create, date_update, initials, name) VALUES (current_timestamp, current_timestamp, 'BA', 'Bahia');
INSERT INTO state (date_create, date_update, initials, name) VALUES (current_timestamp, current_timestamp, 'CE', 'Ceará');
INSERT INTO state (date_create, date_update, initials, name) VALUES (current_timestamp, current_timestamp, 'DF', 'Distrito Federal');
INSERT INTO state (date_create, date_update, initials, name) VALUES (current_timestamp, current_timestamp, 'ES', 'Espírito Santo');
INSERT INTO state (date_create, date_update, initials, name) VALUES (current_timestamp, current_timestamp, 'GO', 'Goiás');
INSERT INTO state (date_create, date_update, initials, name) VALUES (current_timestamp, current_timestamp, 'MA', 'Maranhão');
INSERT INTO state (date_create, date_update, initials, name) VALUES (current_timestamp, current_timestamp, 'MT', 'Mato Grosso');
INSERT INTO state (date_create, date_update, initials, name) VALUES (current_timestamp, current_timestamp, 'MS', 'Mato Grosso do Sul');
INSERT INTO state (date_create, date_update, initials, name) VALUES (current_timestamp, current_timestamp, 'MG', 'Minas Gerais');
INSERT INTO state (date_create, date_update, initials, name) VALUES (current_timestamp, current_timestamp, 'PA', 'Pará');
INSERT INTO state (date_create, date_update, initials, name) VALUES (current_timestamp, current_timestamp, 'PB', 'Paraíba');
INSERT INTO state (date_create, date_update, initials, name) VALUES (current_timestamp, current_timestamp, 'PR', 'Paraná');
INSERT INTO state (date_create, date_update, initials, name) VALUES (current_timestamp, current_timestamp, 'PE', 'Pernambuco');
INSERT INTO state (date_create, date_update, initials, name) VALUES (current_timestamp, current_timestamp, 'PI', 'Piauí');
INSERT INTO state (date_create, date_update, initials, name) VALUES (current_timestamp, current_timestamp, 'RJ', 'Rio de Janeiro');
INSERT INTO state (date_create, date_update, initials, name) VALUES (current_timestamp, current_timestamp, 'RN', 'Rio Grande do Norte');
INSERT INTO state (date_create, date_update, initials, name) VALUES (current_timestamp, current_timestamp, 'RS', 'Rio Grande do Sul');
INSERT INTO state (date_create, date_update, initials, name) VALUES (current_timestamp, current_timestamp, 'RO', 'Rondônia');
INSERT INTO state (date_create, date_update, initials, name) VALUES (current_timestamp, current_timestamp, 'RR', 'Roraima');
INSERT INTO state (date_create, date_update, initials, name) VALUES (current_timestamp, current_timestamp, 'SC', 'Santa Catarina');
INSERT INTO state (date_create, date_update, initials, name) VALUES (current_timestamp, current_timestamp, 'SP', 'São Paulo');
INSERT INTO state (date_create, date_update, initials, name) VALUES (current_timestamp, current_timestamp, 'SE', 'Sergipe');
INSERT INTO state (date_create, date_update, initials, name) VALUES (current_timestamp, current_timestamp, 'TO', 'Tocantins');


INSERT INTO city (date_create, date_update, name, state_id) VALUES (current_timestamp, current_timestamp, 'Brasília', 7);
INSERT INTO city (date_create, date_update, name, state_id) VALUES (current_timestamp, current_timestamp, 'Taguatinga', 7);
INSERT INTO city (date_create, date_update, name, state_id) VALUES (current_timestamp, current_timestamp, 'Ceilândia', 7);


INSERT INTO address (date_create, date_update, city_id, zip_code, place, longitude, latitude, district) VALUES (current_timestamp, current_timestamp, 1, '11111-000', 'Teste Teste', 10, 15, 'Teste');


INSERT INTO user_barber (date_create, date_update, name, email, password, phone, enabled) VALUES (current_timestamp, current_timestamp, 'John Doe', 'johndoe@hotmail.com', '$2a$10$GDnd2HME/FoumxHg6SEZ2u.bmrVp7hsyVxv5Di3KkZeegCKRoXf.C', '61912345678', true);
INSERT INTO user_barber (date_create, date_update, name, email, password, phone, enabled) VALUES (current_timestamp, current_timestamp, 'Doe John', 'doejohn@hotmail.com', '$2a$10$GDnd2HME/FoumxHg6SEZ2u.bmrVp7hsyVxv5Di3KkZeegCKRoXf.C', '61987654321', true);


INSERT INTO permission_group (date_create, date_update, name) VALUES (current_timestamp, current_timestamp, 'GROUP_ADMIN');
INSERT INTO permission_group (date_create, date_update, name) VALUES (current_timestamp, current_timestamp, 'GROUP_USER');


INSERT INTO permission (date_create, date_update, description, name, permission_group_id) VALUES (current_timestamp, current_timestamp, 'Request POST', 'MAKE_POST', 1);
INSERT INTO permission (date_create, date_update, description, name, permission_group_id) VALUES (current_timestamp, current_timestamp, 'Request PUT', 'MAKE_PUT', 1);
INSERT INTO permission (date_create, date_update, description, name, permission_group_id) VALUES (current_timestamp, current_timestamp, 'Request DELETE', 'MAKE_DELETE', 1);
INSERT INTO permission (date_create, date_update, description, name, permission_group_id) VALUES (current_timestamp, current_timestamp, 'Request GET', 'MAKE_GET', 2);


INSERT INTO user_barber_permission_group (user_barber_id, permission_group_id) VALUES (1, 1);
INSERT INTO user_barber_permission_group (user_barber_id, permission_group_id) VALUES (2, 2);
