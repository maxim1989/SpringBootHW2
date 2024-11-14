package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.NotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FacultyService {
    final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty create(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty read(Long id) {
        return facultyRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public Faculty update(Long id, Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public void delete(Long id) {
        facultyRepository.deleteById(id);
    }

    public List<Faculty> filterByColor(String color) {
        return facultyRepository.findByColor(color);
    }

    public List<Faculty> filterByName(String name) {
        return facultyRepository.findByName(name);
    }

    public Set<Student> getStudentsByFaculty(Long id) {
        return facultyRepository
                .findById(id)
                .orElseThrow(NotFoundException::new).getStudents();
    }
}
