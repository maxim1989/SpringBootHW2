package ru.hogwarts.school.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;
import ru.hogwarts.school.utils.MappingUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class FacultyControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentRepository studentRepository;

    @MockBean
    private FacultyRepository facultyRepository;

    @SpyBean
    private StudentService studentService;

    @SpyBean
    private FacultyService facultyService;

    @SpyBean
    private MappingUtils mappingUtils;

    @InjectMocks
    private FacultyController facultyController;

    @Test
    void createFacultyTest() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setId(1L);
        faculty.setName("Name 1");
        faculty.setColor("red");
        JSONObject json = new JSONObject();
        json.put("name", "Name 1");
        json.put("color", "red");

        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(json.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Name 1"))
                .andExpect(jsonPath("$.color").value("red"));
    }

    @Test
    void getFacultyTest() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setId(1L);
        faculty.setName("Name 1");
        faculty.setColor("red");

        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(faculty));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Name 1"))
                .andExpect(jsonPath("$.color").value("red"));
    }

    @Test
    void updateFacultyTest() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setId(1L);
        faculty.setName("Name 1");
        faculty.setColor("red");
        JSONObject json = new JSONObject();
        json.put("id", 1L);
        json.put("name", "Name 1");
        json.put("color", "red");

        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/faculty")
                        .content(json.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Name 1"))
                .andExpect(jsonPath("$.color").value("red"));
    }

    @Test
    void deleteFacultyTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/faculty/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void filterTest() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setId(1L);
        faculty.setName("Name 1");
        faculty.setColor("red");
        List<Faculty> facultyList = new ArrayList<>();
        facultyList.add(faculty);

        when(facultyRepository.findByColor(any(String.class))).thenReturn(facultyList);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/filter?color=red")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(1L))
                .andExpect(jsonPath("$.[0].name").value("Name 1"))
                .andExpect(jsonPath("$.[0].color").value("red"));
    }

    @Test
    void getStudentsByFacultyTest() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setId(3L);
        faculty.setName("F2");
        faculty.setColor("yellow");
        Student student = new Student();
        student.setId(1L);
        student.setName("Test 1");
        student.setAge(24);
        student.setFaculty(faculty);
        Student student2 = new Student();
        student2.setId(2L);
        student2.setName("Test 2");
        student2.setAge(25);
        student2.setFaculty(faculty);
        List<Student> studentList = new ArrayList<>();
        studentList.add(student);
        studentList.add(student2);
        faculty.setStudents(studentList);

        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(faculty));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/3/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0]id").value(1L))
                .andExpect(jsonPath("$.[0]name").value("Test 1"))
                .andExpect(jsonPath("$.[0]age").value(24))
                .andExpect(jsonPath("$.[1]id").value(2L))
                .andExpect(jsonPath("$.[1]name").value("Test 2"))
                .andExpect(jsonPath("$.[1]age").value(25));
    }
}
