package ru.hogwarts.school.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @BeforeEach
    private void clearDb() {
        studentRepository.deleteAll();
    }


    @Test
    void testCreateTest() {
        // given
        Student expectedStudent = new Student("test1", 22);
        // when
        ResponseEntity<Student> studentResponseEntity = testRestTemplate.postForEntity("http://localhost:" + port + "/student",
                expectedStudent,
                Student.class);

        //then

        assertNotNull(studentResponseEntity);
        assertEquals(HttpStatusCode.valueOf(200), studentResponseEntity.getStatusCode());

        Student actualStudent = studentResponseEntity.getBody();
        assertNotNull(actualStudent.getId());
        assertEquals(actualStudent.getAge(), expectedStudent.getAge());
        assertEquals(actualStudent.getName(), expectedStudent.getName());

    }

    @Test
    void testReadTest() {
        // given
        Student expectedStudent = new Student("test1", 22);
        expectedStudent = studentRepository.save(expectedStudent);

        // when
        ResponseEntity<Student> studentResponseEntity = testRestTemplate.getForEntity(
                "http://localhost:" + port + "/student/" + expectedStudent.getId(),
                Student.class);

        // then
        assertNotNull(studentResponseEntity);
        assertEquals(HttpStatusCode.valueOf(200), studentResponseEntity.getStatusCode());

        Student actualStudent = studentResponseEntity.getBody();
        assertEquals(actualStudent.getAge(), expectedStudent.getAge());
        assertEquals(actualStudent.getName(), expectedStudent.getName());
    }

    @Test
    void updateTest() {
        // given
        Student originStudent = new Student("test1", 22);
        originStudent = studentRepository.save(originStudent);
        Student expectedStudent = new Student("chengName", 222);
        HttpEntity<Student> entity = new HttpEntity<>(expectedStudent);

        // when
        ResponseEntity<Student> studentResponseEntity = testRestTemplate.exchange(
                "http://localhost:" + port + "/student/" + originStudent.getId(),
                HttpMethod.PUT,
                entity,
                Student.class);

        // then
        assertNotNull(studentResponseEntity);
        assertEquals(HttpStatusCode.valueOf(200), studentResponseEntity.getStatusCode());

        Student actualStudent = studentResponseEntity.getBody();
        assertEquals(actualStudent.getAge(), expectedStudent.getAge());
        assertEquals(actualStudent.getName(), expectedStudent.getName());
    }

    @Test
    void getByAge() {
        // given
        int age = 22;
        List<Student> students = new ArrayList<>();
        Student student1 = studentRepository.save(new Student("test1", 21));
        Student student2 = studentRepository.save(new Student("test2", 22));
        Student student3 = studentRepository.save(new Student("test3", 22));
        Student student4 = studentRepository.save(new Student("test4", 23));
        students.add(student2);
        students.add(student3);

        // when
        ResponseEntity<List<Student>> studentResponseEntity = testRestTemplate.exchange(
                "http://localhost:" + port + "/student?age=" + age,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Student>>() {
                }
        );
        // then
        assertNotNull(studentResponseEntity);
        assertEquals(HttpStatusCode.valueOf(200), studentResponseEntity.getStatusCode());

        List<Student> actualStudent = studentResponseEntity.getBody();
        assertEquals(students.getClass(), actualStudent.getClass());
        assertEquals(students.size(), actualStudent.size());
        assertEquals(students.get(0), actualStudent.get(0));
        Assertions.assertThat(students).containsExactlyInAnyOrderElementsOf(actualStudent);
    }

    @Test
    void getByAgeBetween2() {
        // give
        int from = 20;
        int to = 22;
        List<Student> students = new ArrayList<>();
        Student student1 = studentRepository.save(new Student("test1", 21));
        Student student2 = studentRepository.save(new Student("test2", 22));
        Student student3 = studentRepository.save(new Student("test3", 23));
        Student student4 = studentRepository.save(new Student("test4", 24));
        students.add(student1);
        students.add(student2);

        // when
        ResponseEntity<List<Student>> studentResponseEntity = testRestTemplate.exchange(
                "http://localhost:" + port + "/student/byAgeBetween?from=" + from + "&to=" + to,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Student>>() {
                }
        );

        // then
        assertNotNull(studentResponseEntity);
        assertEquals(HttpStatusCode.valueOf(200), studentResponseEntity.getStatusCode());

        List<Student> actualStudent = studentResponseEntity.getBody();
        assertEquals(students.getClass(), actualStudent.getClass());
        assertEquals(students.size(), actualStudent.size());
        Assertions.assertThat(students).containsExactlyInAnyOrderElementsOf(actualStudent);
        assertEquals(students.get(students.size() - 1), actualStudent.get(actualStudent.size() - 1));

    }

    @Test
    void getFaculty() {
        // given
        Faculty faculty = new Faculty("testName", "testColor");
        faculty = facultyRepository.save(faculty);

        Student student = new Student("test1", 22);
        student.setFaculty(faculty);
        student = studentRepository.save(student);

        // when
        ResponseEntity<Faculty> facultyResponseEntity = testRestTemplate.getForEntity(
                "http://localhost:" + port + "/student/faculty/" + student.getId(),
                Faculty.class);

        // then
        assertNotNull(facultyResponseEntity);
        assertEquals(HttpStatusCode.valueOf(200), facultyResponseEntity.getStatusCode());

        Faculty actualFaculty = facultyResponseEntity.getBody();
        assertEquals(faculty.getColor(), actualFaculty.getColor());
        assertEquals(faculty.getName(), actualFaculty.getName());

    }

    // -------------------------------------------------------
