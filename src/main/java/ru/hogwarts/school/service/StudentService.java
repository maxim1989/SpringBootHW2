package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class StudentService {
    private final Map<Long, Student> studentMap = new HashMap<Long, Student>();
    private Long counter = 0L;

    public Student create(Student student) {
        counter++;
        student.setId(counter);
        studentMap.put(counter, student);
        return studentMap.getOrDefault(counter, null);
    }

    public Student read(Long id) {
        return studentMap.getOrDefault(id, null);
    }

    public Student update(Long id, Student student) {
        studentMap.put(id, student);
        return studentMap.getOrDefault(id, null);
    }

    public Student delete(Long id) {
        return studentMap.remove(id);
    }
}
