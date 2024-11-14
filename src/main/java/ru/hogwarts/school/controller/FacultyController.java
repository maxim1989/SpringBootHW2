package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping()
    public ResponseEntity<Faculty> createFaculty(@RequestBody Faculty faculty) {
        Faculty response = facultyService.create(faculty);
        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> getFaculty(@PathVariable Long id) {
        Faculty faculty = facultyService.read(id);
        return ResponseEntity.ok(faculty);
    }

    @PutMapping()
    public ResponseEntity<Faculty> updateFaculty(@RequestBody Faculty faculty) {
        Faculty response = facultyService.update(faculty.getId(), faculty);

        if (response == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteFaculty(@PathVariable Long id) {
        facultyService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("filter")
    public ResponseEntity<List<Faculty>> filter(
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
    public ResponseEntity<Set<Student>> getStudentsByFaculty(@PathVariable Long id) {
        return ResponseEntity.ok(facultyService.getStudentsByFaculty(id));
    }
}
