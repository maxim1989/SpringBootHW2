package ru.hogwarts.school.utils;

import org.springframework.stereotype.Component;
import ru.hogwarts.school.dto.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

@Component
public class MappingUtils {
    public FacultyDto mapToFacultyDto(Faculty entity){
        FacultyDto dto = new FacultyDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setColor(entity.getColor());
        return dto;
    }

    public Faculty mapToFaculty(FacultyDto dto){
        Faculty entity = new Faculty();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setColor(dto.getColor());
        return entity;
    }

    public Faculty mapToFacultyCreate(FacultyCreateDto dto){
        Faculty entity = new Faculty();
        entity.setName(dto.getName());
        entity.setColor(dto.getColor());
        return entity;
    }

    public StudentDto mapToStudentDto(Student entity) {
        StudentDto dto = new StudentDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setAge(entity.getAge());
        return dto;
    }

    public StudentFacultyDto mapToStudentFacultyDto(Student entity) {
        StudentFacultyDto dto = new StudentFacultyDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setAge(entity.getAge());
        dto.setFaculty(mapToFacultyDto(entity.getFaculty()));
        return dto;
    }

    public Student mapToStudent(StudentDto dto){
        Student entity = new Student();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setAge(dto.getAge());
        return entity;
    }

    public Student mapToStudentCreate(StudentCreateDto dto){
        Student entity = new Student();
        entity.setName(dto.getName());
        entity.setAge(dto.getAge());
        return entity;
    }
}
