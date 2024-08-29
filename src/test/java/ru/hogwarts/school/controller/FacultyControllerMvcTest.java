package ru.hogwarts.school.controller;

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
import ru.hogwarts.school.service.FacultyService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(FacultyController.class)
class FacultyControllerMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FacultyService facultyService;


    @Test
    void create() throws Exception{
        // given
        Long id = 1L;
        Faculty createFaculty = new Faculty("test1", "test1");
        Faculty expectedFaculty = new Faculty("test1", "test1");
        expectedFaculty.setId(id);


        // when
        when(facultyService.create(createFaculty)).thenReturn(expectedFaculty);

        ResultActions perform = mockMvc.perform(post("/faculty")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createFaculty)));

        // then
        perform
                .andExpect(jsonPath("$.id").value(expectedFaculty.getId()))
                .andExpect(jsonPath("$.name").value(expectedFaculty.getName()))
                .andExpect(jsonPath("$.color").value(expectedFaculty.getColor()))
                .andDo(print());
    }


    @Test
    void read() throws Exception {
        // given
        Long id = 1L;
        Faculty expectedFaculty = new Faculty("test1", "Test1");
        expectedFaculty.setId(id);


        // when
        when(facultyService.read(id)).thenReturn(expectedFaculty);

        ResultActions perform = mockMvc.perform(get("/faculty/{id}", id));

        // then
        perform
                .andExpect(jsonPath("$.id").value(expectedFaculty.getId()))
                .andExpect(jsonPath("$.name").value(expectedFaculty.getName()))
                .andExpect(jsonPath("$.color").value(expectedFaculty.getColor()))
                .andDo(print());
    }

    @Test
    void update() throws Exception {
        // given
        Long id = 1L;
        Faculty originFaculty = new Faculty("test1", "Test1");
        Faculty expectedFaculty = new Faculty("Test2", "Test2");
        expectedFaculty.setId(id);


        // when
        when(facultyService.update(id, originFaculty)).thenReturn(expectedFaculty);

        ResultActions perform = mockMvc.perform(put("/faculty/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(originFaculty)));

        // then
        perform
                .andExpect(jsonPath("$.id").value(expectedFaculty.getId()))
                .andExpect(jsonPath("$.name").value(expectedFaculty.getName()))
                .andExpect(jsonPath("$.color").value(expectedFaculty.getColor()))
                .andDo(print());
    }

    @Test
    void delete() {
    }

    @Test
    void getStudent() throws Exception {
        // given
        Long id = 1L;
        Faculty faculty = new Faculty("testName", "testColor");
        List<Student> expectedStudents = new ArrayList<>();
        Student student1 = new Student("Ivan1", 21);
        Student student2 = new Student("Ivan2", 22);

        faculty.setId(id);
        student1.setId(id);
        student1.setFaculty(faculty);
        student2.setId(id);
        student2.setFaculty(faculty);
        expectedStudents.add(student1);
        expectedStudents.add(student2);

        // when
        when(facultyService.getStudent(id)).thenReturn(expectedStudents);

        ResultActions perform = mockMvc.perform(get("/faculty/student/{id}", id));

        // then
        perform
                .andExpect(jsonPath("$[0].id").value(student1.getId()))
                .andExpect(jsonPath("$[0].name").value(student1.getName()))
                .andExpect(jsonPath("$[0].age").value(student1.getAge()))
                .andExpect(jsonPath("$[0].faculty").value(student1.getFaculty()))

                .andExpect(jsonPath("$[1].id").value(student2.getId()))
                .andExpect(jsonPath("$[1].name").value(student2.getName()))
                .andExpect(jsonPath("$[1].age").value(student2.getAge()))
                .andExpect(jsonPath("$[1].faculty").value(student2.getFaculty()))

                .andDo(print());
    }
}