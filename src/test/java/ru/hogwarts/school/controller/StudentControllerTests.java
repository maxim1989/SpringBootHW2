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
public class StudentControllerTests {
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
    private StudentController studentController;

    @Test
    void createStudentTest() throws Exception {
        Student student = new Student();
        student.setId(1L);
        student.setName("Test 1");
        student.setAge(24);
        JSONObject json = new JSONObject();
        json.put("name", "Test 1");
        json.put("age", 24);

        when(studentRepository.save(any(Student.class))).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student")
                        .content(json.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Test 1"))
                .andExpect(jsonPath("$.age").value(24));
    }

    @Test
    void getStudentTest() throws Exception {
        Student student = new Student();
        student.setId(1L);
        student.setName("Test 1");
        student.setAge(24);

        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Test 1"))
                .andExpect(jsonPath("$.age").value(24));
    }

    @Test
    void updateStudentTest() throws Exception {
        Student student = new Student();
        student.setId(1L);
        student.setName("Test 1");
        student.setAge(24);
        JSONObject json = new JSONObject();
        json.put("id", 1L);
        json.put("name", "Test 1");
        json.put("age", 24);

        when(studentRepository.save(any(Student.class))).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/student")
                        .content(json.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Test 1"))
                .andExpect(jsonPath("$.age").value(24));
    }

    @Test
    void deleteStudentTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/student/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void filterTest() throws Exception {
        Student student = new Student();
        student.setId(1L);
        student.setName("Test 1");
        student.setAge(24);
        List<Student> studentList = new ArrayList<>();
        studentList.add(student);

        when(studentRepository.findByAge(any(Integer.class))).thenReturn(studentList);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/filter?age=24")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(1L))
                .andExpect(jsonPath("$.[0].name").value("Test 1"))
                .andExpect(jsonPath("$.[0].age").value(24));
    }

    @Test
    void getStudentFacultyTest() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setId(3L);
        faculty.setName("F2");
        faculty.setColor("yellow");
        Student student = new Student();
        student.setId(1L);
        student.setName("Test 1");
        student.setAge(24);
        student.setFaculty(faculty);

        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/1/faculty")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3L))
                .andExpect(jsonPath("$.name").value("F2"))
                .andExpect(jsonPath("$.color").value("yellow"));
    }
}
