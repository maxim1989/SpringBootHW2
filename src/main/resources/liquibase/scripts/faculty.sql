-- liquibase formatted sql

-- changeset maksimf:1
CREATE INDEX users_sh_idx ON public.faculty (name, color);