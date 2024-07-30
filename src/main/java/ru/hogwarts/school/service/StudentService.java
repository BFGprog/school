package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Student;

public interface StudentService {

    public Student delete(Long id);
    public Student create(Student student);
    public Student read(Long id) ;
    public Student update(Long id, Student student);
    ;




}
