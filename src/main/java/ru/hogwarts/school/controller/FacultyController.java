package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.dto.FacultyCreateDto;
import ru.hogwarts.school.dto.FacultyDto;
import ru.hogwarts.school.dto.StudentFacultyDto;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.List;

@RestController
@RequestMapping("faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping()
    public ResponseEntity<FacultyDto> createFaculty(@RequestBody FacultyCreateDto faculty) {
        return ResponseEntity.ok(facultyService.create(faculty));
    }

    @GetMapping("{id}")
    public ResponseEntity<FacultyDto> getFaculty(@PathVariable Long id) {
        return ResponseEntity.ok(facultyService.read(id));
    }

    @PutMapping()
    public ResponseEntity<FacultyDto> updateFaculty(@RequestBody FacultyDto faculty) {
        return ResponseEntity.ok(facultyService.update(faculty));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteFaculty(@PathVariable Long id) {
        facultyService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("filter")
    public ResponseEntity<List<FacultyDto>> filter(
            @RequestParam(required = false) String color,
            @RequestParam(required = false) String name
    ) {
        if (color != null & name == null) {
            return ResponseEntity.ok(facultyService.filterByColor(color));
        } else if (color == null & name != null) {
            return ResponseEntity.ok(facultyService.filterByName(name));
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("{id}/students")
    public ResponseEntity<List<StudentFacultyDto>> getStudentsByFaculty(@PathVariable Long id) {
        return ResponseEntity.ok(facultyService.getStudentsByFaculty(id));
    }

    @GetMapping("pagination")
    public ResponseEntity<List<FacultyDto>> getFacultyPagination(@RequestParam() Integer limit,
                                                              @RequestParam() Integer offset) {
        return ResponseEntity.ok(facultyService.getFacultyPagination(limit, offset));
    }
}
