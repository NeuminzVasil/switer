create extension if NOT EXISTS pgcrypto;

update switer.public.customers set password = crypt(password, gen_salt('bf', 8));