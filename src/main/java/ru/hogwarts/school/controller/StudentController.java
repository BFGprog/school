package ru.hogwarts.school.controller;

import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public Student create(@RequestBody Student student) {
        return studentService.create(student);
    }

    @GetMapping("{id}")
    public Student read(@PathVariable Long id) {
        return studentService.read(id);
    }

    @PutMapping("{id}")
    public Student update(@PathVariable Long id, @RequestBody Student student) {
        return studentService.update(id, student);
    }

    @DeleteMapping("{id}")
    public Student delete(@PathVariable Long id) {
        return studentService.delete(id);
    }

    @GetMapping
    public List<Student> getByAge(Integer age) {
        return studentService.getByAge(age);
    }

    @GetMapping("byAgeBetween")
    public List<Student> getByAgeBetween(Integer from, Integer to) {
        return studentService.getBetweenAge(from, to);
    }

    @GetMapping("faculty/{id}")
    public Faculty getFaculty(@PathVariable Long id) {
        return studentService.getFaculty(id);
    }

    @GetMapping("count")
    public Integer getNumberOfStudents() {
        return studentService.getNumberOfStudents();
    }

    @GetMapping("average")
    public int getAverageAge() {
        return studentService.getAverageAge();
    }

    @GetMapping("five")
    public List<Student> get() {
        return studentService.getFiveLastStudent();
    }

    @GetMapping("get-all-start-a")
    public List<String> getStudentsStartingWithA() {
        return studentService.getStudentsStartingWithA();
    }

    @GetMapping("average-stream")
    public Double getAverageAgeStream() {
        return studentService.getAverageAgeStream();
    }


}