//    @Test
//    void getByAgeBetween() {
//        // give
//        int from = 1;
//        int to = 200;
//
//        List<JSONObject> expectedStudents = new ArrayList<>();
//        JSONObject student1 = new JSONObject();
//        student1.put("id", 3);
//        student1.put("age", 21);
//        student1.put("name", "test1");
//        student1.put("faculty", null);
//
//        JSONObject student2 = new JSONObject();
//        student2.put("id", 4);
//        student2.put("age", 22);
//        student2.put("name", "test2");
//        student2.put("faculty", null);
//
//        JSONObject student3 = new JSONObject();
//        student3.put("id", 5);
//        student3.put("age", 23);
//        student3.put("name", "test3");
//        student3.put("faculty", null);
//
//        expectedStudents.add(student1);
//        expectedStudents.add(student2);
//        expectedStudents.add(student3);
//        // when
//        // then
//        Assertions.assertThat(this.testRestTemplate
//                        .getForObject("http://localhost:" + port +
//                                "/student/byAgeBetween?from=" + from + "&to=" + to, ArrayList.class))
//                .isEqualTo(expectedStudents);
//
//    }
//
//
//    @Test
//    void testCreate() {
//        // give
//        int id = 1;
//        int age = 33;
//        String name = "Test1";
//        Student actual = new Student();
////        student1.setId(1L);
//        actual.setAge(age);
//        actual.setName(name);
//
//        JSONObject expected = new JSONObject();
//        expected.put("id", id);
//        expected.put("name", name);
//        expected.put("age", age);
//        expected.put("faculty", null);
//
//
//        // when
//        Assertions.assertThat(this.testRestTemplate.postForObject("http://localhost:" + port + "/student", actual, JSONObject.class))
//                .isEqualTo(expected);
////        when(studentService.read(1L)).thenReturn(student1);
//
//        // then
////        Assertions.assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/student/103", String.class))
////                .isEqualTo(student1);
//
//    }
//
//    @Test
//    void testRead() {
//
//        // give
//        JSONObject expectedStudent = new JSONObject();
//        expectedStudent.put("id", 3);
//        expectedStudent.put("age", 21);
//        expectedStudent.put("name", "test1");
//        expectedStudent.put("faculty", null);
//        // when
//        // then
//        Assertions.assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/student/3", JSONObject.class))
//                .isEqualTo(expectedStudent);
//    }
//
//    @Test
//    void update() {
//        // give
//        JSONObject expectedStudent = new JSONObject();
//        expectedStudent.put("id", 3);
//        expectedStudent.put("age", 121);
//        expectedStudent.put("name", "newName");
//        expectedStudent.put("faculty", null);
//
//        Student student1 = new Student();
//        student1.setName("newName");
//        student1.setAge(121);
//        student1.setFaculty(null);
//        // when
//
//        // then
////        Assertions.assertThat(this.testRestTemplate.exchange(
////                "http://localhost:" + port + "/student/3",
////                HttpMethod.PUT,
////                expectedStudent,
////                JSONObject.class))
////                .isEqualTo(expectedStudent);
//
//    }
//
//    @Test
//    void delete() {
//        // give
//        JSONObject expectedStudent = new JSONObject();
//        expectedStudent.put("id", 3);
//        expectedStudent.put("age", 21);
//        expectedStudent.put("name", "test1");
//        expectedStudent.put("faculty", null);
//        // when
////        testRestTemplate.delete("http://localhost:" + port + "/student/3");
//        // then
////        Assertions.assertThat(this.testRestTemplate.delete("http://localhost:" + port + "/student/3", JSONObject.class))
////                .isEqualTo(expectedStudent);
//
//    }
}