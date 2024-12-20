package ru.hogwarts.school.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.hogwarts.school.dto.StudentDto;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.HashMap;
import java.util.Map;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTests {
    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private FacultyController facultyController;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() throws Exception {
        Assertions.assertThat(studentController).isNotNull();
        Assertions.assertThat(facultyController).isNotNull();
    }

    @Test
    void createStudentTest() throws Exception {
        Student student = new Student();
        student.setName("Test 1");
        student.setAge(24);
        Student studentDb = studentRepository.save(student);
        Assertions
                .assertThat(studentDb)
                .isNotNull();
        Assertions
                .assertThat(studentDb.getName())
                .isEqualTo("Test 1");

        Assertions
                .assertThat(studentDb.getAge())
                .isEqualTo(24);
        this.restTemplate.delete(
                "http://localhost:" + port + "/student/" + studentDb.getId(),
                String.class
        );

        String response2 = this.restTemplate.getForObject(
                "http://localhost:" + port + "/student/" + studentDb.getId(),
                String.class
        );
        Map<String, Object> map2 = stringToStudent(response2);
        Assertions
                .assertThat(map2.get("status"))
                .isEqualTo(404);
    }

    @Test
    void getStudentTest() throws Exception {
        Student student = new Student();
        student.setName("John 1");
        student.setAge(24);
        Student studentDb = studentRepository.save(student);
        Assertions
                .assertThat(
                        this.restTemplate.getForObject(
                                "http://localhost:" + port + "/student/" + studentDb.getId(),
                                String.class
                        )
                )
                .isEqualTo("{\"id\":" + studentDb.getId() + ",\"name\":\"John 1\",\"age\":24}");
    }

    @Test
    void updateStudentTest() throws Exception {
        Student student = new Student();
        student.setName("John");
        student.setAge(24);
        Student studentDb = studentRepository.save(student);
        StudentDto studentDto = new StudentDto();
        studentDto.setId(studentDb.getId());
        studentDto.setName("John 1");
        studentDto.setAge(24);
        this.restTemplate.put("http://localhost:" + port + "/student", studentDto, String.class);
        Assertions
                .assertThat(
                        this.restTemplate.getForObject(
                                "http://localhost:" + port + "/student/" + studentDb.getId(),
                                String.class
                        )
                )
                .isEqualTo("{\"id\":" + studentDb.getId() + ",\"name\":\"John 1\",\"age\":24}");
    }

    @Test
    void deleteStudentTest() throws Exception {
        Student student = new Student();
        student.setName("Test 2");
        student.setAge(25);
        Student studentDb = studentRepository.save(student);
        this.restTemplate.delete(
                "http://localhost:" + port + "/student/" + studentDb.getId(),
                String.class
        );

        String response2 = this.restTemplate.getForObject(
                "http://localhost:" + port + "/student/" + studentDb.getId(),
                String.class
        );
        ObjectMapper mapper2 = new ObjectMapper();
        Map<String, Object> map2 = mapper2.readValue(response2, HashMap.class);
        Assertions
                .assertThat(map2.get("status"))
                .isEqualTo(404);
    }

    @Test
    void filterTest() throws Exception {
        Student student = new Student();
        student.setName("Fil");
        student.setAge(30);
        Student studentDb = studentRepository.save(student);
        Assertions
                .assertThat(
                        this.restTemplate.getForObject(
                                "http://localhost:" + port + "/student/filter?age=30",
                                String.class
                        )
                )
                .isEqualTo("[{\"id\":" + studentDb.getId() + ",\"name\":\"Fil\",\"age\":30}]");
    }

    @Test
    void getStudentFacultyTest() {
        Faculty faculty = new Faculty();
        faculty.setName("f1 test name");
        faculty.setColor("test color");
        Faculty facultyDb = facultyRepository.save(faculty);
        Student student = new Student();
        student.setName("Mike");
        student.setAge(18);
        student.setFaculty(facultyDb);
        Student studentDb = studentRepository.save(student);
        Assertions
                .assertThat(
                        this.restTemplate.getForObject(
                                "http://localhost:" + port + "/student/" + studentDb.getId() + "/faculty",
                                String.class
                        )
                )
                .isEqualTo("{\"id\":" + facultyDb.getId() + ",\"name\":\"f1 test name\",\"color\":\"test color\"}");
    }

    static Map<String, Object> stringToStudent(String str) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(str, HashMap.class);
    }
}
