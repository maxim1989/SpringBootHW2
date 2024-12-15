package ru.hogwarts.school.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.dto.FacultyCreateDto;
import ru.hogwarts.school.dto.FacultyDto;
import ru.hogwarts.school.dto.StudentFacultyDto;
import ru.hogwarts.school.exception.NotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.utils.MappingUtils;

import java.util.*;

@Service
public class FacultyService {
    final FacultyRepository facultyRepository;
    final MappingUtils mappingUtils;

    public FacultyService(FacultyRepository facultyRepository, MappingUtils mappingUtils) {
        this.facultyRepository = facultyRepository;
        this.mappingUtils = mappingUtils;
    }

    public FacultyDto create(FacultyCreateDto facultyDto) {
        Faculty mapper = mappingUtils.mapToFacultyCreate(facultyDto);
        Faculty faculty = facultyRepository.save(mapper);
        return mappingUtils.mapToFacultyDto(faculty);
    }

    public FacultyDto read(Long id) {
        Faculty faculty = facultyRepository.findById(id).orElseThrow(NotFoundException::new);
        return mappingUtils.mapToFacultyDto(faculty);
    }

    public FacultyDto update(FacultyDto facultyDto) {
        Faculty mapper = mappingUtils.mapToFaculty(facultyDto);
        Faculty faculty = facultyRepository.save(mapper);
        return mappingUtils.mapToFacultyDto(faculty);
    }

    public void delete(Long id) {
        facultyRepository.deleteById(id);
    }

    public List<FacultyDto> filterByColor(String color) {
        List<Faculty> facultyList = facultyRepository.findByColor(color);
        List<FacultyDto> facultyDtoList = new ArrayList<>();

        for (final Faculty faculty : facultyList) {
            facultyDtoList.add(mappingUtils.mapToFacultyDto(faculty));
        }

        return facultyDtoList;
    }

    public List<FacultyDto> filterByName(String name) {
        List<Faculty> facultyList = facultyRepository.findByName(name);
        List<FacultyDto> facultyDtoList = new ArrayList<>();

        for (final Faculty faculty : facultyList) {
            facultyDtoList.add(mappingUtils.mapToFacultyDto(faculty));
        }

        return facultyDtoList;
    }

    public List<StudentFacultyDto> getStudentsByFaculty(Long id) {
        List<Student> studentList = facultyRepository
                .findById(id)
                .orElseThrow(NotFoundException::new).getStudents();
        List<StudentFacultyDto> studentFacultyDto = new ArrayList<>();
        for (final Student faculty : studentList) {
            studentFacultyDto.add(mappingUtils.mapToStudentFacultyDto(faculty));
        }
        return studentFacultyDto;
    }

    public List<FacultyDto> getFacultyPagination(Integer limit, Integer offset) {
        PageRequest pageRequest = PageRequest.of(offset - 1, limit);
        List<Faculty> facultyList = facultyRepository.findAll(pageRequest).getContent();
        List<FacultyDto> facultyDtoList = new ArrayList<>();
        for (final Faculty faculty : facultyList) {
            facultyDtoList.add(mappingUtils.mapToFacultyDto(faculty));
        }

        return facultyDtoList;
    }
}
