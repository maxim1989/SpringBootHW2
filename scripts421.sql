ALTER TABLE public.student ADD CONSTRAINT age_constraint CHECK (age >= 16);

ALTER TABLE public.student ALTER COLUMN name SET NOT NULL
ALTER TABLE public.student ADD CONSTRAINT name_unique UNIQUE (name)

ALTER TABLE public.faculty ADD CONSTRAINT name_color_unique UNIQUE (name, color)

ALTER TABLE public.student ALTER COLUMN name SET DEFAULT 20