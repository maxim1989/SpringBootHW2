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
import ru.hogwarts.school.dto.FacultyDto;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.HashMap;
import java.util.Map;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerTests {
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
    void createFacultyTest() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setName("f1 test name");
        faculty.setColor("test color");
        Faculty facultyDb = facultyRepository.save(faculty);
        Assertions
                .assertThat(facultyDb)
                .isNotNull();
        Assertions
                .assertThat(facultyDb.getName())
                .isEqualTo("f1 test name");

        Assertions
                .assertThat(facultyDb.getColor())
                .isEqualTo("test color");
        this.restTemplate.delete(
                "http://localhost:" + port + "/faculty/" + facultyDb.getId(),
                String.class
        );

        String response2 = this.restTemplate.getForObject(
                "http://localhost:" + port + "/faculty/" + facultyDb.getId(),
                String.class
        );
        Map<String, Object> map2 = stringToFaculty(response2);
        Assertions
                .assertThat(map2.get("status"))
                .isEqualTo(404);
    }

    @Test
    void getFacultyTest() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setName("f5");
        faculty.setColor("red");
        Faculty facultyDb = facultyRepository.save(faculty);
        Assertions
                .assertThat(
                        this.restTemplate.getForObject(
                                "http://localhost:" + port + "/faculty/" + facultyDb.getId(),
                                String.class
                        )
                )
                .isEqualTo("{\"id\":" + facultyDb.getId() + ",\"name\":\"f5\",\"color\":\"red\"}");
    }

    @Test
    void updateFacultyTest() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setName("f1 1");
        faculty.setColor("red 1");
        Faculty facultyDb = facultyRepository.save(faculty);
        FacultyDto facultyDto = new FacultyDto();
        facultyDto.setId(facultyDb.getId());
        facultyDto.setName("f1");
        facultyDto.setColor("red");
        this.restTemplate.put("http://localhost:" + port + "/faculty", facultyDto, String.class);
        Assertions
                .assertThat(
                        this.restTemplate.getForObject(
                                "http://localhost:" + port + "/faculty/" + facultyDb.getId(),
                                String.class
                        )
                )
                .isEqualTo("{\"id\":" + facultyDb.getId() + ",\"name\":\"f1\",\"color\":\"red\"}");
    }

    @Test
    void deleteFacultyTest() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setName("f2 test name");
        faculty.setColor("test 2 color");
        Faculty facultyDb = facultyRepository.save(faculty);
        Assertions
                .assertThat(facultyDb)
                .isNotNull();
        Assertions
                .assertThat(facultyDb.getName())
                .isEqualTo("f2 test name");

        Assertions
                .assertThat(facultyDb.getColor())
                .isEqualTo("test 2 color");
        this.restTemplate.delete(
                "http://localhost:" + port + "/faculty/" + facultyDb.getId(),
                String.class
        );

        String response2 = this.restTemplate.getForObject(
                "http://localhost:" + port + "/faculty/" + facultyDb.getId(),
                String.class
        );
        Map<String, Object> map2 = stringToFaculty(response2);
        Assertions
                .assertThat(map2.get("status"))
                .isEqualTo(404);
    }

    @Test
    void filterTest() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setName("F2");
        faculty.setColor("yellow");
        Faculty facultyDb = facultyRepository.save(faculty);
        Assertions
                .assertThat(
                        this.restTemplate.getForObject(
                                "http://localhost:" + port + "/faculty/filter?color=yellow",
                                String.class
                        )
                )
                .isEqualTo("[{\"id\":" + facultyDb.getId() + ",\"name\":\"F2\",\"color\":\"yellow\"}]");
    }

    @Test
    void getStudentsByFacultyTest() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setName("F7");
        faculty.setColor("yellow");
        Faculty facultyDb = facultyRepository.save(faculty);
        Student student = new Student();
        student.setName("Mark");
        student.setAge(18);
        student.setFaculty(facultyDb);
        Student studentDb = studentRepository.save(student);
        Assertions
                .assertThat(
                        this.restTemplate.getForObject(
                                "http://localhost:" + port + "/faculty/" + facultyDb.getId() + "/students",
                                String.class
                        )
                )
                .isEqualTo("[{\"id\":" + studentDb.getId() + ",\"name\":\"Mark\",\"age\":18,\"faculty\":{\"id\":" + facultyDb.getId() + ",\"name\":\"F7\",\"color\":\"yellow\"}}]");
    }

    static Map<String, Object> stringToFaculty(String str) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(str, HashMap.class);
    }
}
