-- liquibase formatted sql

-- changeset maksimf:1
CREATE INDEX student_name_index ON public.student (name);