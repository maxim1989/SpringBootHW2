package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FacultyService {
    private final Map<Long, Faculty> facultyMap = new HashMap<Long, Faculty>();
    private Long counter = 0L;

    public Faculty create(Faculty faculty) {
        counter++;
        faculty.setId(counter);
        facultyMap.put(counter, faculty);
        return facultyMap.getOrDefault(counter, null);
    }

    public Faculty read(Long id) {
        return facultyMap.getOrDefault(id, null);
    }

    public Faculty update(Long id, Faculty faculty) {
        facultyMap.put(id, faculty);
        return facultyMap.getOrDefault(id, null);
    }

    public Faculty delete(Long id) {
        return facultyMap.remove(id);
    }

    public List<Faculty> filterByColor(String color) {
        return new ArrayList<Faculty>(
                facultyMap.values()).stream().filter(student -> Objects.equals(student.getColor(), color)
        ).collect(Collectors.toList());
    }
}
