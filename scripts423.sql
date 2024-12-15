SELECT s.name, s.age, f.name FROM public.student as s LEFT JOIN public.faculty as f ON s.faculty_id = f.id

SELECT s.name, s.age, f.name FROM public.student as s INNER JOIN public.faculty as f ON s.faculty_id = f.id