package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.dto.FacultyDto;
import ru.hogwarts.school.dto.StudentCreateDto;
import ru.hogwarts.school.dto.StudentDto;
import ru.hogwarts.school.exception.NotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.LastFiveStudents;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.repository.StudentsAmount;
import ru.hogwarts.school.repository.StudentsAvgAge;
import ru.hogwarts.school.utils.MappingUtils;

import java.util.*;

@Service
public class StudentService {
    final StudentRepository studentRepository;
    final MappingUtils mappingUtils;
    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

    public StudentService(StudentRepository studentRepository, MappingUtils mappingUtils) {
        this.studentRepository = studentRepository;
        this.mappingUtils = mappingUtils;
    }

    public StudentDto create(StudentCreateDto studentDto) {
        logger.info("StudentService create was invoked");
        Student mapper = mappingUtils.mapToStudentCreate(studentDto);
        Student student = studentRepository.save(mapper);
        return mappingUtils.mapToStudentDto(student);
    }

    public StudentDto read(Long id) {
        logger.info("StudentService read was invoked");
        Student student = studentRepository.findById(id).orElseThrow(NotFoundException::new);
        return mappingUtils.mapToStudentDto(student);
    }

    public StudentDto update(StudentDto studentDto) {
        logger.info("StudentService update was invoked");
        Student mapper = mappingUtils.mapToStudent(studentDto);
        Student student = studentRepository.save(mapper);
        return mappingUtils.mapToStudentDto(student);
    }

    public void delete(Long id) {
        logger.info("StudentService delete was invoked");
        studentRepository.deleteById(id);
    }

    public List<StudentDto> filterByAge(Integer age) {
        logger.info("StudentService filterByAge was invoked");
        List<Student> studentList = studentRepository.findByAge(age);
        List<StudentDto> studentDtoList = new ArrayList<>();
        for (final Student student : studentList) {
            studentDtoList.add(mappingUtils.mapToStudentDto(student));
        }
        return studentDtoList;
    }

    public List<StudentDto> filterByAgeRange(Integer min, Integer max) {
        logger.info("StudentService filterByAgeRange was invoked");
        List<Student> studentList = studentRepository.findByAgeBetween(min, max);
        List<StudentDto> studentDtoList = new ArrayList<>();
        for (final Student student : studentList) {
            studentDtoList.add(mappingUtils.mapToStudentDto(student));
        }
        return studentDtoList;
    }

    public FacultyDto getStudentFaculty(Long id) {
        logger.info("StudentService getStudentFaculty was invoked");
        Faculty faculty = studentRepository
                .findById(id)
                .orElseThrow(NotFoundException::new)
                .getFaculty();
        return mappingUtils.mapToFacultyDto(faculty);
    }

    public StudentsAmount getStudentAmount() {
        logger.info("StudentService getStudentAmount was invoked");
        return studentRepository.getStudentAmount();
    }

    public StudentsAvgAge getStudentAvgAge() {
        logger.info("StudentService getStudentAvgAge was invoked");
        return studentRepository.getStudentAvgAge();
    }

    public List<LastFiveStudents> getLastFiveStudents() {
        logger.info("StudentService getLastFiveStudents was invoked");
        return studentRepository.getLastFiveStudents();
    }

    public List<String> getStartWithAStudents() {
        return studentRepository
                .findAll()
                .stream()
                .filter(
                        student -> student.getName().toUpperCase().startsWith("A")
                )
                .map(student -> student.getName().toUpperCase())
                .sorted()
                .toList();
    }

    public Integer getAvgAgeOfAllStudents() {
        List<Student> studentList = studentRepository.findAll();
        Integer studentAmount = studentList.size();
        return studentList.stream().reduce(0, (a, c) -> a + c.getAge(), Integer::sum) / studentAmount;
    }
}
