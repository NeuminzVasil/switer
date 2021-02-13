insert into customers (login, password, active)
values ('PetrovPP', '100', true);

insert into authorities (authority, customer_id)
values ('ROLE_ADMIN', 1);
