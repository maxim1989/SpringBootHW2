package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final Logger logger = LoggerFactory.getLogger(FacultyService.class);

    public FacultyService(FacultyRepository facultyRepository, MappingUtils mappingUtils) {
        this.facultyRepository = facultyRepository;
        this.mappingUtils = mappingUtils;
    }

    public FacultyDto create(FacultyCreateDto facultyDto) {
        logger.info("FacultyService create was invoked");
        Faculty mapper = mappingUtils.mapToFacultyCreate(facultyDto);
        Faculty faculty = facultyRepository.save(mapper);
        return mappingUtils.mapToFacultyDto(faculty);
    }

    public FacultyDto read(Long id) {
        logger.info("FacultyService read was invoked");
        Faculty faculty = facultyRepository.findById(id).orElseThrow(NotFoundException::new);
        return mappingUtils.mapToFacultyDto(faculty);
    }

    public FacultyDto update(FacultyDto facultyDto) {
        logger.info("FacultyService update was invoked");
        Faculty mapper = mappingUtils.mapToFaculty(facultyDto);
        Faculty faculty = facultyRepository.save(mapper);
        return mappingUtils.mapToFacultyDto(faculty);
    }

    public void delete(Long id) {
        logger.info("FacultyService delete was invoked");
        facultyRepository.deleteById(id);
    }

    public List<FacultyDto> filterByColor(String color) {
        logger.info("FacultyService filterByColor was invoked");
        List<Faculty> facultyList = facultyRepository.findByColor(color);
        List<FacultyDto> facultyDtoList = new ArrayList<>();

        for (final Faculty faculty : facultyList) {
            facultyDtoList.add(mappingUtils.mapToFacultyDto(faculty));
        }

        return facultyDtoList;
    }

    public List<FacultyDto> filterByName(String name) {
        logger.info("FacultyService filterByName was invoked");
        List<Faculty> facultyList = facultyRepository.findByName(name);
        List<FacultyDto> facultyDtoList = new ArrayList<>();

        for (final Faculty faculty : facultyList) {
            facultyDtoList.add(mappingUtils.mapToFacultyDto(faculty));
        }

        return facultyDtoList;
    }

    public List<StudentFacultyDto> getStudentsByFaculty(Long id) {
        logger.info("FacultyService getStudentsByFaculty was invoked");
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
        logger.info("FacultyService getFacultyPagination was invoked");
        PageRequest pageRequest = PageRequest.of(offset - 1, limit);
        List<Faculty> facultyList = facultyRepository.findAll(pageRequest).getContent();
        List<FacultyDto> facultyDtoList = new ArrayList<>();
        for (final Faculty faculty : facultyList) {
            facultyDtoList.add(mappingUtils.mapToFacultyDto(faculty));
        }

        return facultyDtoList;
    }
}
