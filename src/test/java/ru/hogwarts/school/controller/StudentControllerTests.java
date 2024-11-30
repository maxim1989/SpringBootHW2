package ru.hogwarts.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import ru.hogwarts.school.dto.StudentDto;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTests {
    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() throws Exception {
        Assertions.assertThat(studentController).isNotNull();
    }

    @Test
    void createStudentTest() throws Exception {
        StudentDto studentDto = new StudentDto();
        studentDto.setName("Test 1");
        studentDto.setAge(24);
        String response = this.restTemplate.postForObject(
                "http://localhost:" + port + "/student",
                studentDto,
                String.class
        );
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = mapper.readValue(response, HashMap.class);
        Assertions
                .assertThat(response)
                .isNotNull();
        Assertions
                .assertThat(map.get("name"))
                .isEqualTo("Test 1");

        Assertions
                .assertThat(map.get("age"))
                .isEqualTo(24);
        this.restTemplate.delete(
                "http://localhost:" + port + "/student/" + map.get("id"),
                String.class
        );

        String response2 = this.restTemplate.getForObject(
                "http://localhost:" + port + "/student/" + map.get("id"),
                String.class
        );
        ObjectMapper mapper2 = new ObjectMapper();
        Map<String, Object> map2 = mapper2.readValue(response2, HashMap.class);
        Assertions
                .assertThat(map2.get("status"))
                .isEqualTo(404);
    }

    @Test
    void getStudentTest() throws Exception {
        Assertions
                .assertThat(
                        this.restTemplate.getForObject(
                                "http://localhost:" + port + "/student/" + "2",
                                String.class
                        )
                )
                .isEqualTo("{\"id\":2,\"name\":\"John 1\",\"age\":24}");
    }

    @Test
    void updateStudentTest() throws Exception {
        StudentDto studentDto = new StudentDto();
        studentDto.setId(2L);
        studentDto.setName("John 1");
        studentDto.setAge(24);
        this.restTemplate.put("http://localhost:" + port + "/student", studentDto, String.class);
        Assertions
                .assertThat(
                        this.restTemplate.getForObject(
                                "http://localhost:" + port + "/student/" + "2",
                                String.class
                        )
                )
                .isEqualTo("{\"id\":2,\"name\":\"John 1\",\"age\":24}");
    }

    @Test
    void deleteStudentTest() throws Exception {
        StudentDto studentDto = new StudentDto();
        studentDto.setName("Test 2");
        studentDto.setAge(25);
        String response = this.restTemplate.postForObject(
                "http://localhost:" + port + "/student",
                studentDto,
                String.class
        );
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = mapper.readValue(response, HashMap.class);
        Assertions
                .assertThat(response)
                .isNotNull();
        Assertions
                .assertThat(map.get("name"))
                .isEqualTo("Test 2");

        Assertions
                .assertThat(map.get("age"))
                .isEqualTo(25);
        this.restTemplate.delete(
                "http://localhost:" + port + "/student/" + map.get("id"),
                String.class
        );

        String response2 = this.restTemplate.getForObject(
                "http://localhost:" + port + "/student/" + map.get("id"),
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
        Assertions
                .assertThat(
                        this.restTemplate.getForObject(
                                "http://localhost:" + port + "/student/filter?age=18",
                                String.class
                        )
                )
                .isEqualTo("[{\"id\":52,\"name\":\"Mark\",\"age\":18}]");
    }

    @Test
    void getStudentFacultyTest() {
        Assertions
                .assertThat(
                        this.restTemplate.getForObject(
                                "http://localhost:" + port + "/student/52/faculty",
                                String.class
                        )
                )
                .isEqualTo("{\"id\":3,\"name\":\"F2\",\"color\":\"yellow\"}");
    }
}
