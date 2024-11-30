SELECT * FROM public.student as s WHERE s.age>=10 AND s.age<=20

SELECT s.name FROM public.student as s

SELECT * FROM public.student as s WHERE s.name LIKE '%h%'

SELECT * FROM public.student as s WHERE s.age<s.id

SELECT * FROM public.student as s ORDER BY s.age
