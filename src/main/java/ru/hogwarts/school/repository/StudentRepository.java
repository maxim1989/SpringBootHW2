package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Student;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByAge(int position);

    List<Student> findByAgeBetween(Integer min, Integer max);

    @Query(value = "SELECT COUNT(*) as amount FROM student", nativeQuery = true)
    StudentsAmount getStudentAmount();

    @Query(value = "SELECT AVG(age) as avgAge FROM student" , nativeQuery = true)
    StudentsAvgAge getStudentAvgAge();

    @Query(value = "SELECT id, name, age  FROM student ORDER BY id DESC LIMIT 5", nativeQuery = true)
    List<LastFiveStudents> getLastFiveStudents();
}
