package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.dto.FacultyDto;
import ru.hogwarts.school.dto.StudentCreateDto;
import ru.hogwarts.school.dto.StudentDto;
import ru.hogwarts.school.dto.StudentFacultyDto;
import ru.hogwarts.school.exception.NotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.LastFiveStudents;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.repository.StudentsAmount;
import ru.hogwarts.school.repository.StudentsAvgAge;
import ru.hogwarts.school.utils.MappingUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentService {
    final StudentRepository studentRepository;
    final MappingUtils mappingUtils;

    public StudentService(StudentRepository studentRepository, MappingUtils mappingUtils) {
        this.studentRepository = studentRepository;
        this.mappingUtils = mappingUtils;
    }

    public StudentDto create(StudentCreateDto studentDto) {
        Student mapper = mappingUtils.mapToStudentCreate(studentDto);
        Student student = studentRepository.save(mapper);
        return mappingUtils.mapToStudentDto(student);
    }

    public StudentDto read(Long id) {
        Student student = studentRepository.findById(id).orElseThrow(NotFoundException::new);
        return mappingUtils.mapToStudentDto(student);
    }

    public StudentDto update(StudentDto studentDto) {
        Student mapper = mappingUtils.mapToStudent(studentDto);
        Student student = studentRepository.save(mapper);
        return mappingUtils.mapToStudentDto(student);
    }

    public void delete(Long id) {
        studentRepository.deleteById(id);
    }

    public List<StudentDto> filterByAge(Integer age) {
        List<Student> studentList = studentRepository.findByAge(age);
        List<StudentDto> studentDtoList = new ArrayList<>();
        for (final Student student : studentList) {
            studentDtoList.add(mappingUtils.mapToStudentDto(student));
        }
        return studentDtoList;
    }

    public List<StudentDto> filterByAgeRange(Integer min, Integer max) {
        List<Student> studentList = studentRepository.findByAgeBetween(min, max);
        List<StudentDto> studentDtoList = new ArrayList<>();
        for (final Student student : studentList) {
            studentDtoList.add(mappingUtils.mapToStudentDto(student));
        }
        return studentDtoList;
    }

    public FacultyDto getStudentFaculty(Long id) {
        Faculty faculty = studentRepository
                .findById(id)
                .orElseThrow(NotFoundException::new)
                .getFaculty();
        return mappingUtils.mapToFacultyDto(faculty);
    }

    public StudentsAmount getStudentAmount() {
        return studentRepository.getStudentAmount();
    }

    public StudentsAvgAge getStudentAvgAge() {
        return studentRepository.getStudentAvgAge();
    }

    public List<LastFiveStudents> getLastFiveStudents() {
        return studentRepository.getLastFiveStudents();
    }
}
