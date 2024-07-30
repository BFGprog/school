package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

import java.util.HashMap;
import java.util.Map;

@Service
public class FacultyServiceImpl implements FacultyService {
    
    private Long countId = 0L;

    private final Map<Long, Faculty> faculties = new HashMap<>();

    @Override
    public Faculty create(Faculty faculty) {
        Long actualId = ++countId;

        return faculties.put(actualId, faculty);
    }

    @Override
    public Faculty read(Long id) {
        return faculties.get(id);
    }
    @Override
    public Faculty update(Long id, Faculty faculty){
        return faculties.put(id, faculty);
    }
    @Override
    public Faculty delete(Long id) {
        return faculties.remove(id);
    }
}
