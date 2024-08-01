package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.HashMap;
import java.util.Map;

@Service
public class FacultyServiceImpl implements FacultyService {

    private FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }


    @Override
    public Faculty create(Faculty faculty) {
//        Long actualId = ++countId;
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty read(Long id) {
        return facultyRepository.findById(id).get();
    }

    @Override
    public Faculty update(Faculty faculty) {
//        facultyRepository.getById(id).setName(faculty.getName());
//        facultyRepository.getById(id).setColor(faculty.getColor());
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty delete(Long id) {
        Faculty facultyDel = facultyRepository.findById(id).get();
        facultyRepository.deleteById(id);
        return facultyDel;
    }
}
