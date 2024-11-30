package ru.hogwarts.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import ru.hogwarts.school.dto.FacultyDto;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerTests {
    @LocalServerPort
    private int port;

    @Autowired
    private FacultyController facultyController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() throws Exception {
        Assertions.assertThat(facultyController).isNotNull();
    }

    @Test
    void createFacultyTest() throws Exception {
        FacultyDto facultyDto = new FacultyDto();
        facultyDto.setName("f1 test name");
        facultyDto.setColor("test color");
        String response = this.restTemplate.postForObject(
                "http://localhost:" + port + "/faculty",
                facultyDto,
                String.class
        );
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = mapper.readValue(response, HashMap.class);
        Assertions
                .assertThat(response)
                .isNotNull();
        Assertions
                .assertThat(map.get("name"))
                .isEqualTo("f1 test name");

        Assertions
                .assertThat(map.get("color"))
                .isEqualTo("test color");
        this.restTemplate.delete(
                "http://localhost:" + port + "/faculty/" + map.get("id"),
                String.class
        );

        String response2 = this.restTemplate.getForObject(
                "http://localhost:" + port + "/faculty/" + map.get("id"),
                String.class
        );
        ObjectMapper mapper2 = new ObjectMapper();
        Map<String, Object> map2 = mapper2.readValue(response2, HashMap.class);
        Assertions
                .assertThat(map2.get("status"))
                .isEqualTo(404);
    }

    @Test
    void getFacultyTest() throws Exception {
        Assertions
                .assertThat(
                        this.restTemplate.getForObject(
                                "http://localhost:" + port + "/faculty/" + "2",
                                String.class
                        )
                )
                .isEqualTo("{\"id\":2,\"name\":\"f1\",\"color\":\"red\"}");
    }

    @Test
    void updateFacultyTest() throws Exception {
        FacultyDto facultyDto = new FacultyDto();
        facultyDto.setId(2L);
        facultyDto.setName("f1");
        facultyDto.setColor("red");
        this.restTemplate.put("http://localhost:" + port + "/faculty", facultyDto, String.class);
        Assertions
                .assertThat(
                        this.restTemplate.getForObject(
                                "http://localhost:" + port + "/faculty/2",
                                String.class
                        )
                )
                .isEqualTo("{\"id\":2,\"name\":\"f1\",\"color\":\"red\"}");
    }

    @Test
    void deleteFacultyTest() throws Exception {
        FacultyDto facultyDto = new FacultyDto();
        facultyDto.setName("f2 test name");
        facultyDto.setColor("test 2 color");
        String response = this.restTemplate.postForObject(
                "http://localhost:" + port + "/faculty",
                facultyDto,
                String.class
        );
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = mapper.readValue(response, HashMap.class);
        Assertions
                .assertThat(response)
                .isNotNull();
        Assertions
                .assertThat(map.get("name"))
                .isEqualTo("f2 test name");

        Assertions
                .assertThat(map.get("color"))
                .isEqualTo("test 2 color");
        this.restTemplate.delete(
                "http://localhost:" + port + "/faculty/" + map.get("id"),
                String.class
        );

        String response2 = this.restTemplate.getForObject(
                "http://localhost:" + port + "/faculty/" + map.get("id"),
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
                                "http://localhost:" + port + "/faculty/filter?color=yellow",
                                String.class
                        )
                )
                .isEqualTo("[{\"id\":3,\"name\":\"F2\",\"color\":\"yellow\"}]");
    }

    @Test
    void getStudentsByFacultyTest() throws Exception {
        Assertions
                .assertThat(
                        this.restTemplate.getForObject(
                                "http://localhost:" + port + "/faculty/3/students",
                                String.class
                        )
                )
                .isEqualTo("[{\"id\":52,\"name\":\"Mark\",\"age\":18,\"faculty\":{\"id\":3,\"name\":\"F2\",\"color\":\"yellow\"}}]");
    }
}
