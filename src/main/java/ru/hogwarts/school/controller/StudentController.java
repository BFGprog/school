package ru.hogwarts.school.controller;

import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

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
    public Student update(@RequestBody Student student) {
        return studentService.update(student);
    }

    @DeleteMapping("{id}")
    public Student delete(@PathVariable Long id) {
        return studentService.delete(id);
    }
}
