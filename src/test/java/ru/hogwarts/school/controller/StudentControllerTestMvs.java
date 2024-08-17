package ru.hogwarts.school.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.StudentService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@WebMvcTest(StudentController.class)
class StudentControllerTestMvs {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private StudentService studentService;

    @MockBean
    private AvatarService avatarService;



    @Test
    void create() throws Exception {
        // given
        Long id = 1L;
        Student createStudent = new Student("Ivan", 20);
        Student expectedStudent = new Student("Ivan", 20);
        expectedStudent.setId(id);


        // when
        when(studentService.create(createStudent)).thenReturn(expectedStudent);

        ResultActions perform = mockMvc.perform(post("/student")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createStudent)));

        // then
        perform
                .andExpect(jsonPath("$.id").value(expectedStudent.getId()))
                .andExpect(jsonPath("$.name").value(expectedStudent.getName()))
                .andExpect(jsonPath("$.age").value(expectedStudent.getAge()))
                .andDo(print());
    }

    @Test
    void read() throws Exception {
        // given
        Long id = 1L;
        Student expectedStudent = new Student("Ivan", 20);
        expectedStudent.setId(id);


        // when
        when(studentService.read(id)).thenReturn(expectedStudent);

        ResultActions perform = mockMvc.perform(get("/student/{id}", id));

        // then
        perform
                .andExpect(jsonPath("$.id").value(expectedStudent.getId()))
                .andExpect(jsonPath("$.name").value(expectedStudent.getName()))
                .andExpect(jsonPath("$.age").value(expectedStudent.getAge()))
                .andDo(print());
    }

    @Test
    void update() throws Exception {
        // given
        Long id = 1L;
        Student originStudent = new Student("Ivan", 20);
        Student expectedStudent = new Student("Ivanov", 22);
        expectedStudent.setId(id);


        // when
        when(studentService.update(id, originStudent)).thenReturn(expectedStudent);

        ResultActions perform = mockMvc.perform(put("/student/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(originStudent)));

        // then
        perform
                .andExpect(jsonPath("$.id").value(expectedStudent.getId()))
                .andExpect(jsonPath("$.name").value(expectedStudent.getName()))
                .andExpect(jsonPath("$.age").value(expectedStudent.getAge()))
                .andDo(print());
    }

    @Test
    void deleteTest() throws Exception {
        // given
        Long id = 1L;
        Student expectedStudent = new Student("Ivan", 20);
        expectedStudent.setId(id);


        // when
        when(studentService.delete(id)).thenReturn(expectedStudent);

        ResultActions perform = mockMvc.perform(delete("/student/{id}", id));

        // then
        perform
                .andExpect(jsonPath("$.id").value(expectedStudent.getId()))
                .andExpect(jsonPath("$.name").value(expectedStudent.getName()))
                .andExpect(jsonPath("$.age").value(expectedStudent.getAge()))
                .andDo(print());
    }

    @Test
    void getByAge() throws Exception {
        // given
        int age = 20;
        Long id = 1L;
        Student expectedStudent = new Student("Ivan", 20);
        expectedStudent.setId(id);

        List<Student> students = new ArrayList<>();
        students.add(expectedStudent);


        // when
        when(studentService.getByAge(age)).thenReturn(students);

        ResultActions perform = mockMvc.perform(get("/student?age=" + age));

        // then
        perform
                .andExpect(jsonPath("$[0].id").value(expectedStudent.getId()))
                .andExpect(jsonPath("$[0].name").value(expectedStudent.getName()))
                .andExpect(jsonPath("$[0].age").value(expectedStudent.getAge()))
                .andDo(print());
    }

    @Test
    void getByAgeBetween() throws Exception {
        // given
        int from = 20;
        int to = 22;
        Long id = 1L;

        Student expectedStudent = new Student("Ivan", 20);
        expectedStudent.setId(id);

        List<Student> students = new ArrayList<>();
        students.add(expectedStudent);


        // when
        when(studentService.getBetweenAge(from, to)).thenReturn(students);

        ResultActions perform = mockMvc.perform(get("/student/byAgeBetween?from=" + from + "&to=" + to));

        // then
        perform
                .andExpect(jsonPath("$[0].id").value(expectedStudent.getId()))
                .andExpect(jsonPath("$[0].name").value(expectedStudent.getName()))
                .andExpect(jsonPath("$[0].age").value(expectedStudent.getAge()))
                .andDo(print());
    }


    @Test
    void getFaculty() throws Exception {
        // given
        Long id = 1L;
        Student student = new Student("Ivan", 20);
        Faculty expectedFaculty = new Faculty("testName", "testColor");

        expectedFaculty.setId(id);
        student.setId(id);
        student.setFaculty(expectedFaculty);



        // when
        when(studentService.getFaculty(id)).thenReturn(expectedFaculty);

        ResultActions perform = mockMvc.perform(get("/student/faculty/{id}", id));

        // then
        perform
                .andExpect(jsonPath("$.id").value(expectedFaculty.getId()))
                .andExpect(jsonPath("$.name").value(expectedFaculty.getName()))
                .andExpect(jsonPath("$.color").value(expectedFaculty.getColor()))
                .andDo(print());
    }
}