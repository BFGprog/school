package ru.hogwarts.school.controller;

import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

@RestController
@RequestMapping("faculty")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public Faculty create(@RequestBody Faculty faculty) {
        return facultyService.create(faculty);
    }

    @GetMapping("{id}")
    public Faculty read(@PathVariable Long id) {
        return facultyService.read(id);
    }

    @PutMapping("{id}")
    public Faculty update(@PathVariable Long id, @RequestBody Faculty faculty) {
        return facultyService.update(id, faculty);
    }

    @DeleteMapping("{id}")
    public Faculty delete(@PathVariable Long id) {
        return facultyService.delete(id);
    }

}
