package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.HashMap;
import java.util.Map;

@Service
public class StudentServiceImpl implements StudentService {

    private Long countId = 0L;

    private final Map<Long, Student> students = new HashMap<>();

    @Override
    public Student create(Student student) {
        Long actualId = ++countId;
        student.setId(actualId);
        students.put(actualId, student);
        return student;
    }

    @Override
    public Student read(Long id) {
        return students.get(id);
    }

    @Override
    public Student update(Long id, Student student) {
        Student studentUpdate = students.get(id);
        studentUpdate.setName(student.getName());
        studentUpdate.setAge(student.getAge());
        return studentUpdate;
    }

    @Override
    public Student delete(Long id) {
        return students.remove(id);
    }
}
