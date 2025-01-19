package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.dto.FacultyDto;
import ru.hogwarts.school.dto.StudentCreateDto;
import ru.hogwarts.school.dto.StudentDto;
import ru.hogwarts.school.repository.LastFiveStudents;
import ru.hogwarts.school.repository.StudentsAmount;
import ru.hogwarts.school.repository.StudentsAvgAge;
import ru.hogwarts.school.service.StudentService;

import java.util.List;

@RestController()
@RequestMapping("student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping()
    public ResponseEntity<StudentDto> createStudent(@RequestBody StudentCreateDto student) {
        return ResponseEntity.ok(studentService.create(student));
    }

    @GetMapping("{id}")
    public ResponseEntity<StudentDto> getStudent(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.read(id));
    }

    @PutMapping()
    public ResponseEntity<StudentDto> updateStudent(@RequestBody StudentDto studentDto) {
        return ResponseEntity.ok(studentService.update(studentDto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("filter")
    public ResponseEntity<List<StudentDto>> filter(
            @RequestParam(required = false) Integer age,
            @RequestParam(required = false) Integer min,
            @RequestParam(required = false) Integer max
    ) {
        if (age != null & min == null & max == null) {
            return ResponseEntity.ok(studentService.filterByAge(age));
        } else if (age == null & min != null & max != null) {
            return ResponseEntity.ok(studentService.filterByAgeRange(min, max));
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("{id}/faculty")
    public ResponseEntity<FacultyDto> getStudentFaculty(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudentFaculty(id));
    }

    @GetMapping("amount")
    public ResponseEntity<StudentsAmount> getStudentAmount() {
        return ResponseEntity.ok(studentService.getStudentAmount());
    }

    @GetMapping("avg-age")
    public ResponseEntity<StudentsAvgAge> getStudentAvgAge() {
        return ResponseEntity.ok(studentService.getStudentAvgAge());
    }

    @GetMapping("last-five-students")
    public ResponseEntity<List<LastFiveStudents>> getLastFiveStudents() {
        return ResponseEntity.ok(studentService.getLastFiveStudents());
    }

    @GetMapping("start-with-a")
    public ResponseEntity<List<String>> getStartWithAStudents() {
        return ResponseEntity.ok(studentService.getStartWithAStudents());
    }

    @GetMapping("students-avg-age")
    public ResponseEntity<Integer> getAvgAgeOfAllStudents() {
        return ResponseEntity.ok(studentService.getAvgAgeOfAllStudents());
    }

    @GetMapping("formula-result")
    public Long getFormulaResult() {
        return studentService.getFormulaResult();
    }

    @GetMapping("print-parallel")
    public void printParallel() {
        studentService.printParallel();
    }

    @GetMapping("print-synchronized")
    public void printSynchronized() {
        studentService.printSynchronized();
    }
}
