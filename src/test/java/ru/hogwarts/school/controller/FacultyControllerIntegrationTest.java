package ru.hogwarts.school.controller;

import io.swagger.v3.oas.models.PathItem;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FacultyControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before("")
    private void clearDb() {
        facultyRepository.deleteAll();
    }

    @Test
    void create() {
        // given
        Faculty expectedFaculty = new Faculty("test1", "testColor");
        // when
        ResponseEntity<Faculty> facultyResponseEntity = restTemplate.postForEntity("http://localhost:" + port + "/faculty",
                expectedFaculty,
                Faculty.class);

        //then
        assertNotNull(facultyResponseEntity);
        assertEquals(HttpStatusCode.valueOf(200), facultyResponseEntity.getStatusCode());

        Faculty actualStudent = facultyResponseEntity.getBody();
        assertNotNull(actualStudent.getId());
        assertEquals(actualStudent.getColor(), expectedFaculty.getColor());
        assertEquals(actualStudent.getName(), expectedFaculty.getName());
    }

    @Test
    void read() {
        // given
        Faculty expectedFaculty = new Faculty("test1", "testColor");
        expectedFaculty = facultyRepository.save(expectedFaculty);
        // when
        ResponseEntity<Faculty> facultyResponseEntity = restTemplate.getForEntity(
                "http://localhost:" + port + "/faculty/" + expectedFaculty.getId(),
                Faculty.class);

        //then
        assertNotNull(facultyResponseEntity);
        assertEquals(HttpStatusCode.valueOf(200), facultyResponseEntity.getStatusCode());

        Faculty actualStudent = facultyResponseEntity.getBody();
        assertNotNull(actualStudent.getId());
        assertEquals(actualStudent.getColor(), expectedFaculty.getColor());
        assertEquals(actualStudent.getName(), expectedFaculty.getName());
    }

    @Test
    void update() {
        // given
        Faculty originFaculty = new Faculty("test", "test");
        originFaculty = facultyRepository.save(originFaculty);
        Faculty expectedFaculty = new Faculty("test1", "testColor");
        HttpEntity<Faculty> entity = new HttpEntity<>(expectedFaculty);

        // when
        ResponseEntity<Faculty> facultyResponseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/faculty/" + originFaculty.getId(),
                HttpMethod.PUT,
                entity,
                Faculty.class);

        //then
        assertNotNull(facultyResponseEntity);
        assertEquals(HttpStatusCode.valueOf(200), facultyResponseEntity.getStatusCode());

        Faculty actualStudent = facultyResponseEntity.getBody();
        assertNotNull(actualStudent.getId());
        assertEquals(actualStudent.getColor(), expectedFaculty.getColor());
        assertEquals(actualStudent.getName(), expectedFaculty.getName());
    }

    @Test
    void delete() {
        // given
        Faculty expectedFaculty = new Faculty("test", "test");
        expectedFaculty = facultyRepository.save(expectedFaculty);

        // when
        ResponseEntity<Faculty> facultyResponseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/faculty/" + expectedFaculty.getId(),
                HttpMethod.DELETE,
                HttpEntity.EMPTY,
                Faculty.class);

        //then
        assertNotNull(facultyResponseEntity);
        assertEquals(HttpStatusCode.valueOf(200), facultyResponseEntity.getStatusCode());

        Faculty actualStudent = facultyResponseEntity.getBody();
        assertNotNull(actualStudent.getId());
        assertEquals(actualStudent.getColor(), expectedFaculty.getColor());
        assertEquals(actualStudent.getName(), expectedFaculty.getName());
    }

    @Test
    void getStudent() {
        // given
        Faculty expectedFaculty = new Faculty("test", "test");
        expectedFaculty = facultyRepository.save(expectedFaculty);

        Student student1 = new Student("test1", 11);
        student1.setFaculty(expectedFaculty);
        student1 = studentRepository.save(student1);

        Student student2 = new Student("test2", 12);
        student2.setFaculty(expectedFaculty);
        student2 = studentRepository.save(student2);

        // when
        ResponseEntity<List<Student>> facultyResponseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/faculty/student/" + expectedFaculty.getId(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Student>>() {
                });

    }
}